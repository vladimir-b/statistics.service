package com.n26.exercise.service;

import com.google.common.annotations.VisibleForTesting;
import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Vladimir on 12/2/2016.
 */
@Component
public class StatisticsServiceImpl implements StatisticsService {
    private Statistics currentStatistics = new Statistics();
    final private ArrayList<Transaction> transactions = new ArrayList<>();

    @Value("${statistics.service.ttl}")
    @VisibleForTesting
    private int ttl;

    @Override
    public synchronized Statistics getCurrentStatistics(long currentTime) {
       return currentStatistics;
    }

    @Override
    public synchronized void transactions(Transaction transaction, long currentTime) {
        if (currentTime < transaction.getTimestamp()) {
            throw new IllegalArgumentException("Transaction timestamp is bigger than current time.");
        }

        final long threshold = currentTime - ttl;
        if (transaction.getTimestamp() >= threshold) {
            transactions.add(transaction);

            if (!updateStatistics(threshold)) {
                addTransaction(currentStatistics, transaction);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public synchronized void statisticsPeriodicalUpdate() {
        updateStatistics(System.currentTimeMillis() - ttl);
    }

    protected void setTtl(int ttl) {
        this.ttl = ttl;
    }

    protected boolean updateStatistics(long threshold) {
        if (currentStatistics.getMinTimestamp() < threshold) {
            calculateStatistics(threshold);
            return true;
        }
        return false;
    }

    private void calculateStatistics(long threshold) {
        transactions.removeIf(t -> t.getTimestamp() < threshold);

        final Statistics result = new Statistics();
        transactions.forEach(t -> addTransaction(result, t));
        currentStatistics = result;
    }

    private static Statistics addTransaction(Statistics statistics, Transaction transaction) {
        double amount = transaction.getAmount();

        statistics.setSum(statistics.getSum() + amount);
        if (statistics.getMax() == null || statistics.getMax() < amount) {
            statistics.setMax(amount);
        }

        if (statistics.getMin() == null || statistics.getMin() > amount) {
            statistics.setMin(amount);
        }

        if (statistics.getMinTimestamp() > transaction.getTimestamp()) {
            statistics.setMinTimestamp(transaction.getTimestamp());
        }

        statistics.setCount(statistics.getCount() + 1);
        statistics.setAvg(statistics.getSum() / statistics.getCount());
        return statistics;
    }
}

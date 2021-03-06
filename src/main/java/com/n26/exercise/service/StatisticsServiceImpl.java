package com.n26.exercise.service;

import com.google.common.annotations.VisibleForTesting;
import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * Created by Vladimir on 12/2/2016.
 * Threadsafe statistics service implementation.
 * Calculate statistics upon receiving transactions.
 * Statistics is periodically updated in case if no transactions happen withing last 60 seconds.
 */
@Component
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = LogManager.getLogger(StatisticsServiceImpl.class);
    private Statistics currentStatistics = new Statistics();
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Time to leave for transaction in statistics.
     */
    @Value("${statistics.service.ttl}")
    @VisibleForTesting
    private int ttl;

    /**
     * Return transaction statistics for the last 60 seconds.
     * Statistics is calculated in advance. Methods work in O(1).
     * @param currentTime Current time provided by caller.
     * @return pre calculated statistics.
     */
    @Override
    public synchronized Statistics getCurrentStatistics(long currentTime) {
       return currentStatistics;
    }

    /**
     * Add transaction and update statistics correspondingly.
     * @param transaction to add to statistics.
     * @param currentTime Current time provided by caller.
     * @return true if transaction is within ttl range.
     */
    @Override
    public synchronized boolean transactions(Transaction transaction, long currentTime) {
        logger.trace("Transaction received: " + transaction);

        if (currentTime < transaction.getTimestamp()) {
            String message = "Transaction timestamp is bigger than current time.";
            logger.error(message);

            throw new IllegalArgumentException(message);
        }

        final long threshold = currentTime - ttl;
        if (transaction.getTimestamp() >= threshold) {
            transactions.add(transaction);

            if (!updateStatistics(threshold)) {
                addTransaction(currentStatistics, transaction);

                logger.trace("Statistic updated: " + currentStatistics);
            }
            return true;
        }
        return false;
    }

    /**
     * Update statistics every half second in case if no transactions were happened.
     */
    @Scheduled(fixedRate = 500)
    public synchronized void statisticsPeriodicalUpdate() {
        updateStatistics(System.currentTimeMillis() - ttl);
    }

    protected void setTtl(int ttl) {
        this.ttl = ttl;
    }

    protected boolean updateStatistics(long threshold) {
        if (currentStatistics.getMinTimestamp() < threshold) {
            calculateStatistics(threshold);
            logger.trace("Statistic fully recalculated: " + currentStatistics);

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

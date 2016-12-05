package com.n26.exercise.service;

import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;

/**
 * Created by Vladimir on 12/4/2016.
 */
public interface StatisticsService {
    /*
            final Timer timer = new Timer(ttl / 2, e -> updateStatistics(e.getWhen() - ttl));

            public StatisticsService(int ttl) {
                this.ttl = ttl;
                this.timer.start();
            }
        */

    /**
     * TODO
     * @param currentTime
     * @return
     */
    Statistics getCurrentStatistics(long currentTime);

    /**
     *
     * @param transaction
     * @param currentTime
     */
    void transactions(Transaction transaction, long currentTime);
}

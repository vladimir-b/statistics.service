package com.n26.exercise.service;

import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;

/**
 * Created by Vladimir on 12/4/2016.
 * Basic interface for updating and receiving statistics.
 */
public interface StatisticsService {
    /**
     * Return transaction statistics for the last 60 seconds.
     * Statistics is calculated in advance. Methods work in O(1).
     * @param currentTime Current time provided by caller.
     * @return pre calculated statistics.
     */
    Statistics getCurrentStatistics(long currentTime);

    /**
     * Add transaction and update statistics correspondingly.
     * @param transaction to add to statistics.
     * @param currentTime Current time provided by caller.
     * @return true if transaction is within ttl range.
     */
    boolean transactions(Transaction transaction, long currentTime);
}

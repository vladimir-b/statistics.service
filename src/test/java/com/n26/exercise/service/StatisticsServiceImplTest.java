package com.n26.exercise.service;

import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Vladimir on 12/4/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsServiceImplTest {
    private final int ttl = 10;
    private final double delta = 0.0001;
    private StatisticsServiceImpl service;

    @Before
    public void setUp() {
        service = new StatisticsServiceImpl();
        service.setTtl(ttl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoesNotAcceptTransactionWithTimestampInFuture() {
        // Arrange
        final long currentTime = System.currentTimeMillis();
        Transaction transaction = new Transaction(10, currentTime + 10);

        // Act / Assert
        service.transactions(transaction, currentTime);
    }

    @Test
    public void testDoesNotKeepExpiredTransactions1() {
        // Arrange
        final long currentTime = System.currentTimeMillis();
        Transaction transaction = new Transaction(10, currentTime - ttl - 1);

        // Act
        service.transactions(transaction, currentTime);

        // Assert
        Statistics statistics = service.getCurrentStatistics(currentTime);
        assertEquals(0, statistics.getAvg(), delta);
        assertEquals(0, statistics.getCount(), delta);
        assertNull(statistics.getMax());
        assertNull(statistics.getMin());
        assertEquals(0, statistics.getSum(), delta);
    }

    @Test
    public void testDoesNotKeepExpiredTransactions2() {
        // Arrange
        final long currentTime = System.currentTimeMillis();
        Transaction transaction1 = new Transaction(10, currentTime - ttl + 1);
        Transaction transaction2 = new Transaction(5, currentTime - ttl + 2);

        // Act
        service.transactions(transaction1, currentTime);
        service.transactions(transaction2, currentTime + 2); // transaction1 should expire here. transaction2 still valid

        // Assert
        Statistics statistics = service.getCurrentStatistics(currentTime);
        assertEquals(5, statistics.getAvg(), delta);
        assertEquals(1, statistics.getCount(), delta);
        assertEquals(5, statistics.getMax(), delta);
        assertEquals(5, statistics.getMin(), delta);
        assertEquals(5, statistics.getSum(), delta);
    }

    @Test
    public void testStatisticsCalculation() {
        // Arrange
        final long currentTime = System.currentTimeMillis();
        Transaction transaction1 = new Transaction(10, currentTime - ttl + 1);
        Transaction transaction2 = new Transaction(5, currentTime - ttl + 2);

        // Act
        service.transactions(transaction1, currentTime);
        service.transactions(transaction2, currentTime);

        // Assert
        Statistics statistics = service.getCurrentStatistics(currentTime);
        assertEquals(7.5, statistics.getAvg(), delta);
        assertEquals(2, statistics.getCount(), delta);
        assertEquals(10, statistics.getMax(), delta);
        assertEquals(5, statistics.getMin(), delta);
        assertEquals(15, statistics.getSum(), delta);
    }

    @Test
    public void testStatisticsUpdatedOnTimer() {
        // Arrange
        final long currentTime = System.currentTimeMillis();
        Transaction transaction1 = new Transaction(10, currentTime - ttl + 1);
        Transaction transaction2 = new Transaction(5, currentTime - ttl + 2);


        service.transactions(transaction1, currentTime);
        service.transactions(transaction2, currentTime);

        // Act
        service.updateStatistics(currentTime - ttl + 2);

        // Assert
        Statistics statistics = service.getCurrentStatistics(currentTime);
        assertEquals(5, statistics.getAvg(), delta);
        assertEquals(1, statistics.getCount(), delta);
        assertEquals(5, statistics.getMax(), delta);
        assertEquals(5, statistics.getMin(), delta);
        assertEquals(5, statistics.getSum(), delta);
    }
}

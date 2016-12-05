package com.n26.exercise.model;

/**
 * Created by Vladimir on 12/2/2016.
 */
public class Transaction {
    private double amount;
    private long timestamp;

    public Transaction() {
        this(0, 0);
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

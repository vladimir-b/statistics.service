package com.n26.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Vladimir on 12/2/2016.
 */
public class Statistics {
    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    @JsonIgnore
    private long minTimestamp;

    public long getMinTimestamp() {
        return minTimestamp;
    }

    public void setMinTimestamp(long minTimestamp) {
        this.minTimestamp = minTimestamp;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }
}

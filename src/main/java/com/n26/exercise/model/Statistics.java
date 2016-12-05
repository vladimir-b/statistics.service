package com.n26.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Vladimir on 12/2/2016.
 */
public class Statistics {
    private double sum;
    private double avg;
    private Double max;
    private Double min;
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

    public void setMax(Double max) {
        this.max = max;
    }

    public void setMin(Double min) {
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

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                ", minTimestamp=" + minTimestamp +
                '}';
    }
}

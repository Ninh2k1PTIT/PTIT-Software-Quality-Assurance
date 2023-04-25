package model;

import java.io.Serializable;

public class Period implements Serializable {

    private long periodId;
    private long month;
    private double interestRate;

    public Period() {
    }

    public Period(long periodId, long month, double interestRate) {
        this.periodId = periodId;
        this.month = month;
        this.interestRate = interestRate;
    }

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return this.month + " tháng - " + this.interestRate + " %/năm";
    }
}

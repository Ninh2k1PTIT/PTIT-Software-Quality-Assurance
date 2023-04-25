package model;

import java.io.Serializable;
import java.util.Date;

public class Passbook implements Serializable {

    private long passbookId;
    private Date startDate;
    private Date endDate;
    private Date settlementDate;
    private long amount;
    private Account account;
    private Period period;

    public Passbook() {
    }

    public Passbook(Date startDate, Date endDate, Date settlementDate, long amount, Account account, Period period) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.settlementDate = settlementDate;
        this.amount = amount;
        this.account = account;
        this.period = period;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Long getPassbookId() {
        return passbookId;
    }

    public void setPassbookId(Long passbookId) {
        this.passbookId = passbookId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}

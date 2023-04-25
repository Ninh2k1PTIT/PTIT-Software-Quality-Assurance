package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    private long accountId;
    private String username;
    private String password;
    private long balance;
    private ArrayList<Passbook> passbooks;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, long balance, ArrayList<Passbook> passbooks) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.passbooks = passbooks;
    }

    public Account(long accountId, String username, String password) {
        this.username = username;
        this.password = password;
        this.accountId = accountId;
    }

    public Account(long accountId, String username, String password, long balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public ArrayList<Passbook> getPassbooks() {
        return passbooks;
    }

    public void setPassbooks(ArrayList<Passbook> passbooks) {
        this.passbooks = passbooks;
    }

}

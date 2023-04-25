package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;

public class AccountDAO extends DAO {

    public AccountDAO() {
        super();
    }

    public Account login(String username, String password) {
        Account result = new Account();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.setUsername(rs.getString("username"));
                result.setAccountId(rs.getLong("accountId"));
                result.setBalance(rs.getLong("balance"));
            } else {
                result = null;
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public boolean signup(Account account) {
        String sqlCheckUsername = "SELECT username FROM account WHERE username = ?";
        String sql = "INSERT INTO account (username, password, balance) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sqlCheckUsername);
            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setLong(3, 0);
            ps.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Account getAccountById(long id) {
        Account account = new Account();
        String sql = "SELECT username, balance, accountId FROM account WHERE accountId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account.setUsername(rs.getString("username"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountId(rs.getLong("accountId"));
            } else {
                account = null;
            }
        } catch (SQLException e) {
            return null;
        }
        return account;
    }

    public boolean depositeIntoAccount(long accountId, long amount) {
        String sql = "UPDATE account SET balance = balance + ? WHERE accountId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, amount);
            ps.setLong(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}

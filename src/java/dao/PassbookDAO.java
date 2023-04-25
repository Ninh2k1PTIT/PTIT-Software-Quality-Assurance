package dao;

import static dao.DAO.con;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Account;
import model.Passbook;
import model.Period;
import utilities.CalculateInterest;

public class PassbookDAO extends DAO {

    private static final String CREATE_PASS_BOOK_SQL = "INSERT INTO passbook (accountId, periodId, startDate, endDate, amount) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_STARTDATE_AND_ENDDATE_SQL = "SELECT CURRENT_DATE AS startDate, DATE_ADD(CURRENT_DATE, INTERVAL ? DAY) AS endDate";
    private static final String SELECT_BALANCE_OF_ACCOUNT_SQL = "SELECT balance FROM account WHERE account.accountId = ?";
    private static final String SELECT_PASSBOOKS_OF_ACCOUNT_SQL = "SELECT passbookId, startDate, endDate, amount, month, interestRate\n"
            + "FROM passbook\n"
            + "INNER JOIN period ON period.periodId = passbook.periodId\n"
            + "WHERE passbook.accountId = ? AND passbook.settlementDate IS NULL";
    private static final String SELECT_PASSBOOK_BY_ID_SQL = "SELECT passbookId, startDate, endDate, amount, month, interestRate, accountId\n"
            + "FROM passbook\n"
            + "INNER JOIN period ON period.periodId = passbook.periodId\n"
            + "WHERE passbook.passbookId = ?";
    private static final String UPDATE_SETTLEMENT_SQL = "UPDATE passbook SET settlementDate = CURRENT_DATE WHERE passbookId = ?";

    public PassbookDAO() {
        super();
    }

    public boolean create(Passbook passbook) {
        try {
            //Check balance > = passbook.amount
            PreparedStatement ps = con.prepareStatement(SELECT_BALANCE_OF_ACCOUNT_SQL);
            ps.setLong(1, passbook.getAccount().getAccountId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long balance = rs.getLong("balance");
                if (balance < passbook.getAmount()) {
                    return false;
                }
                ps = con.prepareStatement(SELECT_STARTDATE_AND_ENDDATE_SQL);
                ps.setLong(1, passbook.getPeriod().getMonth() * 30);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    ps = con.prepareStatement(CREATE_PASS_BOOK_SQL);
                    ps.setLong(1, passbook.getAccount().getAccountId());
                    ps.setLong(2, passbook.getPeriod().getPeriodId());
                    ps.setDate(3, startDate);
                    ps.setDate(4, endDate);
                    ps.setLong(5, passbook.getAmount());
                    ps.executeUpdate();
                    AccountDAO accountDAO = new AccountDAO();
                    accountDAO.depositeIntoAccount(passbook.getAccount().getAccountId(), -passbook.getAmount());
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public ArrayList<Passbook> getPassbooksOfAccount(long accountId) {
        ArrayList<Passbook> result = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_PASSBOOKS_OF_ACCOUNT_SQL);
            ps.setLong(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Passbook passbook = new Passbook();
                passbook.setPassbookId(rs.getLong("passbookId"));
                passbook.setStartDate(rs.getDate("startDate"));
                passbook.setEndDate(rs.getDate("endDate"));
                passbook.setAmount(rs.getLong("amount"));

                Period period = new Period();
                period.setMonth(rs.getLong("month"));
                period.setInterestRate(rs.getDouble("interestRate"));
                passbook.setPeriod(period);
                result.add(passbook);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public Passbook getPassbookById(long id) {
        Passbook passbook = new Passbook();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_PASSBOOK_BY_ID_SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                passbook.setPassbookId(rs.getLong("passbookId"));
                passbook.setStartDate(rs.getDate("startDate"));
                passbook.setEndDate(rs.getDate("endDate"));
                passbook.setAmount(rs.getLong("amount"));

                Period period = new Period();
                period.setMonth(rs.getLong("month"));
                period.setInterestRate(rs.getDouble("interestRate"));

                Account account = new Account();
                account.setAccountId(rs.getLong("accountId"));

                passbook.setAccount(account);
                passbook.setPeriod(period);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }

        return passbook;
    }

    public boolean settlementPassbook(long id) {
        Passbook passbook = getPassbookById(id);
        AccountDAO accountDAO = new AccountDAO();
        try {
            PreparedStatement ps = con.prepareStatement(UPDATE_SETTLEMENT_SQL);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        accountDAO.depositeIntoAccount(passbook.getAccount().getAccountId(), CalculateInterest.getLaiHienTaiCuaSoTietKiem(passbook) + passbook.getAmount());
        return true;
    }
}

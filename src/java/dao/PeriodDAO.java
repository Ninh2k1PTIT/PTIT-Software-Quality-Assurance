package dao;

import static dao.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Period;

public class PeriodDAO extends DAO {

    public PeriodDAO() {
        super();
    }

    public ArrayList<Period> getAll() {
        String sql = "SELECT * FROM period WHERE period.month != 0";
        ArrayList<Period> result = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Period p = new Period();
                p.setPeriodId(rs.getLong("periodId"));
                p.setMonth(rs.getLong("month"));
                p.setInterestRate(rs.getDouble("interestRate"));
                result.add(p);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public Period getDefault() {
        String sql = "SELECT * FROM period WHERE period.month = 0";
        Period p = new Period();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setPeriodId(rs.getLong("periodId"));
                p.setMonth(rs.getLong("month"));
                p.setInterestRate(rs.getDouble("interestRate"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
        return p;
    }
}

package utilities;

import dao.DAO;
import dao.PeriodDAO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Passbook;
import model.Period;

public class CalculateInterest extends DAO {

    private static final String SELECT_DATEDIFF_SQL = "SELECT DATEDIFF(CURRENT_DATE, ?) as datediff";

    public static long getLai(Period period, long soTien, long ngayGui) {
        //So chu ki trong 1 nam: n = (365/(30*period.month)
        double soChuKiTrongNam = 365.0 / (30 * period.getMonth());
        long soNgayDu = ngayGui % (30 * period.getMonth());

        //Lai kep = Goc * (1 + Lai suat/n)^(n*ngayGui/365)
        double laiKep = soTien * Math.pow((1 + period.getInterestRate() / 100 / soChuKiTrongNam), soChuKiTrongNam * (ngayGui - soNgayDu) / 365) - soTien;

        double laiDon = getLaiKhongKiHan(soTien + laiKep, soNgayDu);
        return (long) (Math.round((laiKep + laiDon) * 10) / 10.0);
    }

    public static long getLaiKhongKiHan(double soTien, long ngayGui) {
        PeriodDAO periodDAO = new PeriodDAO();
        Period period = periodDAO.getDefault();
        return (long) (Math.round((soTien * (period.getInterestRate() / 100) * ngayGui / 365) * 10) / 10.0);
    }

    public static long getLaiHienTaiCuaSoTietKiem(Passbook passbook) {
        long result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_DATEDIFF_SQL);
            ps.setDate(1, (Date) passbook.getStartDate());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long datediff = rs.getLong("datediff");
                if (datediff < passbook.getPeriod().getMonth() * 30) {
                    result = getLaiKhongKiHan(passbook.getAmount(), datediff);
                } else {
                    result = getLai(passbook.getPeriod(), passbook.getAmount(), datediff);
                }
            }
        } catch (SQLException e) {
        }
        return result;

    }

    public static long getLaiDaoHanCuaSoTietKiem(Passbook passbook) {
        return getLai(passbook.getPeriod(), passbook.getAmount(), passbook.getPeriod().getMonth() * 30);
    }
}

package utilities;

import dao.PassbookDAO;
import dao.PeriodDAO;
import java.sql.SQLException;
import model.Passbook;
import model.Period;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

//Người thực hiện: Lê Ngọc Phương
public class CalculateInterestTest {

    PeriodDAO periodDAO = new PeriodDAO();
    PassbookDAO passbookDAO = new PassbookDAO();

    public CalculateInterestTest() {
    }

    @Before
    public void setUp() throws SQLException {
        periodDAO.con.setAutoCommit(false);
        passbookDAO.con.setAutoCommit(false);
    }

    @After
    public void testDown() throws SQLException {
        periodDAO.con.rollback();
        passbookDAO.con.rollback();
        periodDAO.con.setAutoCommit(true);
        passbookDAO.con.setAutoCommit(true);
    }

    @Test
    public void testcase1() {
        // test so ngay gui = ky han
        Period period = new Period(2, 1, 6);
        long tienLaiThucTe = CalculateInterest.getLai(period, 100000, 30);
        assertEquals(tienLaiThucTe, 493);
        Passbook passbook = new Passbook();
        passbook.setPeriod(period);
        passbook.setAmount(100000L);
        long tienLaiDaoHan = CalculateInterest.getLaiDaoHanCuaSoTietKiem(passbook);
        assertEquals(tienLaiDaoHan, tienLaiThucTe);
    }

    @Test
    public void testcase3() {
        // test so ngay gui < ky han (ngay gui =29)
        // test bien
        Period period = new Period(2, 1, 6);
        long tienLaiThucTe = CalculateInterest.getLai(period, 100000, 29);
        assertEquals(tienLaiThucTe, 39);
        Passbook passbook = new Passbook();
        passbook.setPeriod(period);
        passbook.setAmount(100000L);
        long tienLaiDaoHan = CalculateInterest.getLaiDaoHanCuaSoTietKiem(passbook);
        assertNotEquals(tienLaiDaoHan, tienLaiThucTe);

    }

    @Test
    public void testcase2() {
        // test so ngay gui > ky han (ngay gui =31)
        // test bien
        Period period = new Period(2, 1, 6);
        long tienLaiThucTe = CalculateInterest.getLai(period, 100000, 31);
        assertEquals(tienLaiThucTe, 494);
        Passbook passbook = new Passbook();
        passbook.setPeriod(period);
        passbook.setAmount(100000L);
        long tienLaiDaoHan = CalculateInterest.getLaiDaoHanCuaSoTietKiem(passbook);
        assertNotEquals(tienLaiDaoHan, tienLaiThucTe);
    }

    @Test
    public void getLaiHienTaiCuaSoTietKiem() {
        Passbook passbook = passbookDAO.getPassbookById(1);
        long tienLaiHienTai = CalculateInterest.getLaiHienTaiCuaSoTietKiem(passbook);
        long tienLaiDaoHan = CalculateInterest.getLaiDaoHanCuaSoTietKiem(passbook);
        assertNotEquals(tienLaiDaoHan, tienLaiHienTai);
    }
}

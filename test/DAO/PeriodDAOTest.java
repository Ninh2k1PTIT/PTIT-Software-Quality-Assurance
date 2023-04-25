package DAO;

import dao.PeriodDAO;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

//Nguoi thuc hien: Nguyen Tien Hai Ninh
public class PeriodDAOTest {

    public PeriodDAOTest() {
    }

    PeriodDAO periodDAO = new PeriodDAO();

    @Before
    public void setUp() throws SQLException {
        periodDAO.con.setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        periodDAO.con.rollback();
        periodDAO.con.setAutoCommit(true);
    }

    //Test Get các Kì hạn, Lãi suất trong database (Trừ Không kì hạn)
    @Test
    public void testGetAll() {
        assertEquals(periodDAO.getAll().size(), 7);
    }

    //Test Get Lãi suất Không kì hạn (Kì hạn là 0 tháng)
    @Test
    public void testGetDefault() {
        assertEquals(periodDAO.getDefault().getMonth(), 0);
    }
}

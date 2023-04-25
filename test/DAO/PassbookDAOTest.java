package DAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Account;
import model.Passbook;
import model.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import dao.PassbookDAO;

//Người thực hiện: Nguyễn Xuân Ngọc

public class PassbookDAOTest {
    
    public PassbookDAOTest() {
    }
    
    PassbookDAO passbookDAO = new PassbookDAO();
    
    @Before
    public void setUp() throws SQLException {
        passbookDAO.con.setAutoCommit(false);
    }
    
    @After
    public void tearDown() throws SQLException {
        passbookDAO.con.rollback();
        passbookDAO.con.setAutoCommit(true);
    }

    @Test
    public void testCreate1() throws ParseException {
          //tạo thành công 1 sổ tiết kiệm mới
          Passbook passbook;
          Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04");
          Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-04");
          //String endDate = "2023-05-04";
          Date settlementDate = null;
          Account acc = new Account(3,"ngoc", "1"); //id = 3
          Period period = new Period(2, 1, 6);
          //passbook.getAccount().getAccountId();
          long periodId =2;
          long amount = 1000000;
          
          ArrayList<Passbook> currentPassbook = passbookDAO.getPassbooksOfAccount(3);
          
          passbook = new Passbook(startDate, endDate, settlementDate, amount, acc, period);
          //tạo sổ mới
          boolean passbookRes = passbookDAO.create(passbook);
          boolean expRes = true;
          assertEquals(expRes, passbookRes);
        
          //kiểm tra với database sau khi thêm sổ mới
//          System.out.println("size hien tai: "+currentPassbook.size());
//          System.out.println("size sau khi tao: "+passbookDAO.getPassbooksOfAccount(3).size());
          assertEquals((currentPassbook.size()+1), passbookDAO.getPassbooksOfAccount(3).size());
          
    }
    
    @Test
    public void testCreate2() throws ParseException {
        //để trống số tiền gửi
          Passbook passbook;
          Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04");
          Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-04");
          //String endDate = "2023-05-04";
          Date settlementDate = null;
          Account acc = new Account(3,"ngoc", "1");
          Period period = new Period(2, 1, 6);
          //passbook.getAccount().getAccountId();
          long periodId =2;
          long amount = 0;
          
          ArrayList<Passbook> currentPassbook = passbookDAO.getPassbooksOfAccount(3);
          
          passbook = new Passbook(startDate, endDate, settlementDate, amount, acc, period);
          boolean passbookRes = passbookDAO.create(passbook);
          boolean expRes = false;
          
          assertEquals(expRes, passbookRes);
          //kiểm tra số lượng sổ trước và sau khi tạo
          assertEquals((currentPassbook.size()), passbookDAO.getPassbooksOfAccount(3).size());
    }
    
    @Test
    public void testCreate3() throws ParseException {
          //số tiền gửi < 1.000.000đ
          Passbook passbook;
          Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04");
          Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-04");
          //String endDate = "2023-05-04";
          Date settlementDate = null;
          Account acc = new Account(3,"ngoc", "1");
          Period period = new Period(2, 1, 6);
          //passbook.getAccount().getAccountId();
          long periodId =2;
          long amount = 100;
          
          ArrayList<Passbook> currentPassbook = passbookDAO.getPassbooksOfAccount(3);
          
          passbook = new Passbook(startDate, endDate, settlementDate, amount, acc, period);
          boolean passbookRes = passbookDAO.create(passbook);
          boolean expRes = false;
          
          assertEquals(expRes, passbookRes);
          //kiểm tra số lượng sổ trước và sau khi tạo
          assertEquals((currentPassbook.size()), passbookDAO.getPassbooksOfAccount(3).size());
    }
    
    @Test
    public void testCreate4() throws ParseException {
          //số tiền gửi > số dư hiện có
          Passbook passbook;
          Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04");
          Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-04");
          //String endDate = "2023-05-04";
          Date settlementDate = null;
            Account acc = new Account(3,"ngoc", "1",10000000); //989036985
          Period period = new Period(2, 1, 6);
          //passbook.getAccount().getAccountId();
          long periodId =2;
          long amount = 12000000;
          ArrayList<Passbook> currentPassbook = passbookDAO.getPassbooksOfAccount(3);
          
          passbook = new Passbook(startDate, endDate, settlementDate, amount, acc, period);
          boolean passbookRes = passbookDAO.create(passbook);
          boolean expRes = false;
          assertEquals(expRes, passbookRes);
          //kiểm tra số lượng sổ trước và sau khi tạo
          assertEquals((currentPassbook.size()), passbookDAO.getPassbooksOfAccount(3).size());
    }
    
    @Test
    public void testCreate5() throws ParseException {
        //lãi suất không kỳ hạn
          Passbook passbook;
          Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04");
          Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-04");
          //String endDate = "2023-05-04";
          Date settlementDate = null;
          Account acc = new Account(3,"ngoc", "1");
          Period period = new Period(0, 0, 0.1);
          //passbook.getAccount().getAccountId();
          long periodId =2;
          long amount = 1000000;
          ArrayList<Passbook> currentPassbook = passbookDAO.getPassbooksOfAccount(3);
          
          passbook = new Passbook(startDate, endDate, settlementDate, amount, acc, period);
          boolean passbookRes = passbookDAO.create(passbook);
          boolean expRes = true;
          assertEquals(expRes, passbookRes);
          //kiểm tra số lượng sổ trước và sau khi tạo
          assertEquals((currentPassbook.size()+1), passbookDAO.getPassbooksOfAccount(3).size());
    }

    /**
     * Test of getPassbooksOfAccount method, of class PassbookDAO.
     */
    @Test
    public void testGetPassbooksOfAccount() {
        //lấy ra số sổ theo accountId
        //System.out.println("getPassbooksOfAccount");
        long accountId = 3; // id tài khoản
        PassbookDAO passbook = new PassbookDAO();
//        ArrayList<Passbook> expResult = 
        ArrayList<Passbook> result = passbook.getPassbooksOfAccount(accountId);
        //int expRes = result.size();
        //System.out.println(result.size());
        assertNotNull(result);
        
    }    
}


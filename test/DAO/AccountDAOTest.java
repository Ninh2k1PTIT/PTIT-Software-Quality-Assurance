package DAO;

import dao.AccountDAO;
import java.sql.SQLException;
import model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//Người thực hiện: Nguyễn Tiến Hải Ninh
public class AccountDAOTest {

    public AccountDAOTest() {
    }

    AccountDAO accountDAO = new AccountDAO();

    @Before
    public void setUp() throws SQLException {
        accountDAO.con.setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        accountDAO.con.rollback();
        accountDAO.con.setAutoCommit(true);
    }

    //Test login tài khoản, mật khẩu có trong database
    @Test
    public void testLogin1() {
        Account actualResult = accountDAO.login("ninh", "1");
        assertEquals(actualResult.getUsername(), "ninh");
    }

    //Test login tài khoản, có trong database nhưng sai mật khẩu
    @Test
    public void testLogin2() {
        Account actualResult = accountDAO.login("ninh", "2");
        assertNull(actualResult);
    }

    //Test login tài khoản không có trong database
    @Test
    public void testLogin3() {
        Account actualResult = accountDAO.login("abc", "1");
        assertNull(actualResult);
    }

    //Test login tài khoản để trống username
    @Test
    public void testLogin4() {
        Account actualResult = accountDAO.login("", "1");
        assertNull(actualResult);
    }

    //Test login tài khoản để trống password
    @Test
    public void testLogin5() {
        Account actualResult = accountDAO.login("ninh", "");
        assertNull(actualResult);
    }

    //Test đăng ký tài khoản chưa có trong database
    @Test
    public void testSignup1() {
        boolean actualResult = accountDAO.signup(new Account("test", "123"));
        Account account = accountDAO.login("test", "123");

        //Kiểm tra function
        assertTrue(actualResult);
        //Kiểm tra database: tài khoản phải tồn tại sau khi đăng ký
        assertEquals(account.getUsername(), "test");
    }

    //Test đăng ký tài khoản đã có trong database
    @Test
    public void testSignup2() {
        Account account = accountDAO.login("ninh", "1");
        //Kiểm tra database: tài khoản đã tồn tại trước khi đăng ký
        assertEquals(account.getUsername(), "ninh");
        boolean actualResult = accountDAO.signup(new Account("ninh", "123"));
        //Kiểm tra function
        assertFalse(actualResult);
    }

    //Test đăng ký tài khoản để trống username
    @Test
    public void testSignup3() {
        boolean actualResult = accountDAO.signup(new Account("", "123"));
        Account account = accountDAO.login("", "123");
        //Kiểm tra function
        assertFalse(actualResult);
        //Kiểm tra database: không cho phép đăng ký
        assertNull(account);
    }

    //Test đăng ký tài khoản để trống password
    @Test
    public void testSignup4() {
        boolean actualResult = accountDAO.signup(new Account("test", ""));
        Account account = accountDAO.login("test", "");
        //Kiểm tra function
        assertFalse(actualResult);
        //Kiểm tra database: không cho phép đăng ký
        assertNull(account);
    }

    //Test get tài khoản tồn tại trong database
    @Test
    public void testGetAccountById1() {
        Account actualResult = accountDAO.getAccountById(1);
        assertEquals(actualResult.getAccountId(), 1);
    }

    //Test get tài khoản không tồn tại trong database
    @Test
    public void testGetAccountById2() {
        Account actualResult = accountDAO.getAccountById(999);
        assertNull(actualResult);
    }

    //Test nạp tiền vào tài khoản với số tiền là 0
    @Test
    public void testdepositeIntoAccount1() {
        long deposite = 0;
        long currentBalance = accountDAO.getAccountById(1).getBalance();
        long expectedBalance = currentBalance + deposite;
        boolean actualResult = accountDAO.depositeIntoAccount(1, deposite);
        long actualBalance = accountDAO.getAccountById(1).getBalance();
        //Kiểm tra function
        assertTrue(actualResult);
        //Kiểm tra database: số dư không thay đổi
        assertEquals(actualBalance, expectedBalance);
    }

    //Test nạp tiền vào tài khoản với số tiền là 1000
    @Test
    public void testdepositeIntoAccount2() {
        long deposite = 1000;
        long currentBalance = accountDAO.getAccountById(1).getBalance();
        long expectedBalance = currentBalance + deposite;
        boolean actualResult = accountDAO.depositeIntoAccount(1, deposite);
        long actualBalance = accountDAO.getAccountById(1).getBalance();
        //Kiểm tra function
        assertTrue(actualResult);
        //Kiểm tra database: số dư thay đổi +1000 so với trước khi nạp
        assertEquals(actualBalance, expectedBalance);
    }

    //Test trừ tiền tài khoản với số tiền là 1000
    @Test
    public void testdepositeIntoAccount3() {
        long deposite = -1000;
        long currentBalance = accountDAO.getAccountById(1).getBalance();
        long expectedBalance = currentBalance + deposite;
        boolean actualResult = accountDAO.depositeIntoAccount(1, deposite);
        long actualBalance = accountDAO.getAccountById(1).getBalance();
        //Kiểm tra function
        assertTrue(actualResult);
        //Kiểm tra database: số dư thay đổi -1000 so với trước khi trừ
        assertEquals(actualBalance, expectedBalance);
    }

    //Test nạp tiền vào tài khoản không tồn tại
    @Test
    public void testdepositeIntoAccount4() {
        Account account = accountDAO.getAccountById(999);
        //Kiểm tra database: Tài khoản không tồn tại
        assertNull(account);
        boolean actualResult = accountDAO.depositeIntoAccount(999, 1000);
        //Kiểm tra function
        assertFalse(actualResult);

    }
}

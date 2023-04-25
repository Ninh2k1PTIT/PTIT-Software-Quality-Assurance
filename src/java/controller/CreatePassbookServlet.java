package controller;

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.PassbookDAO;
import dao.PeriodDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Passbook;
import model.Period;

@WebServlet(name = "CreatePassbookServlet", urlPatterns = {"/passbook-create"})
public class CreatePassbookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO();
        Cookie accountIdCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    accountIdCookie = cookie;
                }
            }
        }
        if (accountIdCookie != null) {
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountById(Long.parseLong(accountIdCookie.getValue()));
            request.setAttribute("balance", account.getBalance());
        }
        ArrayList list = periodDAO.getAll();
        request.setAttribute("listPeriod", list);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/passbook-create.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Passbook passbook = new Passbook();
        Account account = new Account();
        //Set period
        Gson g = new Gson();
        Period period = g.fromJson(request.getParameter("period"), Period.class);

        //Set account
        Cookie accountIdCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    accountIdCookie = cookie;
                }
            }
        }
        if (accountIdCookie != null) {
            account.setAccountId(Long.parseLong(accountIdCookie.getValue()));
        }

        passbook.setAccount(account);
        passbook.setPeriod(period);
        passbook.setAmount(Long.parseLong(request.getParameter("amount")));

        PassbookDAO passbookDAO = new PassbookDAO();
        if (passbookDAO.create(passbook)) {
            request.getSession().setAttribute("createPassbookSuccess", true);
            response.sendRedirect(request.getContextPath() + "/passbooks");
        } else {
            request.setAttribute("error", "Số dư không đủ");
            doGet(request, response);
        }
    }
}

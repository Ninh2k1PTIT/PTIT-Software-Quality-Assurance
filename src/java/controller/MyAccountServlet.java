package controller;

import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "GetMyAccountServlet", urlPatterns = {"/myaccount"})
public class MyAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            request.setAttribute("account", account);
        }

        RequestDispatcher rd = request.getRequestDispatcher("myaccount.jsp");
        rd.forward(request, response);
    }
}

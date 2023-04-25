package controller;

import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PayinServlet", urlPatterns = {"/deposite"})
public class DepositeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long amount = Long.parseLong(request.getParameter("amount"));
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
            if (accountDAO.depositeIntoAccount(Long.parseLong(accountIdCookie.getValue()), amount)) {
                request.getSession().setAttribute("depositeSuccess", true);
                response.sendRedirect(request.getContextPath() + "/myaccount");
            }
        }

    }
}

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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String error = "";
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.login(username, password);;
        if ((username != null && password != null)) {
            if (account != null) {
                Cookie usernameCookie = new Cookie("username", account.getUsername());
                Cookie idCookie = new Cookie("id", account.getAccountId() + "");
                usernameCookie.setMaxAge(60 * 60 * 24);
                idCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(usernameCookie);
                response.addCookie(idCookie);
                response.sendRedirect("/BankManagement/passbooks");
            } else {
                error = "Username hoặc password không chính xác";
                request.setAttribute("error", error);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
            }
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
}

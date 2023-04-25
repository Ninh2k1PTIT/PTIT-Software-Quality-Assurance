package controller;

import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");
        String error = "";

        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, password);
        if (!password.equals(rePassword)) {
            error = "Xác khẩu mật khẩu chưa trùng khớp";
            request.setAttribute("error", error);
            request.setAttribute("username", username);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            rd.forward(request, response);
        } else if (!accountDAO.signup(account)) {
            error = "Username đã tồn tại";
            request.setAttribute("error", error);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("signupSuccess", true);
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }
}

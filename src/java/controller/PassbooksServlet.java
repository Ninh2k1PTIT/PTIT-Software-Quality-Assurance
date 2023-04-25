package controller;

import dao.PassbookDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Passbook;

@WebServlet(name = "GetPassbookServlet", urlPatterns = {"/passbooks"})
public class PassbooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PassbookDAO passbookDAO = new PassbookDAO();
        long accountId = 0;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    accountId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }

        List<Passbook> list = passbookDAO.getPassbooksOfAccount(accountId);
        request.setAttribute("listPassbook", list);
        RequestDispatcher rd = request.getRequestDispatcher("passbooks-management.jsp");
        rd.forward(request, response);
    }
}

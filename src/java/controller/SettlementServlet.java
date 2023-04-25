package controller;

import dao.PassbookDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Passbook;
import utilities.CalculateInterest;

@WebServlet(name = "GetPassbookDetailServlet", urlPatterns = {"/settlement"})
public class SettlementServlet extends HttpServlet {

    private String passbookId = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PassbookDAO passbookDAO = new PassbookDAO();
        passbookId = request.getParameter("id");
        Passbook passbook = passbookDAO.getPassbookById(Long.parseLong(passbookId));
        request.setAttribute("passbook", passbook);
        request.setAttribute("endInterest", CalculateInterest.getLaiDaoHanCuaSoTietKiem(passbook));
        request.setAttribute("currentInterest", CalculateInterest.getLaiHienTaiCuaSoTietKiem(passbook));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/settlement.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PassbookDAO passbookDAO = new PassbookDAO();
        if (passbookDAO.settlementPassbook(Long.parseLong(passbookId))) {
            request.getSession().setAttribute("createPassbookSuccess", true);
            response.sendRedirect(request.getContextPath() + "/passbooks");
        }
    }

}

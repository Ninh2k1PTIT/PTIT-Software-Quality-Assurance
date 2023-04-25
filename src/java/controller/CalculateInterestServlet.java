package controller;

import com.google.gson.Gson;
import dao.PeriodDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Period;
import utilities.CalculateInterest;

@WebServlet(name = "CalculateInterestServlet", urlPatterns = {"/calculate-interest"})
public class CalculateInterestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO();
        ArrayList list = periodDAO.getAll();
        request.setAttribute("listPeriod", list);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/calculate-interest.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long day = Long.parseLong(request.getParameter("day"));
        long amount = Long.parseLong(request.getParameter("amount"));
        System.out.println(request.getParameter("period"));
        Gson g = new Gson();
        Period period = g.fromJson(request.getParameter("period"), Period.class);
        request.setAttribute("day", day);
        request.setAttribute("amount", amount);
        request.setAttribute("period", period);
        request.setAttribute("endInterest", CalculateInterest.getLai(period, amount, period.getMonth() * 30));
        request.setAttribute("currentInterest", CalculateInterest.getLai(period, amount, day));
        doGet(request, response);
    }
}

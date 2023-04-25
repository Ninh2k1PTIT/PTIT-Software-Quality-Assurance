package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        Cookie usernameCookie = null;
        Cookie accountIdCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    usernameCookie = cookie;
                }
                if (cookie.getName().equals("id")) {
                    accountIdCookie = cookie;
                }
            }
        }
        if (usernameCookie != null) {
            usernameCookie.setMaxAge(0);
            response.addCookie(usernameCookie);
        }
        if (accountIdCookie != null) {
            accountIdCookie.setMaxAge(0);
            response.addCookie(accountIdCookie);
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}

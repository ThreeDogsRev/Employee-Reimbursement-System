package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/employeeHome")
public class EmployeeHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        if (ses.getAttribute("user") != null) {
            req.getRequestDispatcher("employeeHomePage.html").forward(req, resp);
        } else {
            req.setAttribute("message", "You must be logged in first.");
            resp.sendRedirect("login");
        }
    }

}

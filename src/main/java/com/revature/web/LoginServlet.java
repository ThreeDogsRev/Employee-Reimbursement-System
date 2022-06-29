package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.FakeDao;
import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    EmployeeService employeeService = new EmployeeService(new FakeDao());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("employeeHome");
        } else {
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String messageAttributeKey = "message";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Employee user = null;

        try {
            user = this.employeeService.confirmLogin(username, password);
            request.getSession().setAttribute("user", user);
        } catch (UserNotFoundException e) {
            request.setAttribute(messageAttributeKey, "User not found");
        } catch (PasswordInvalidException e) {
            request.setAttribute(messageAttributeKey, "Password invalid");
        } catch (Exception e) {
            request.setAttribute(messageAttributeKey, e.getMessage());
        }
        if (user == null) {
            try {
                request.getRequestDispatcher("login.html").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        } else {
            response.sendRedirect("employeeHome");
        }

    }

}

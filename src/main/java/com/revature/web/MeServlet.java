package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;
import com.revature.utils.FormInputValidator;

@WebServlet("/me")
public class MeServlet extends HttpServlet {

    private ObjectMapper om;
    private EmployeeService es;

    public MeServlet() {
        this.om = new ObjectMapper();
        this.om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.es = new EmployeeService(new EmployeeDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        Employee employee = (Employee) request.getSession().getAttribute("user");
        String json = this.om.writeValueAsString(employee);
        PrintWriter out = resp.getWriter();
        out.write(json);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");

        Employee employee = (Employee) req.getSession().getAttribute("user");

        if (currentPassword == null || currentPassword.isEmpty()) {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.write("{\"error\": \"Please enter your currentPassword password\"}");
            return;
        }

        if (!currentPassword.equals(((Employee) req.getSession().getAttribute("user")).getPassword())) {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.write("{\"error\": \"Your password was incorrect\"}");
            return;
        }

        // validate firstname
        if (firstname != null && !firstname.isEmpty()) {
            if (!FormInputValidator.checkName(firstname.toLowerCase())) {
                write(resp, "Invalid first name");
                return;
            }
            employee.setFirstName(firstname.toLowerCase());
        }

        // validate lastname
        if (lastname != null && !lastname.isEmpty()) {
            if (!FormInputValidator.checkName(lastname.toLowerCase())) {
                write(resp, "Invalid last name");
                return;
            }
            employee.setLastName(lastname.toLowerCase());
        }

        // validate username
        if (username != null && !username.isEmpty()) {
            if (!FormInputValidator.checkName(username)) {
                write(resp, "Invalid username");
                return;
            }
            employee.setUsername(username);
        }

        // validate email
        if (email != null && !email.isEmpty()) {
            if (!FormInputValidator.checkEmail(email.toLowerCase())) {
                write(resp, "Invalid Email");
                return;
            }
            employee.setEmail(email.toLowerCase());
        }

        // validate password
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!FormInputValidator.checkPassword(newPassword)) {
                write(resp, "New Password is invalid");
                return;
            }
            employee.setPassword(newPassword);
        }

        employee = this.es.updateEmployee(employee);
        req.getSession().setAttribute("user", employee);
        String json = this.om.writeValueAsString(employee);
        PrintWriter out = resp.getWriter();
        out.write(json);
    }

    private void write(HttpServletResponse resp, String string) {
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        out.write("<p>" + string + "</p>");
        out.close();
    }

}

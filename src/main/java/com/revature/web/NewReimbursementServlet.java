package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.service.EmployeeService;
import com.revature.utils.FormInputValidator;

@WebServlet("/new-reimbursement")
public class NewReimbursementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeService es = new EmployeeService(new EmployeeDao());

        // 1. extract all values from the parameters
        String amount = req.getParameter("amount");
        String type = req.getParameter("type");
        String description = req.getParameter("description");
        Long parsedAmount;

        // TODO Use logger instead of using System.out to log new reimbursement requests

        if (amount == null || amount.isEmpty()) {
            resp.getWriter().write("Amount is required");
            return;
        }

        if (type == null || type.isEmpty()) {
            resp.getWriter().write("Type is required");
            return;
        }

        if (description == null || description.isEmpty()) {
            resp.getWriter().write("Description is required");
            return;
        }

        // 2. validate the values
        try {
            parsedAmount = ((Double) (Double.parseDouble(amount) * 100)).longValue();
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().write("Could not parse amount");
            return;
        }

        if (!FormInputValidator.isValidType(type)) {
            resp.setStatus(400);
            resp.getWriter().write("Invalid type");
            return;
        }
        if (!FormInputValidator.isValidDescription(description)) {
            resp.setStatus(400);
            resp.getWriter().write("Invalid description");
            return;
        }

        // Check if user is logged in
        Employee user = (Employee) req.getSession().getAttribute("user");
        if (user == null) {
            resp.setStatus(401);
            resp.getWriter().write("You must be logged in to submit a reimbursement");
            return;
        }

        // 2. construct a new reimbursement object
        user.addReimbursement(
            new Reimbursement(parsedAmount, ReimbursementType.valueOf(type.toUpperCase()), description));

        try {
            // 3. save the reimbursement object to the database and the session
            es.updateEmployee(user);
            resp.setStatus(200);
            resp.getWriter().write("Reimbursement submitted successfully");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Something went wrong");
        }
    }

}

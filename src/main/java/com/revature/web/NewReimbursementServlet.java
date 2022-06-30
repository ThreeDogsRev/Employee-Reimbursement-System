package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.Dao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.service.EmployeeService;
import com.revature.utils.FormInputValidator;

@WebServlet("/new-reimbursement")
public class NewReimbursementServlet extends HttpServlet {


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    EmployeeService es = new EmployeeService(new Dao());

    // 1. extract all values from the parameters
    String amount = req.getParameter("amount");
    String type = req.getParameter("type");
    String description = req.getParameter("description");
    Long parsedAmount = 0L;

    System.out.println("NewReimbursementServlet.doPost()");
    System.out.println("amount: <" + amount + ">");
    System.out.println("type: <" + type + ">");
    System.out.println("description: <" + description + ">");

    if(amount == null || amount.isEmpty()) {
      resp.getWriter().write("Amount is required");
      return;
    }

    if(type == null || type.isEmpty()) {
      resp.getWriter().write("Type is required");
      return;
    }

    if(description == null || description.isEmpty()) {
      resp.getWriter().write("Description is required");
      return;
    }

    // 2. validate the values
    FormInputValidator fiv = new FormInputValidator();
    try {
      parsedAmount = parseAmount(amount);
    } catch (NumberFormatException e) {
      resp.setStatus(400);
      resp.getWriter().write("Invalid amount, format should be 123.42 or 123,234.42");
      return;
    }

    if (!fiv.isValidType(type)) {
      resp.setStatus(400);
      resp.getWriter().write("Invalid type");
      return;
    }
    if (!fiv.isValidDescription(description)) {
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
    user.addReimbursement(new Reimbursement(
        parsedAmount,
        ReimbursementType.valueOf(type.toUpperCase()),
        description));

    try {
      // 3. save the reimbursement object to the database and the session
      es.updateEmployee(user);
      req.getSession().setAttribute("user", user);
      resp.setStatus(200);
      resp.getWriter().write("Reimbursement submitted successfully");
    } catch (Exception e) {
      resp.setStatus(500);
      resp.getWriter().write("Something went wrong");
      return;
    }
  }

  private static long parseAmount(String amount) throws NumberFormatException {
    // Enforced 2 decimal places, so we can round to the nearest cent
    // optional commas are allowed, so we need to remove them
    if(!amount.matches("([0-9]{1,3},)*[0-9]*\\.[0-9]{2}")){
      throw new NumberFormatException("Invalid amount");
    }
    return Math.round(Double.parseDouble(amount.replace(",", "")) * 100);
  }

}

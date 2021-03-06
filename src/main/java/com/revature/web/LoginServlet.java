package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  EmployeeService employeeService = new EmployeeService(new EmployeeDao());

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.addHeader("Access-Control-Allow-Origin", "*");

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Employee user = null;
    String message = "";

    try {
      user = employeeService.confirmLogin(username, password);
      System.out.println(user.getUsername() + " logged in successfully");
      request.getSession().setAttribute("user", user);
    } catch (UserNotFoundException e) {
      message = "User not found";
    } catch (PasswordInvalidException e) {
      message = "Password is invalid";
    } catch (Exception e) {
      message = "Something went wrong";
    }
    if(user != null) {
      try {
        response.sendRedirect("/employee-reimbursement-system");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      response.getWriter().write(new ObjectMapper().writeValueAsString(message));
    }
  }

}

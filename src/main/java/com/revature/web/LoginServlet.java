package com.revature.web;

import javax.servlet.RequestDispatcher;
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Employee user = null;

    try {
      user = employeeService.confirmLogin(username, password);
      System.out.println(user.getUsername() + " logged in successfully");
      request.getSession().setAttribute("user", user);
    } catch (UserNotFoundException e) {
      request.setAttribute("message", "User not found");
    } catch (PasswordInvalidException e) {
      request.setAttribute("message", "Password Invalid");
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
    }
    RequestDispatcher dispatcher = (user == null)
    ? request.getRequestDispatcher("index.html")
    : request.getRequestDispatcher("employeeHomePage.html");
    try{
      dispatcher.forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e);
    }


  }

}

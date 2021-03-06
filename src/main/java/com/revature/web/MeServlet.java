package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;
import com.revature.utils.FormInputValidator;

@WebServlet("/me")
public class MeServlet extends HttpServlet {
  ObjectMapper om;
  EmployeeService es;

  public MeServlet() {
    om = new ObjectMapper();
    om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    es = new EmployeeService(new EmployeeDao());
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.addHeader("Access-Control-Allow-Origin", "*");

    System.out.println("doGet()");
    Employee employee = (Employee) request.getSession().getAttribute("user");
    System.out.println(employee);
    String json = om.writeValueAsString(employee);
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
    System.out.println("POST: /me");
    System.out.println("firstname: " + firstname);
    System.out.println("lastname: " + lastname);
    System.out.println("username: " + username);
    System.out.println("email: " + email);
    System.out.println("currentPassword: " + currentPassword);
    System.out.println("newPassword: " + newPassword);
    System.out.println("employee: " + employee);

    if (currentPassword == null || currentPassword.isEmpty()) {
      resp.setStatus(400);
      PrintWriter out = resp.getWriter();
      out.write("{\"error\": \"Please enter your currentPassword password\"}");
      return;
    }

    if (!currentPassword.equals(((Employee) req.getSession().getAttribute("user")).getPassword())){
      resp.setStatus(400);
      PrintWriter out = resp.getWriter();
      out.write("{\"error\": \"Your password was incorrect\"}");
      return;
    }

    // validate firstname
    if (firstname != null && !firstname.isEmpty()) {
      if (FormInputValidator.checkName(firstname.toLowerCase())) {
        employee.setFirstName(firstname.toLowerCase());
      } else {
        write(resp, "Invalid first name");
        return;
      }
    }

    // validate lastname
    if (lastname != null && !lastname.isEmpty()) {
      if (FormInputValidator.checkName(lastname.toLowerCase())) {
        employee.setLastName(lastname.toLowerCase());
      } else {
        write(resp, "Invalid last name");
        return;
      }
    }

    // validate username
    if (username != null && !username.isEmpty()) {
      if (FormInputValidator.checkName(username)) {
        employee.setUsername(username);
      } else {
        write(resp, "Invalid username");
        return;
      }
    }

    // validate email
    if (email != null && !email.isEmpty()) {
      if (FormInputValidator.checkEmail(email.toLowerCase())) {
        employee.setEmail(email.toLowerCase());
      } else {
        write(resp, "Invalid Email");
        return;
      }
    }

    // validate password
    if (newPassword != null && !newPassword.isEmpty()) {
      if (FormInputValidator.checkPassword(newPassword)) {
        employee.setPassword(newPassword);
      } else {
        write(resp, "New Password is invalid");
        return;
      }
    }

    employee = es.updateEmployee(employee);
    req.getSession().setAttribute("user", employee);
    String json = om.writeValueAsString(employee);
    PrintWriter out = resp.getWriter();
    out.write(json);
  }

  private void write(HttpServletResponse resp, String string) {
    PrintWriter out = null;
    try {
      out = resp.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    out.write("<p>" + string + "</p>");
  }
}

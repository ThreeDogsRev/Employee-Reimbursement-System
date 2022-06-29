package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.dao.Dao;
import com.revature.dao.FakeDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {

  private static EmployeeService es = new EmployeeService(new FakeDao());
  private static ObjectMapper om = new ObjectMapper();

  public EmployeesServlet() {
    super();
    om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // Todo: Check if a user is logged in and has permissions to view this page
    List<Employee> employees = new ArrayList<>();
    resp.setContentType("application/json");
    resp.addHeader("Access-Control-Allow-Origin", "*");
    String query = req.getQueryString();
    if (query != null) {
      String[] queryParams = query.split("&");
      for (String param : queryParams) {
        String[] keyValue = param.split("=");
        if (keyValue[0].equals("id")) {
          Employee emp = null;
          try {
            emp = es.getEmployee(Integer.parseInt(keyValue[1]));
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (emp != null) {
            employees.add(emp);
          }
        } else if (keyValue[0].equals("name")) {
          Employee emp = null;
          try {
            emp = es.getEmployee(keyValue[1]);
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (emp != null) {
            employees.add(emp);
          }
        }
      }
    } else {
      employees = es.getEmployees();
    }

    // NUll the employees author information because it causes an infinate relationship loop
    for(Employee emp : employees) {
      for(Reimbursement r : emp.getReimbursements()) {
        r.setAuthor(null);
      }
    }


    String json = om.writeValueAsString(employees);
    PrintWriter out = resp.getWriter();
    out.write(json);
  }
}

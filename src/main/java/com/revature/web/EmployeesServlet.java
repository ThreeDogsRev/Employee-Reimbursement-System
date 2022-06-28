package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.Dao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {

  private static EmployeeService es = new EmployeeService(new Dao());
  private static ObjectMapper om = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // Todo: Check if a user is logged in and has permissions to view this page
    resp.setContentType("application/json");
    resp.addHeader("Access-Control-Allow-Origin", "*");
    List<Employee> employees = es.getEmployees();

    // for (Employee e : employees) {
    //   e.setPassword(null);
    //   for (Reimbursement r : e.getReimbursements()) {
    //     r.setAuthor(null);
    //   }
    // }
    String json = om.writeValueAsString(employees);
    PrintWriter out = resp.getWriter();
    out.write(json);
  }

  public static void main(String[] args) throws JsonProcessingException {
    List<Employee> employees = es.getEmployees();
    System.out.println(om.writeValueAsString(employees));
    
  }
}

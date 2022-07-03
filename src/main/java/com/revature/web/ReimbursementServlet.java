package com.revature.web;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.dao.EmployeeDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.service.ReimbursementService;

@WebServlet("/reimbursements")
public class ReimbursementServlet extends HttpServlet {
  private static ReimbursementService rs = new ReimbursementService(new EmployeeDao());
  private static ObjectMapper om = new ObjectMapper();

  public ReimbursementServlet() {
    super();
    om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // Todo: Check if a user is logged in and has permissions to view this page
    resp.setContentType("application/json");
    resp.addHeader("Access-Control-Allow-Origin", "*");
    System.out.println("GET: /reimbursements");
    System.out.println("User: " + req.getSession().getAttribute("user"));
    
    List<Reimbursement> reimbursements = rs.getReimbursementsCopy();
    String query = req.getQueryString();
    if(query != null){
      String[] queryParams = query.split("&");
      for(String param : queryParams){
        String[] keyValue = param.split("=");

        if(keyValue[0].equals("status")){
          reimbursements = reimbursements.stream()
            .filter(predicate -> predicate.getStatus()
              .equals(ReimbursementStatus.valueOf(keyValue[1].toUpperCase())))
            .collect(Collectors.toList());
        }
        if(keyValue[0].equals("type")){
          reimbursements = reimbursements.stream()
            .filter(predicate -> predicate.getType()
              .equals(ReimbursementType.valueOf(keyValue[1].toUpperCase())))
            .collect(Collectors.toList());
        }
      }
    }
    reimbursements = reimbursements.stream()
      .map((Reimbursement r) -> {
        return new Reimbursement(r) {
          public String submitter = r.getAuthor().getFirstName() + " " + r.getAuthor().getLastName();
        };
      }).collect(Collectors.toList());


    String json = om.writeValueAsString(reimbursements);
    System.out.println(json);
    PrintWriter out = resp.getWriter();
    out.write(json);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    return;
  }

}

package com.revature.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.revature.dao.EmployeeDao;
import com.revature.dao.FakeDao;
import com.revature.dao.IDao;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.service.EmployeeService;
import com.revature.service.ReimbursementService;
import com.revature.utils.FormInputValidator;
import com.revature.utils.SessionHelper;

@WebServlet("/update-reimbursement")
public class ProcessReimbursementServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    EmployeeService es = new EmployeeService(new EmployeeDao());
    ReimbursementService rs = new ReimbursementService(new EmployeeDao());

    // This is an odd case where the manager is trying to process a reimbursement directly without going
    // through the author object so im breaking the abstraction for the sake of effeciency.
    IDao<Reimbursement> rdao = new IDao<Reimbursement>(){
      @Override
      @Deprecated
      public List<Reimbursement> selectAll() {
        return null;
      }

      @Override
      public Reimbursement selectById(int id) throws SQLException {
        Reimbursement reimbursement = null;
        Session session = null;
        try {
          session = SessionHelper.getSession();
          session.beginTransaction();
          reimbursement = session.get(Reimbursement.class, id);
        } catch (HibernateException e) {
          e.printStackTrace();
        } finally {
          try {
            if(session != null) session.close();
          } catch(Exception e) {
            e.printStackTrace();
          }
        }
        return reimbursement;
      }

      @Override
      @Deprecated
      public Reimbursement selectByUsername(String userName) {
        return null;
      }
    };

    Employee user = null;
    Reimbursement reimbursement = null;

    // 1. extract all values from the parameters
    String id = req.getParameter("id");
    String amount = req.getParameter("amount");
    String description = req.getParameter("description");
    String status = req.getParameter("status");
    String type = req.getParameter("type");

    System.out.println("NewReimbursementServlet.doPost()");
    System.out.println("id: <" + id + ">");
    System.out.println("amount: <" + amount + ">");
    System.out.println("description: <" + description + ">");
    System.out.println("status: <" + status + ">");
    System.out.println("type: <" + type + ">");

    // check if user has permissions to modify a reimbursement
    user = (Employee) req.getSession().getAttribute("user");
    if(user == null) {
      resp.setStatus(401);
      resp.getWriter().write("You must be logged in to process a reimbursement");
      return;
    }
    if(!(user.getEmployeeRole().equals(EmployeeRole.ADMIN) || user.getEmployeeRole().equals(EmployeeRole.MANAGER))){
      resp.setStatus(403);
      resp.getWriter().write("You must be an admin or manager to process a reimbursement");
      return;
    }

    if(id == null || id.isEmpty()) {
      resp.setStatus(400);
      resp.getWriter().write("Id is required");
      return;
    }

    // 1 lookup the reimbursement by id
    try{
      reimbursement = rdao.selectById(Integer.parseInt(id));
      if(reimbursement == null) {
        resp.setStatus(204);
        resp.getWriter().write("Reimbursement Does not Exist");
        return;
      }
    } catch (NumberFormatException e) {
      resp.setStatus(400);
      resp.getWriter().write("Invalid id, should be an integer");
      return;
    } catch (SQLException e) {
      resp.setStatus(500);
      resp.getWriter().write("Internal Server Error");
      return;
    }


    // 2 validate and assign values to the reimbursement
    if(!(amount == null || amount.isEmpty())) {
      try {
        reimbursement.setAmount(FormInputValidator.parseAmount(amount));
      } catch (NumberFormatException e) {
        resp.setStatus(400);
        resp.getWriter().write("Invalid amount, should be a 123.21");
        return;
      }
    }

    if(!(description == null || description.isEmpty())) {
      if(!FormInputValidator.isValidDescription(description)){
        resp.setStatus(400);
        resp.getWriter().write("Description is in the wrong format");
        return;
      }
      reimbursement.setDescription(description);
    }

    if(!(status == null || status.isEmpty())) {
      if(!FormInputValidator.isValidStatus(status)){
        resp.setStatus(400);
        resp.getWriter().write("Status must be one of the following: PENDING, APPROVED, DENIED");
        return;
      }
      ReimbursementStatus parsedStatus = ReimbursementStatus.valueOf(status);
      reimbursement.setStatus(parsedStatus);
      if(parsedStatus.equals(ReimbursementStatus.APPROVED) || parsedStatus.equals(ReimbursementStatus.DENIED)) {
        reimbursement.setResolver(user);
      }
      if(parsedStatus.equals(ReimbursementStatus.PENDING)) {
        reimbursement.setResolver(null);
      }
    }

    if(!(type == null || type.isEmpty())) {
      if(!FormInputValidator.isValidType(type)){
        resp.setStatus(400);
        resp.getWriter().write("Type must be one of the following: Lodging, Travel, Food, Other");
        return;
      }
      reimbursement.setType(ReimbursementType.valueOf(type.toUpperCase()));
    }
    rdao.update(reimbursement);
    resp.setStatus(200);
    resp.getWriter().write("Reimbursement Updated");
  }
}

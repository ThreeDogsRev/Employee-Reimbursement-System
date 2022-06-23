package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.utils.SessionHelper;

public class ReimbursementDao implements IDao<Reimbursement> {


  @Override
  public List<Reimbursement> selectAll() {
    List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
    try {
      Session session = SessionHelper.getSession();
      Query query = session.createQuery("FROM Reimbursement");
      reimbursements = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return reimbursements;
  }

  @Override
  public Reimbursement selectById(int id) {
    try {
      Session session = SessionHelper.getSession();
      session.beginTransaction();
      Reimbursement entity = (Reimbursement) session.get(Reimbursement.class, id);
      session.getTransaction().commit();
      return entity;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    List<Reimbursement> reimbursements;
    ReimbursementDao reimbursementDao = new ReimbursementDao();
    EmployeeDao employeeDao = new EmployeeDao();
    
    Employee james_may = new Employee("James", "May", "jMay", "Slow", "Email", EmployeeRole.EMPLOYEE);
    Employee richard_hammond = new Employee("Richard", "Hammond", "rHammond ", "Short", "Email2", EmployeeRole.EMPLOYEE);
    Reimbursement beer = new Reimbursement(1000L, ReimbursementType.FOOD, "Beer Money" );
    Reimbursement lodging = new Reimbursement(10000L, ReimbursementType.LODGING, "Hotel Money");
    Reimbursement flight = new Reimbursement(12000L, ReimbursementType.TRAVEL, "Flight");
    james_may.addReimbursement(beer);
    richard_hammond.addReimbursement(lodging);
    employeeDao.insert(james_may);
    employeeDao.insert(richard_hammond);
    Employee employee = employeeDao.selectAll().get(1);
    System.out.println(employee);
    System.out.println(employee.getReimbursments());

    employee.addReimbursement(flight);
    employeeDao.update(employee);

    employee = employeeDao.selectById(employee.getId());
    System.out.println(employee);
    System.out.println(employee.getReimbursments());
  }

}
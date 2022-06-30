package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.IDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class ReimbursementService {
  private IDao<Employee> dao;

  public ReimbursementService(IDao<Employee> ed) {
    this.dao = ed;
  }

  public List<Reimbursement> getReimbursementsCopy() {
    List<Reimbursement> reimbursements = new ArrayList<>();
    List<Employee> employees = dao.selectAll();
    for(Employee employee: employees) {
      for(Reimbursement reimbursement: employee.getReimbursements()) {
        Employee e = new Employee(employee);
        Reimbursement r = new Reimbursement(reimbursement);
        e.setReimbursements(null);
        r.setAuthor(e);
        reimbursements.add(r);
      }
    }
    return reimbursements;
  }

  public List<Reimbursement> getReimbursements() {
    List<Reimbursement> reimbursements = new ArrayList<>();
    List<Employee> employees = dao.selectAll();
    for(Employee employee: employees) {
      for(Reimbursement reimbursement: employee.getReimbursements()) {
        reimbursements.add(reimbursement);
      }
    }
    return reimbursements;
  }
}

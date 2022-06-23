package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.Dao;
import com.revature.models.*;;

public class ReimbursementService {
  private Dao ed;

  public ReimbursementService(Dao ed) {
    this.ed = ed;
  }

  public List<Reimbursement> getReimbursements() {
    List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
    List<Employee> employees = ed.selectAll();
    for(Employee employee: employees) {
      for(Reimbursement reimbursement: employee.getReimbursements()) {
        reimbursement.setAuthor(employee);
        reimbursements.add(reimbursement);
      }
    }
    return reimbursements;
  }
  
}

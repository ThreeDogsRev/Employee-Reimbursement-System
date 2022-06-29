package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.Dao;
import com.revature.dao.FakeDao;
import com.revature.dao.IDao;
import com.revature.models.*;;

public class ReimbursementService {
  private IDao dao;

  public ReimbursementService(IDao ed) {
    this.dao = ed;
  }

  public List<Reimbursement> getReimbursementsCopy() {
    List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
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

}

package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;

public class FakeDao extends HashMap<Integer, Employee> implements IDao<Employee> {

  public FakeDao() {
    super();
    Employee e1 = new Employee("Alice", "Able", "atest", "test", "test@test", EmployeeRole.EMPLOYEE);
    Employee e2 = new Employee("Bob", "Bobby", "btest", "test", "test2@test", EmployeeRole.EMPLOYEE);
    e1.addReimbursement(new Reimbursement(1000L, ReimbursementType.FOOD, "Wendies"));
    e1.addReimbursement(new Reimbursement(200000L, ReimbursementType.LODGING, "Hotel California"));
    e1.addReimbursement(new Reimbursement(100000L, ReimbursementType.TRAVEL, "American Airlines"));
    this.insert(e1);
    this.insert(e2);
  }

  @Override
  public Employee insert(Employee entity) {
    entity.setId(this.size());
    super.put(this.size(), entity);
    return entity;
  }

  @Override
  public List<Employee> selectAll() {
    return new ArrayList<Employee>(this.values());
  }

  @Override 
  public void persist (Employee entity) {
    super.put(entity.getId(), entity);
  }

  @Override
  public Employee selectById(int id) throws SQLException {
    if(super.containsKey(id)) {
      return super.get(id);
    }
    throw new SQLException("employee not found");
  }

  @Override
  public Employee selectByUsername(String userName) {
    for (Employee employee : this.values()) {
      if (employee.getUsername().equals(userName)) {
        return employee;
      }
    }
    return null;
  }


}


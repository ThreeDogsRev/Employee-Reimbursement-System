package com.revature.models.employee;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_rolls")
public class EmployeeRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id")
    private int user_role_id;

    @Column(name = "user_role")
    private EmployeeRoleEnum employeeType;

    public EmployeeRoles(int user_role_id, EmployeeRoleEnum userRole) {
        super();
        this.user_role_id = user_role_id;
        this.employeeType = userRole;
    }

    public EmployeeRoles(EmployeeRoleEnum userRole) {
        super();
        this.employeeType = userRole;
    }

    public EmployeeRoles() {
        super();
    }

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public EmployeeRoleEnum getUserRole() {
        return employeeType;
    }

    public void setUserRole(EmployeeRoleEnum userRole) {
        this.employeeType = userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeType, user_role_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmployeeRoles other = (EmployeeRoles) obj;
        return employeeType == other.employeeType && user_role_id == other.user_role_id;
    }

    @Override
    public String toString() {
        return "UserRoles [user_role_id=" + user_role_id + ", userRole=" + employeeType + "]";
    }

}

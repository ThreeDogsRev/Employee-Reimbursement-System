package com.revature.models.employee;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.revature.models.PostgreSQLEnumType;
import com.revature.models.reimbursement.Reimbursement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity(name = "Employee")
@Table(name = "employee")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String fristName;

    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 6, max = 50)
    @Column(name = "user_name", unique = true)
    private String userName;

    @Size(min = 8, max = 50)
    @Column(name = "pwd")
    private String password;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role", columnDefinition = "ers.employee_role")
    @Type(type = "pgsql_enum")
    private EmployeeRole employeeRole;

    public Employee(int id, String fristName, String lastName, String userName, String password, String email,
            EmployeeRole employeeRole) {
        super();
        this.id = id;
        this.fristName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    public Employee(String fristName, String lastName, String userName, String password, String email,
            EmployeeRole employeeRole) {
        super();
        this.fristName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    public Employee() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, fristName, id, lastName, password, userName, employeeRole);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        return Objects.equals(email, other.email) && Objects.equals(fristName, other.fristName) && id == other.id
                && Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
                && Objects.equals(userName, other.userName) && employeeRole == other.employeeRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeRole getEmployeeRole() {
        return this.employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public List<Reimbursement> getReimbursements() {
        return reimbursements;
    }

    public void setReimbursements(List<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", fristName=" + fristName + ", lastName=" + lastName + ", userName=" + userName
                + ", password=" + password + ", email=" + email + ", employeeRole=" + employeeRole + "]";
    }

}

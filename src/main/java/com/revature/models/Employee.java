package com.revature.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity(name = "Employee")
@Table(name = "employee")
@JsonIgnoreProperties(value = { "password" })
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;

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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author")
    private List<Reimbursement> reimbursements = new ArrayList<>();

    public Employee(Employee employee) {
        this.id = employee.id;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.userName = employee.userName;
        this.password = employee.password;
        this.email = employee.email;
        this.employeeRole = employee.employeeRole;
        this.reimbursements = employee.reimbursements;
    }

    public Employee(int id, String fristName, String lastName, String userName, String password, String email,
            EmployeeRole employeeRole) {
        this.id = id;
        this.firstName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    public Employee(String fristName, String lastName, String userName, String password, String email,
            EmployeeRole employeeRole) {
        this.firstName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    public Employee() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String fristName) {
        this.firstName = fristName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
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
        return this.reimbursements;
    }

    public void setReimbursements(List<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.firstName, this.id, this.lastName, this.password, this.userName,
            this.employeeRole);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee other = (Employee) obj;
        return Objects.equals(this.email, other.email) && Objects.equals(this.firstName, other.firstName)
            && this.id == other.id && Objects.equals(this.lastName, other.lastName)
            && Objects.equals(this.password, other.password) && Objects.equals(this.userName, other.userName)
            && this.employeeRole == other.employeeRole;
    }

    @Override
    public String toString() {
        return "Employee:" + this.id + " " + this.email + " " + this.employeeRole + " " + this.firstName + " "
                + this.lastName + " " + this.password + " " + this.userName;
    }

    public boolean addReimbursement(Reimbursement reimbursement) {
        reimbursement.setAuthor(this);
        return this.reimbursements.add(reimbursement);
    }

    public void removeReimbursement(Reimbursement r) {
        this.reimbursements.remove(r);
    }

}

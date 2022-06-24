package com.revature.models;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity(name = "Employee")
@Table(name = "employee")
public class Employee {

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author")
    private List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();

    public Employee(int id, String fristName, String lastName, String userName, String password, String email,
            EmployeeRole employeeRole) {
        super();
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
        super();
        this.firstName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }


    public Employee() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fristName) {
        this.firstName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
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
        return employeeRole;
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
    public int hashCode() {
        return Objects.hash(email, firstName, id, lastName, password, userName, employeeRole);
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
        return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
                && Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
                && Objects.equals(userName, other.userName) && employeeRole == other.employeeRole;
    }

    @Override
    public String toString() {
        return "Employee:" + id + " " + email + " " + employeeRole + " " + firstName + " " + lastName + " " + password + " "+ userName;
    }

    public boolean addReimbursement(Reimbursement reimbursement) {
        reimbursement.setAuthor(this);
        return this.reimbursements.add(reimbursement);
    }

    public void removeReimbursement(Reimbursement r) {
        this.reimbursements.remove(r);
    }


}

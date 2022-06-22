package com.revature.models.employee;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.revature.models.PostgreSQLEnumType;
import com.revature.models.reimbursement.Reimbursement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity(name = "Employee")
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid")
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

    @OneToMany
    @JoinColumn(name = "author_id", referencedColumnName = "eid")
    private Set<Reimbursement> reimbursements;

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

    @Override
    public String toString() {
        return "Users [id=" + id + ", fristName=" + fristName + ", lastName=" + lastName + ", userName=" + userName
                + ", password=" + password + ", email=" + email + ", employeeRole=" + employeeRole + "]";
    }

    public Object getUserName() {
        return this.userName;
    }

    public Object getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }

    public void setReimbursements(Set<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    public String getFirstName() {
        return this.fristName;
    }

    public void setFirstName(String firstName) {
        this.fristName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;        
    }

}

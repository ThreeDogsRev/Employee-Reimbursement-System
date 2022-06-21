package com.revature.models.employee;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.revature.models.reimbursement.Reimbursement;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String fristName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "pwd")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user_role_id", fetch = FetchType.LAZY)
    private int user_role_id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_reimbursement")
    private List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();

    public Employee(int id, String fristName, String lastName, String userName, String password, String email,
            int user_role_id) {
        super();
        this.id = id;
        this.fristName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.user_role_id = user_role_id;
    }

    public Employee(String fristName, String lastName, String userName, String password, String email,
            int user_role_id) {
        super();
        this.fristName = fristName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.user_role_id = user_role_id;
    }

    public Employee() {
        super();
    }

    // TODO This method does nothing
    public void addReimbursement(Reimbursement reimbursement) {
        if (reimbursements == null) {
            reimbursements = new ArrayList<Reimbursement>();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, fristName, id, lastName, password, userName, user_role_id);
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
                && Objects.equals(userName, other.userName) && user_role_id == other.user_role_id;
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

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
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
                + ", password=" + password + ", email=" + email + ", user_role_id=" + user_role_id + "]";
    }

}

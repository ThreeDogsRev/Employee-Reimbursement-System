package com.revature.modles;

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

import com.revature.enums.Roles;

@Entity
@Table(name="user_role")
public class UserRoles {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "user_role_id")
    private int user_role_id;
    @Column(name = "user_role")
    private Roles userRole;
    public UserRoles(int user_role_id, Roles userRole) {
        super();
        this.user_role_id = user_role_id;
        this.userRole = userRole;
    }
    public UserRoles(Roles userRole) {
        super();
        this.userRole = userRole;
    }
    public UserRoles() {
        super();
    }
    
    public int getUser_role_id() {
        return user_role_id;
    }
    
    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }
    
    public Roles getUserRole() {
        return userRole;
    }
    
    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }
    @Override
    public int hashCode() {
        return Objects.hash(userRole, user_role_id);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRoles other = (UserRoles) obj;
        return userRole == other.userRole && user_role_id == other.user_role_id;
    }
    @Override
    public String toString() {
        return "UserRoles [user_role_id=" + user_role_id + ", userRole=" + userRole + "]";
    }
    
    
    
}

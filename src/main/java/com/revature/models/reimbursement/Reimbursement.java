package com.revature.models.reimbursement;

import java.sql.Blob;
import java.sql.Types;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.revature.models.PostgreSQLEnumType;
import com.revature.models.employee.Employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity(name = "Reimbursement")
@Table(name = "reimbursements")
public class Reimbursement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rid")
  private int id;

  @Column(nullable = false, name = "submit_date")
  private Date submitDate;

  /*
   * Stores the amount of the reimbursement denominated in cents
   */
  @NotNull
  @Min(value = 0)
  private Long amount;

  @Column(nullable = false, length = 250)
  private String description;

  @Column(nullable = false)
  private ReimbursementStatus status;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id", referencedColumnName = "eid")
  private Employee author;

  @Column(name = "resolve_date")
  private Date resolveDate;

  @Column(name = "resolver_id")
  private int resolverId;

  private Blob receipt;


  public Reimbursement() {
  }

  public Reimbursement(int id, Date submitDate, Long amount, String description, ReimbursementStatus status,
      Employee author, Date resolveDate, int resolverId, Blob receipt) {
    this.id = id;
    this.submitDate = submitDate;
    this.amount = amount;
    this.description = description;
    this.status = status;
    this.author= author;
    this.resolveDate = resolveDate;
    this.resolverId = resolverId;
    this.receipt = receipt;
  }

  public Reimbursement(Date submitDate, Long amount, String description, ReimbursementStatus status, Employee author,
      Date resolveDate, int resolverId, Blob receipt) {
    this.submitDate = submitDate;
    this.amount = amount;
    this.description = description;
    this.status = status;
    this.author = author;
    this.resolveDate = resolveDate;
    this.resolverId = resolverId;
    this.receipt = receipt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.amount, this.author, this.id, this.submitDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Reimbursement other = (Reimbursement) obj;
    return Double.doubleToLongBits(this.amount) == Double.doubleToLongBits(other.amount)
        && this.author == other.author && this.id == other.id
        && Objects.equals(this.submitDate, other.submitDate);
  }

  @Override
  public String toString() {
    return "Reimbursement [id=" + this.id + ", submitDate=" + this.submitDate + ", amount=" + this.amount
        + ", description=" + this.description + ", status=" + this.status + ", authorId=" + this.author.getId()
        + ", resolveDate=" + this.resolveDate + ", resolverId=" + this.resolverId + "]";
  }

  public void setDescription(String string) {
    this.description = string;
  }

  public void setAmount(long l) {
    this.amount = l;
  }

  public void setStatus(ReimbursementStatus status) {
    this.status = status;
  }
}

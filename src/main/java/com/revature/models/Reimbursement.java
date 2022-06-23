package com.revature.models;

import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity(name = "Reimbursement")
@Table(name = "reimbursement")
public class Reimbursement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;


  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "ers.reimbursement_status")
  @Type(type = "pgsql_enum")
  private ReimbursementStatus status;


  @Enumerated(EnumType.STRING)
  @Column(name = "type", columnDefinition = "ers.reimbursement_type")
  @Type(type = "pgsql_enum")
  private ReimbursementType type;


  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "submit_date", nullable = false)
  private Date submitDate;

  /*
   * Stores the amount of the reimbursement denominated in cents
   */
  @NotNull
  @Min(value = 0)
  private Long amount;


  @Column(nullable = false, length = 250)
  private String description;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false, referencedColumnName="id")
  private Employee author;


  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modify_date")
  private Date lastModified;


  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resolver_id")
  private Employee resolver;


  private Blob receipt;

  public Reimbursement() {}

  public Reimbursement(int id, Date submitDate, Long amount, String description, ReimbursementStatus status,
      ReimbursementType type, Employee author, Date lastModified, Employee resolver, Blob receipt) {
    this.id = id;
    this.submitDate = submitDate;
    this.amount = amount;
    this.description = description;
    this.status = status;
    this.type = type;
    this.author = author;
    this.lastModified = lastModified;
    this.resolver = resolver;
    this.receipt = receipt;
  }

  public Reimbursement(Long amount, ReimbursementType type, String description) {
    this.amount = amount;
    this.type = type;
    this.description = description;
    this.status = ReimbursementStatus.PENDING;
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
    return Double.doubleToLongBits(this.amount) == Double.doubleToLongBits(other.amount) && this.author == other.author
        && this.id == other.id && Objects.equals(this.submitDate, other.submitDate);
  }

  @Override
  public String toString() {
    return "Reimbursement [id=" + this.id + ", submitDate=" + this.submitDate + ", amount=" + this.amount
        + ", description=" + this.description + ", status=" + this.status + ", authorId=" + this.author.getId()
        + ", resolveDate=" + this.lastModified + ", resolverId=" + this.lastModified + "]";
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

  public Employee getAuthor() {
    return this.author;
  }

  public void setEmployee(Employee employee) {
    this.author = employee;
  }

}

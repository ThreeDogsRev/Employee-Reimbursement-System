package com.revature.modles;

import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "reimbursements")
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "submit_date")
    private Date submitDate;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false)
    private ReimbursementStatus status;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @Column(name = "author_id")
    private int authorId;

    @Column(name = "resolve_date")
    private Date resolveDate;

    @Column(name = "resolver_id")
    private int resolverId;

    private Blob receipt;

    public Reimbursement() {}

    public Reimbursement(int id, Date submitDate, double amount, String description, ReimbursementStatus status,
            int authorId, Date resolveDate, int resolverId, Blob receipt) {
        this.id = id;
        this.submitDate = submitDate;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.authorId = authorId;
        this.resolveDate = resolveDate;
        this.resolverId = resolverId;
        this.receipt = receipt;
    }

    public Reimbursement(Date submitDate, double amount, String description, ReimbursementStatus status, int authorId,
            Date resolveDate, int resolverId, Blob receipt) {
        this.submitDate = submitDate;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.authorId = authorId;
        this.resolveDate = resolveDate;
        this.resolverId = resolverId;
        this.receipt = receipt;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSubmitDate() {
        return this.submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReimbursementStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public int getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getResolveDate() {
        return this.resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }

    public int getResolverId() {
        return this.resolverId;
    }

    public void setResolverId(int resolverId) {
        this.resolverId = resolverId;
    }

    public Blob getReceipt() {
        return this.receipt;
    }

    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amount, this.authorId, this.id, this.submitDate);
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
                && this.authorId == other.authorId && this.id == other.id
                && Objects.equals(this.submitDate, other.submitDate);
    }

    @Override
    public String toString() {
        return "Reimbursement [id=" + this.id + ", submitDate=" + this.submitDate + ", amount=" + this.amount
                + ", description=" + this.description + ", status=" + this.status + ", authorId=" + this.authorId
                + ", resolveDate=" + this.resolveDate + ", resolverId=" + this.resolverId + "]";
    }

}

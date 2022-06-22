package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.revature.models.reimbursement.Reimbursement;
import com.revature.utils.SessionHelper;

public class ReimbursementDao implements IDao<Reimbursement> {


  @Override
  public List<Reimbursement> selectAll() {
    List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
    try {
      Session session = SessionHelper.getSession();
      Query query = session.createQuery("FROM Reimbursement");
      reimbursements = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return reimbursements;
  }

  @Override
  public Reimbursement selectById(int id) {
    try {
      Session session = SessionHelper.getSession();
      session.beginTransaction();
      Reimbursement entity = (Reimbursement) session.get(Reimbursement.class, id);
      session.getTransaction().commit();
      return entity;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return null;
  }

}
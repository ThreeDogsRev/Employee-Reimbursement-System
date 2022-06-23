package com.revature.dao;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.revature.utils.SessionHelper;


public abstract class AbstractDao<T> {


  public AbstractDao() {
    super();
  }

  // Create
  /**
   * 
   * @param entity, the item to insert into the database
   * @return the item that was inserted into the DB
   */
  public T insert(T entity) {
    Session session = null;
    try {
      session = SessionHelper.getSession();
      session.beginTransaction();
      Integer id = (Integer) session.save(entity);
      session.getTransaction().commit();
      return (T) session.get(entity.getClass(), id);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {if(session != null) session.close();} catch(Exception ex) {}
    }
    return null;
  }

  public void persist(T entitiy){
    Session session = null;
    try {
      session = SessionHelper.getSession();
      session.beginTransaction();
      session.persist(entitiy);
      session.getTransaction().commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {if(session != null) session.close();} catch(Exception ex) {}
    }
  }

  // Read
  /**
   * @return a list of all items in the DB
   */
  public abstract List<T> selectAll();

  /**
   * @param id, the id of the item to select
   * @return the item with the id that was passed in
   */
  public abstract T selectById(int id) throws SQLException;

  // Update

  /**
   * @param entity, the item to update in the DB
   * @return the item that was updated in the DB
   */
  public T update(T entity){
    Session session = null;
      try {
        session = SessionHelper.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        return entity;
      } catch (HibernateException e) {
        e.printStackTrace();
      } finally {
        try {if(session != null) session.close();} catch(Exception ex) {}
      }
      return null;
    }

  // Delete
  /**
   * @param entity, the item to delete from the DB
   * @return the item that was deleted from the DB
   */
  public T delete(T entity){
    Session session = null;
      try {
        session = SessionHelper.getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        return entity;
      } catch (HibernateException e) {
        e.printStackTrace();
      } finally {
        try {if(session != null) session.close();} catch(Exception ex) {}
      }
      return null;
    }
  }
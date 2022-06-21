package dao;
import java.util.List;


public interface Dao<T> {
  // Create
  
  /**
   * 
   * @param entity, the item to insert into the database
   * @return the item that was inserted into the DB
   */
   Integer insert(T entity);
  
  /*default Integer insert(T entity) {
    try {
      Session session = Hibernate.getSession();
      session.beginTransaction();
      Integer id = (Integer) session.save(entity);
      session.getTransaction().commit();
      session.close();
      return id;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return 0;
  }
  */

  // Read
  
  /**
   * @return a list of all items in the DB
   */
  List<T> selectAll();

  /**
   * @param id, the id of the item to select
   * @return the item with the id that was passed in
   */
  T selectById(int id);

  // Update

  /**
   * @param entity, the item to update in the DB
   * @return the item that was updated in the DB
   */
  T update(T entity);

  // Delete
  /**
   * @param entity, the item to delete from the DB
   * @return the item that was deleted from the DB
   */
  T delete(T entity);
}

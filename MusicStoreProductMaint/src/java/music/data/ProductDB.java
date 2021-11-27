package music.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import music.business.Product;

public class ProductDB 
{
     public static List<Product> selectAll() {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    String qString = "SELECT u FROM Product u";
    TypedQuery<Product> q = em.createQuery(qString, Product.class);

    List<Product> products;
    try {
      products = q.getResultList();
    } catch (NoResultException e) {
      System.out.println(e);
      products = null;
    } finally {
      em.close();
    }
    return products;
  }
       
  public static Product selectByCode(String code) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    String qString = "SELECT u FROM Product u WHERE u.code = :code";
    TypedQuery<Product> q = em.createQuery(qString, Product.class);
    q.setParameter("code", code);
    
    Product product = null;
    try {
      product = q.getSingleResult();
    } catch (NoResultException e) {
      System.out.println(e);
      product = null;
    } finally {
      em.close();
    }
    return product;
  }
      
  public static void update(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
      trans.begin();
      em.merge(product);
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
    
  public static void insert(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
      trans.begin();
      em.persist(product);
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
   
  public static void delete(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();      
    try {
      trans.begin();
      em.remove(em.merge(product));
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
    
    public static Product selectProduct(String code)
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p FROM Product p " +
                "WHERE p.code = :code";
        TypedQuery<Product> q = em.createQuery(qString, Product.class);
        q.setParameter("code", code);
        Product result = null;
        try {
            result = q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return (Product)result;
    }
    
    public static Product selectProduct(long productId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        return em.find(Product.class, productId);
    }
    
    public List<Product> selectProducts() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p from Product p";
        TypedQuery<Product> q = em.createQuery(qString, Product.class);
        List<Product> results = null;
        try {
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
  }
  // JPA Part End
  
  // closing public class ProductDB 

    /*

    public static Product selectProduct(String code) {
                EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM Product u " +
                "WHERE u.code = :code";
        TypedQuery<Product> q = em.createQuery(qString, Product.class);
        q.setParameter("code", code);
        try {
            Product product = q.getSingleResult();
            return product;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }

    }
    
    public static Product selectProduct(long productId) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        return em.find(Product.class, productId);
    }

    public static void delete(Product product) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(product));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }     
    }
/*
    public static void insert(Product product) 
    {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void update(Product product)
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
*/
/*
    public static void update(Product product) 
    {
        
        
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    System.out.println("UPDATEDB");
    
    try 
    {
      trans.begin();
      em.merge(product);
      trans.commit();
    } 
    catch (Exception e) 
    {
      System.out.println(e);
      trans.rollback();
    } 
    finally 
    {
      em.close();
    }
  }
    
  public static void insert(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    System.out.println("INSERTDB");
    try {
      trans.begin();
      em.persist(product);
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
    public static boolean exists(String code) 
    {
        Product p = selectProduct(code);   
        return p != null;
    }
    
    public static List<Product> selectProducts() 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p from Product p";
        TypedQuery<Product> p = em.createQuery(qString, Product.class);
        List<Product> results = null;
        try {
            results = p.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
     
    /*
    public static List<Product> selectProducts() 
     {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p from Product p";
        TypedQuery<Product> p = em.createQuery(qString, Product.class);

        List<Product> products;
        try {
            products = p.getResultList();
            if (products == null || products.isEmpty())
                products = null;
        } finally {
            em.close();
        }
        return products;
    }
     public static void insert(User user) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(User user) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }       
    }

    public static User selectUser(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u " +
                "WHERE u.email = :email";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("email", email);
        try {
            User user = q.getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean emailExists(String email) {
        User u = selectUser(email);   
        return u != null;
    }
    
    public static List<User> selectUsers() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u from User u";
        TypedQuery<User> q = em.createQuery(qString, User.class);

        List<User> users;
        try {
            users = q.getResultList();
            if (users == null || users.isEmpty())
                users = null;
        } finally {
            em.close();
        }
        return users;
    }
}
    */

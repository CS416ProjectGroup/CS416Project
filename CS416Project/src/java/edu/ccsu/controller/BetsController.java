package edu.ccsu.controller;

import edu.ccsu.model.User;
import edu.ccsu.model.UserInfo;
import edu.ccsu.model.Group;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@ManagedBean
public class BetsController {

  @PersistenceUnit(unitName = "FinalProjectPU")
  private EntityManagerFactory entityManagerFactory;
  @Resource
  private UserTransaction userTransaction;
  //@ManagedProperty(value = "#{user}")
  //private User user;
  //@ManagedProperty(value = "#{userInfo}")
  //private UserInfo userInfo;

  //public String createUser() 
  //{
//    String returnValue = "Error";
//    try 
//    {
//        //User Stuff
//        user.setPassword(Hash(user.getPassword()));
//        String[] userGroups = {"casinouser"};
//        user.setGroupNames(userGroups);      
//        userTransaction.begin();
//        EntityManager em = entityManagerFactory.createEntityManager();
//        for (String group : user.getGroupNames()) 
//        {
//            if (group.length() > 0) 
//            {
//              Group userGroup = em.find(Group.class, group);
//              user.getGroups().add(userGroup);
//              userGroup.getUsers().add(user);
//            }
//        }
//        //User Info Stuff
//        userInfo.setUser(user);
//        userInfo.setMoney(50); 
//          
//        //Persistence
//        em.persist(user);      
//        em.persist(userInfo);
//        user.getGroups().stream().forEach((group) -> 
//        {
//            em.persist(group);
//        });
//        userTransaction.commit();
//        em.close();
//        returnValue = "UserLogin";
//    } 
//    catch (Exception e) 
//    {
//        System.out.println(e.toString());
//        e.printStackTrace();
//    }
//    return returnValue;
//}
//  
//    public void getMatchingMoney(ActionEvent event, String username) //throws NotSupportedException, SystemException 
//    {       
//        try 
//        {
//            double money = 0;
//            //userTransaction.begin();
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            String selectSQL = "select u.money from UserInfo u where u.user = :username";
//            //String selectSQL = "select c from Customer c where c.firstName like :name";
//            userInfo.setUser(entityManager.find(User.class, username)); 
//            user = entityManager.find(User.class, username);                   
//            //userInfo.setUser(entityManager.find(User.class, username)); 
//            //int abc = 1;
//            Query selectQuery = entityManager.createQuery(selectSQL);
//            selectQuery.setParameter("username", user);
//            money = (double)selectQuery.getSingleResult();
//            userInfo.setMoney(money);
//            entityManager.close();
//            //userTransaction.commit();
//        } catch (Exception e) {           
//            e.printStackTrace();
//        }       
//    }
//    
//    public void AddMoney(ActionEvent event, String username) 
//    {      
//        try 
//        {
////            double money = 0;      
////            EntityManager entityManager = entityManagerFactory.createEntityManager();
////            //String selectSQL = "select u.money from UserInfo u where u.user = :username";       
////            String selectSQL = "select u.userInfoId from UserInfo u where u.user = :username";
////            Query selectQuery = entityManager.createQuery(selectSQL);
////            //User user = new User();
////            user = entityManager.find(User.class, username);
////            selectQuery.setParameter("username", user);
////            Long UserInfoId = (Long)selectQuery.getSingleResult();     
////            
////            
////            userInfo = entityManager.find(UserInfo.class, UserInfoId);
////            userInfo.setNewMoney(userInfo.getNewMoney());
////            userInfo.addFunds(event);
////            userInfo.setUser(user); 
////            entityManager.merge(userInfo);
//            //userInfo.setUser(entityManager.find(User.class, username)); 
//            //user = entityManager.find(User.class, username);                   
//            //userInfo.setUser(entityManager.find(User.class, username)); 
//            //int abc = 1;
//            //Query selectQuery = entityManager.createQuery(selectSQL);
//            //selectQuery.setParameter("username", user);
//            //money = (double)selectQuery.getSingleResult();
//            
//            //userInfo.setMoney(money);
//            //userInfo.addFunds(event);
//            //userTransaction.begin();
//            //entityManager.merge(userInfo);          
//            //
//            //String updateSQL = "update UserInfo u set u.money= :money where u.user = :username";
//            //Query updateQuery = entityManager.createQuery(updateSQL);
//            //updateQuery.setParameter("username", user);
//            //updateQuery.setParameter("money", money);
//            //int rowsAffected = updateQuery.executeUpdate();
//            //userTransaction.commit();
//            double money = 0;
//            //userTransaction.begin();
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            String selectSQL = "select u.money from UserInfo u where u.user = :username";
//            //String selectSQL = "select c from Customer c where c.firstName like :name";
//            user = entityManager.find(User.class, username);
//            userInfo.setUser(user);                  
//            //userInfo.setUser(entityManager.find(User.class, username)); 
//            //int abc = 1;
//            Query selectQuery = entityManager.createQuery(selectSQL);
//            selectQuery.setParameter("username", user);
//            money = (double)selectQuery.getSingleResult();
//            userInfo.setMoney(money);
//            userInfo.addFunds(event);
//            String updateSQL = "update UserInfo u set u.money= :money where u.user = :username";
//            Query updateQuery = entityManager.createQuery(updateSQL);
//            updateQuery.setParameter("username", user);
//            updateQuery.setParameter("money", userInfo.getMoney());
//            userTransaction.begin();
//            entityManager.joinTransaction();
//            int rowsAffected = updateQuery.executeUpdate();
//            userTransaction.commit();
//            entityManager.close();
//            
//            //userTransaction.commit();
//            //
//        } catch (Exception e) {           
//            e.printStackTrace();
//        } 
//    }
//  
//
//  public String Hash(String password) throws NoSuchAlgorithmException {
//    MessageDigest msgDigest = MessageDigest.getInstance("MD5");
//    byte[] bs;
//    msgDigest.reset();
//    bs = msgDigest.digest(password.getBytes());
//    StringBuilder sBuilder = new StringBuilder();
//    for (int i = 0; i < bs.length; i++) {
//      String hexVal = Integer.toHexString(0xFF & bs[i]);
//      if (hexVal.length() == 1) {
//        sBuilder.append("0");
//      }
//      sBuilder.append(hexVal);
//    }
//    return sBuilder.toString();
//  }
//
    public BetsController() {

    }
//
//  public User getUser() {
//    return user;
//  }
//
//  public void setUser(User user) {
//    this.user = user;
//  }
//  
//  public UserInfo getUserInfo() {
//    return userInfo;
//  }
//
//  public void setUserInfo(UserInfo userInfo) {
//    this.userInfo = userInfo;
//  }
}
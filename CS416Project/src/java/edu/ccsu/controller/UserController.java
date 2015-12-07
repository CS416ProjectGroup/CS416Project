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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@ManagedBean
@RequestScoped
public class UserController {

  @PersistenceUnit(unitName = "FinalProjectPU")
  private EntityManagerFactory entityManagerFactory;
  @Resource
  private UserTransaction userTransaction;
  @ManagedProperty(value = "#{user}")
  private User user;
  @ManagedProperty(value = "#{userInfo}")
  private UserInfo userInfo;
  
  //private List<UserInfo> currentList;
  private String searchterm;

  public List matchingUsers() {
        List<String> userInfoList = new ArrayList();
        List<UserInfo> userlist = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select u from UserInfo u where u.user.username like :username";
        try 
        {
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("username", (userInfo.getUser().getUsername() + "%"));
            userInfoList = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        //currentList = userInfoList;
        return userInfoList;
    }
  
  public void deleteUser(ActionEvent event) 
  {
      EntityManager entityManager = entityManagerFactory.createEntityManager();     
        try 
        {
            userTransaction.begin();
            entityManager.joinTransaction();
            
            User deleteuser = entityManager.find(User.class, userInfo.getUser().getUsername());
            
            String deleteSQL = "delete from Bets b where b.userInfo.user = :username";    
            Query deleteQuery = entityManager.createQuery(deleteSQL);
            deleteQuery.setParameter("username", deleteuser);                     
            int rowsaffect = deleteQuery.executeUpdate();
            
            String deleteSQL3 = "delete from UserInfo u where u.user = :username";    
            Query deleteQuery3 = entityManager.createQuery(deleteSQL3);
            deleteQuery3.setParameter("username", deleteuser);                     
            int rowsaffect3 = deleteQuery3.executeUpdate();           
            
            String deleteSQL2 = "delete from User u where u.username= :username";    
            Query deleteQuery2 = entityManager.createQuery(deleteSQL2);
            deleteQuery2.setParameter("username", (userInfo.getUser().getUsername()));                     
            int rowsaffect2 = deleteQuery2.executeUpdate();
            
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    entityManager.close();
  }
  
  public String createUser() 
  {
    String returnValue = "Error";
    try 
    {
        //User Stuff
        user.setPassword(Hash(user.getPassword()));
        String[] userGroups = {"casinouser"};
        user.setGroupNames(userGroups);      
        userTransaction.begin();
        EntityManager em = entityManagerFactory.createEntityManager();
        for (String group : user.getGroupNames()) 
        {
            if (group.length() > 0) 
            {
              Group userGroup = em.find(Group.class, group);
              user.getGroups().add(userGroup);
              userGroup.getUsers().add(user);
            }
        }
        //User Info Stuff
        userInfo.setUser(user);
        userInfo.setMoney(50); 
          
        //Persistence
        em.persist(user);      
        em.persist(userInfo);
        user.getGroups().stream().forEach((group) -> 
        {
            em.persist(group);
        });
        userTransaction.commit();
        em.close();
        returnValue = "UserLogin";
    } 
    catch (Exception e) 
    {
        System.out.println(e.toString());
        e.printStackTrace();
    }
    return returnValue;
}
  
    public void getMatchingMoney(ActionEvent event, String username) //throws NotSupportedException, SystemException 
    {       
        try 
        {
            double money = 0;
            //userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String selectSQL = "select u.money from UserInfo u where u.user = :username";
            //String selectSQL = "select c from Customer c where c.firstName like :name";
            userInfo.setUser(entityManager.find(User.class, username)); 
            user = entityManager.find(User.class, username);                   
            //userInfo.setUser(entityManager.find(User.class, username)); 
            //int abc = 1;
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("username", user);
            money = (double)selectQuery.getSingleResult();
            userInfo.setMoney(money);
            entityManager.close();
            //userTransaction.commit();
        } catch (Exception e) {           
            e.printStackTrace();
        }       
    }
    
    public void AddMoney(ActionEvent event, String username) 
    {      
        try 
        {
//            double money = 0;      
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            //String selectSQL = "select u.money from UserInfo u where u.user = :username";       
//            String selectSQL = "select u.userInfoId from UserInfo u where u.user = :username";
//            Query selectQuery = entityManager.createQuery(selectSQL);
//            //User user = new User();
//            user = entityManager.find(User.class, username);
//            selectQuery.setParameter("username", user);
//            Long UserInfoId = (Long)selectQuery.getSingleResult();     
//            
//            
//            userInfo = entityManager.find(UserInfo.class, UserInfoId);
//            userInfo.setNewMoney(userInfo.getNewMoney());
//            userInfo.addFunds(event);
//            userInfo.setUser(user); 
//            entityManager.merge(userInfo);
            //userInfo.setUser(entityManager.find(User.class, username)); 
            //user = entityManager.find(User.class, username);                   
            //userInfo.setUser(entityManager.find(User.class, username)); 
            //int abc = 1;
            //Query selectQuery = entityManager.createQuery(selectSQL);
            //selectQuery.setParameter("username", user);
            //money = (double)selectQuery.getSingleResult();
            
            //userInfo.setMoney(money);
            //userInfo.addFunds(event);
            //userTransaction.begin();
            //entityManager.merge(userInfo);          
            //
            //String updateSQL = "update UserInfo u set u.money= :money where u.user = :username";
            //Query updateQuery = entityManager.createQuery(updateSQL);
            //updateQuery.setParameter("username", user);
            //updateQuery.setParameter("money", money);
            //int rowsAffected = updateQuery.executeUpdate();
            //userTransaction.commit();
            double money = 0;
            //userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String selectSQL = "select u.money from UserInfo u where u.user = :username";
            //String selectSQL = "select c from Customer c where c.firstName like :name";
            user = entityManager.find(User.class, username);
            userInfo.setUser(user);                  
            //userInfo.setUser(entityManager.find(User.class, username)); 
            //int abc = 1;
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("username", user);
            money = (double)selectQuery.getSingleResult();
            userInfo.setMoney(money);
            userInfo.addFunds(event);
            String updateSQL = "update UserInfo u set u.money= :money where u.user = :username";
            Query updateQuery = entityManager.createQuery(updateSQL);
            updateQuery.setParameter("username", user);
            updateQuery.setParameter("money", userInfo.getMoney());
            userTransaction.begin();
            entityManager.joinTransaction();
            int rowsAffected = updateQuery.executeUpdate();
            userTransaction.commit();
            entityManager.close();
            
            //userTransaction.commit();
            //
        } catch (Exception e) {           
            e.printStackTrace();
        } 
    }
  

  public String Hash(String password) throws NoSuchAlgorithmException {
    MessageDigest msgDigest = MessageDigest.getInstance("MD5");
    byte[] bs;
    msgDigest.reset();
    bs = msgDigest.digest(password.getBytes());
    StringBuilder sBuilder = new StringBuilder();
    for (int i = 0; i < bs.length; i++) {
      String hexVal = Integer.toHexString(0xFF & bs[i]);
      if (hexVal.length() == 1) {
        sBuilder.append("0");
      }
      sBuilder.append(hexVal);
    }
    return sBuilder.toString();
  }

  public UserController() {

  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  
  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

    /**
     * @return the searchterm
     */
    public String getSearchterm() {
        return searchterm;
    }

    /**
     * @param searchterm the searchterm to set
     */
    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
// */
//package edu.ccsu.controller;
//
//import edu.ccsu.model.Users;
//import edu.ccsu.model.Group;
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceUnit;
//import javax.persistence.Query;
//import javax.transaction.UserTransaction;
//
///**
// *
// * @author AB
// */
//@ManagedBean
//public class UserController {
//    
//    @PersistenceUnit(unitName = "FinalProjectPU")
//    private EntityManagerFactory entityManagerFactory;
//    @Resource
//    private UserTransaction userTransaction;
//    @ManagedProperty(value = "#{user}")
//    private Users user;
//    
//    public String userRegistration() {
//        String returnValue = "error";
//        try {
//            users.setMoney(40.0);
//            userTransaction.begin();
//            EntityManager em = entityManagerFactory.createEntityManager();
//            em.persist(users);
//            userTransaction.commit();
//            em.close();
//            returnValue = "UserProfile";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnValue;
//    }
//    
//        /**
//     * @return the user
//     */
//    public Users getUsers() {
//        return users;
//    }
//
//    /**
//     * @param user the user to set
//     */
//    public void setUsers(Users users) {
//        this.users = users;
//    }
//}
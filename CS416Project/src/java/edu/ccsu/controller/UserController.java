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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@ManagedBean
public class UserController {

  @PersistenceUnit(unitName = "FinalProjectPU")
  private EntityManagerFactory entityManagerFactory;
  @Resource
  private UserTransaction userTransaction;
  @ManagedProperty(value = "#{user}")
  private User user;
  @ManagedProperty(value = "#{userInfo}")
  private UserInfo userInfo;

  public String createUser() {
    String returnValue = "Error";
    try {
      user.setPassword(Hash(user.getPassword()));
      String[] userGroups = {"user"};
      user.setGroupNames(userGroups);
      userInfo.setMoney(50);
      userTransaction.begin();
      EntityManager em = entityManagerFactory.createEntityManager();
      for (String group : user.getGroupNames()) {
        if (group.length() > 0) {
          Group userGroup = em.find(Group.class, group);
          user.getGroups().add(userGroup);
          userGroup.getUsers().add(user);
        }
      }
      em.persist(user);
      em.persist(userInfo);
      user.getGroups().stream().forEach((group) -> {
        em.persist(group);
      });
      userTransaction.commit();
      em.close();
      returnValue = "UserLogin";
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }
    return returnValue;
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
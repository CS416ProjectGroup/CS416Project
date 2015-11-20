/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.controller;

import edu.ccsu.model.User;
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

/**
 *
 * @author Adam
 */
@ManagedBean
public class UserController {
    
    @PersistenceUnit(unitName = "CS416ProjectPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    @ManagedProperty(value = "#{user}")
    private User user;
    
    public String userRegistration() {
        String returnValue = "error";
        try {
            user.setMoney(50.0);
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            em.persist(user);
            userTransaction.commit();
            em.close();
            returnValue = "userProfile";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
        /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}

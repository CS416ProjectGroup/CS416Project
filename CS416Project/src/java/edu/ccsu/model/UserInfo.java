/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.model;
import static edu.ccsu.model.Group_.users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.UserTransaction;

/**
 *
 * @author AB
 */

@ManagedBean
@SessionScoped
@Entity
public class UserInfo implements Serializable {
    
    @Id
    @GeneratedValue
    private Long userInfoId;
    
    @OneToOne    
    @JoinColumn(name="username")
    private User user = new User();
    
    @OneToMany(mappedBy="userInfo")
    private Set<Bets> bets; 
    private double money = 0.0;
    private double newMoney = 0.0;

    

    public UserInfo() {
    }

    public void addFunds(ActionEvent event) {
        money = money + getNewMoney();
    }   
    
    public void commitBet()
    {
        money = money - getNewMoney();
    }
        
    /**
     * @return the userInfoId
     */
    public Long getUserInfoId() {
        return userInfoId;
    }

    /**
     * @param userInfoId the userInfoId to set
     */
    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    /**
     * @return the money
     */
    public double getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(double money) {
        this.money = money;
    }
    
      /**
     * @return the newMoney
     */
    public double getNewMoney() {
        return newMoney;
    }
    
      /**
     * @param newMoney the newMoney to set
     */
    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
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

    /**
     * @return the bets
     */
    public Set<Bets> getBets() {
        return bets;
    }

    /**
     * @param bets the bets to set
     */
    public void setBets(Set<Bets> bets) {
        this.bets = bets;
    }

}
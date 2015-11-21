/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.model;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author AB
 */
@ManagedBean
@SessionScoped
@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue
    private Long userId;
    private String userName = "";
    private String userPassword = "";
    private double money = new Long(0);
    private double newMoney = new Long(0);


    public Users() {
    }

    public void addFunds(ActionEvent event) {
        money = money + getNewMoney();
    }
        
    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
     * @param stateOwned the stateOwned to set
     */
    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
    }

}
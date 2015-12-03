/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.model;
import static edu.ccsu.model.Group_.users;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    @JoinColumn(name = "userName")
    private User user;
    private double money = new Long(0);
    private double newMoney = new Long(0);


    public UserInfo() {
    }

    public void addFunds(ActionEvent event) {
        money = money + getNewMoney();
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
     * @param stateOwned the stateOwned to set
     */
    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
    }

}
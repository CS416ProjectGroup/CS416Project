/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author KK
 */
@ManagedBean
@Entity
@SessionScoped
public class Bets implements Serializable {



    @Id
    @GeneratedValue
    private Long betId;
    
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    
    private Double bet;
    private Double win;
    
    private Double bjScore;
    private ArrayList<String> bjHand = new ArrayList<String>();
    
    private Double bjDealerScore;
    private ArrayList<String> bjDealerHand = new ArrayList<String>();


    public Bets()
    {}

    public Long getBetId() {
        return betId;
    }
    public Double getBet() {
        return bet;
    }
    public Double getWin() {
        return win;
    }

    public void setBetId(Long id) {
        this.betId = id;
    }
    public void setBet(Double bet) {
        this.bet = bet;
    }
    public void setWin(Double win) {
        this.win = win;
    }

    @Override
    public String toString() {
        return "edu.ccsu.model.Bets[ id=" + betId + " ]";
    }

    /**
     * @return the userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * @return the bjHand
     */
    public ArrayList<String> getBjHand() {
        return bjHand;
    }

    /**
     * @param bjHand the bjHand to set
     */
    public void setBjHand(ArrayList<String> bjHand) {
        this.bjHand = bjHand;
    }

    /**
     * @return the bjScore
     */
    public Double getBjScore() {
        return bjScore;
    }

    /**
     * @param bjScore the bjScore to set
     */
    public void setBjScore(Double bjScore) {
        this.bjScore = bjScore;
    }

    /**
     * @return the bjDealerScore
     */
    public Double getBjDealerScore() {
        return bjDealerScore;
    }

    /**
     * @param bjDealerScore the bjDealerScore to set
     */
    public void setBjDealerScore(Double bjDealerScore) {
        this.bjDealerScore = bjDealerScore;
    }

    /**
     * @return the bjDealerHand
     */
    public ArrayList<String> getBjDealerHand() {
        return bjDealerHand;
    }

    /**
     * @param bjDealerHand the bjDealerHand to set
     */
    public void setBjDealerHand(ArrayList<String> bjDealerHand) {
        this.bjDealerHand = bjDealerHand;
    }

}

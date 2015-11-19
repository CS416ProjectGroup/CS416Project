/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Krzysztof
 */
@ManagedBean
@SessionScoped
@Entity

public class Bets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    private Double bet;
    private Double win;
    @ManyToOne
    private int userId;
    public Bets(){}
    public int getId() {
        return id;
    }
    public Double getBet() {
        return bet;
    }
    public Double getWin() {
        return win;
    }
    public int getUserId() {
        return userId;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setBet(Double bet) {
        this.bet = bet;
    }
    public void setWin(Double win) {
        this.win = win;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "edu.ccsu.model.Bets[ id=" + id + " ]";
    }
    
}

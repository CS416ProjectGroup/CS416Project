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
import javax.persistence.JoinColumn;
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



    @Id
    @GeneratedValue
    private Long betId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;
    private Double bet;
    private Double win;


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

}

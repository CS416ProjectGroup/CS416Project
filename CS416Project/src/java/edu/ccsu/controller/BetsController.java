package edu.ccsu.controller;

import edu.ccsu.model.Bets;
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
  @ManagedProperty(value = "#{bets}")
  private Bets bets;
  

  public List returnMatchingBets() {
        List<UserInfo> betsList = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select b.userInfoId, b.bet, b.win, b.bjScore, b.bjDealerScore from Bets b where b.userInfoId like :userInfo";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("userInfo", bets.getUserInfo());
            betsList = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //currentList = userInfoList;
        entityManager.close();
        return betsList;       
    }

    /**
     * @return the bets
     */
    public Bets getBets() {
        return bets;
    }

    /**
     * @param bets the bets to set
     */
    public void setBets(Bets bets) {
        this.bets = bets;
    }
  
}
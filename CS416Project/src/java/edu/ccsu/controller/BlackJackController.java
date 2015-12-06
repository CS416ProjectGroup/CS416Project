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
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@ManagedBean
public class BlackJackController {

  @PersistenceUnit(unitName = "FinalProjectPU")
  private EntityManagerFactory entityManagerFactory;
  @Resource
  private UserTransaction userTransaction;
  @ManagedProperty(value = "#{bets}")
  private Bets bets;
  @ManagedProperty(value = "#{userInfo}")
  private UserInfo userInfo;
  
  private ArrayList<String> deck = new ArrayList<String>();
  private ArrayList<String> hand = new ArrayList<String>();
  
  public String placeBet(String username) {
    String returnValue = "Error";
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try 
    {
        double money = 0;
        
        User user = entityManager.find(User.class, username);
        userInfo.setUser(user); 
        bets.setUser(user);
        String selectSQL = "select u.money from UserInfo u where u.user = :username";
        Query selectQuery = entityManager.createQuery(selectSQL);
        selectQuery.setParameter("username", user);
        money = (double)selectQuery.getSingleResult();
        userInfo.setMoney(money);
        if(bets.getBet() > userInfo.getMoney())
        {
            returnValue = "BlackJackBetError";
            entityManager.close();
            return returnValue;
        }
        else
        {
            userInfo.setNewMoney(bets.getBet());
            userInfo.commitBet();
            startNewDeck();                               
            String updateSQL = "update UserInfo u set u.money= :money where u.user = :username";
            Query updateQuery = entityManager.createQuery(updateSQL);
            updateQuery.setParameter("username", user);
            updateQuery.setParameter("money", userInfo.getMoney());
            userTransaction.begin();
            entityManager.joinTransaction();
            int rowsAffected = updateQuery.executeUpdate();
            userTransaction.commit();
            returnValue = "BlackJack";
            entityManager.close();
            return returnValue;
        }
    } catch (Exception e) 
    {
        System.out.println(e.toString());
        e.printStackTrace();     
    }
    

    entityManager.close();
    return returnValue;
  }
  
//      user.setGroupNames(userGroups);
//      //userInfo.setMoney(50);
//      userTransaction.begin();
//      EntityManager em = entityManagerFactory.createEntityManager();
//      for (String group : user.getGroupNames()) {
//        if (group.length() > 0) {
//          Group userGroup = em.find(Group.class, group);
//          user.getGroups().add(userGroup);
//          userGroup.getUsers().add(user);
//        }
//      }
//      em.persist(user);
//     // em.persist(userInfo);
//      user.getGroups().stream().forEach((group) -> {
//        em.persist(group);
//      });
//      userTransaction.commit();
//      em.close();
//      returnValue = "UserLogin";

//    }
  

//  public String Hash(String password) throws NoSuchAlgorithmException {
//    MessageDigest msgDigest = MessageDigest.getInstance("MD5");
//    byte[] bs;
//    msgDigest.reset();
//    bs = msgDigest.digest(password.getBytes());
//    StringBuilder sBuilder = new StringBuilder();
//    for (int i = 0; i < bs.length; i++) {
//      String hexVal = Integer.toHexString(0xFF & bs[i]);
//      if (hexVal.length() == 1) {
//        sBuilder.append("0");
//      }
//      sBuilder.append(hexVal);
//    }
//    return sBuilder.toString();
//  }
//
//  public UserController() {
//
//  }
//
//  public User getUser() {
//    return user;
//  }
//
//  public void setUser(User user) {
//    this.user = user;
//  }
  
  public void playHit()
  {
      
  }
  
  public void playStay()
  {
      
  }
  
  public void startNewDeck()
  {
      deck.clear();
      hand.clear();
      deck.add("1ASpades"); deck.add("2Spades"); deck.add("3Spades"); deck.add("4Spades"); deck.add("5Spades"); deck.add("6Spades"); deck.add("7Spades"); deck.add("8Spades"); deck.add("9Spades"); deck.add("10Spades"); deck.add("10JSpades"); deck.add("10QSpades"); deck.add("10KSpades");
      deck.add("1ADiamonds"); deck.add("2Diamonds"); deck.add("3Diamonds"); deck.add("4Diamonds"); deck.add("5Diamonds"); deck.add("6Diamonds"); deck.add("7Diamonds"); deck.add("8Diamonds"); deck.add("9Diamonds"); deck.add("10Diamonds"); deck.add("10JDiamonds"); deck.add("10QDiamonds"); deck.add("10KDiamonds");
      deck.add("1AHearts"); deck.add("2Hearts"); deck.add("3Hearts"); deck.add("4Hearts"); deck.add("5Hearts"); deck.add("6Hearts"); deck.add("7Hearts"); deck.add("8Hearts"); deck.add("9Hearts"); deck.add("10Hearts"); deck.add("10JHearts"); deck.add("10QHearts"); deck.add("10KHearts");
      deck.add("1AClubs"); deck.add("2Clubs"); deck.add("3Clubs"); deck.add("4Clubs"); deck.add("5Clubs"); deck.add("6Clubs"); deck.add("7Clubs"); deck.add("8Clubs"); deck.add("9Clubs"); deck.add("10Clubs"); deck.add("10JClubs"); deck.add("10QClubs"); deck.add("10KClubs");
  }
  public int getNewCard()
  {
      int cardValue = 0;
      int deckSize = deck.size();
      int randomCard = 0 + (int)(Math.random()*(deckSize-1));
      
      return cardValue;
  }
  public BlackJackController() {

  }

  public Bets getBets() {
    return bets;
  }

  public void setBets(Bets bets) {
    this.bets = bets;
  }
  
  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
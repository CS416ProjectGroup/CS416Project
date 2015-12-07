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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@ManagedBean
@SessionScoped
public class BlackJackController 
{

    @PersistenceUnit(unitName = "FinalProjectPU")
    private EntityManagerFactory entityManagerFactory;
     @Resource
    private UserTransaction userTransaction;
    @ManagedProperty(value = "#{bets}")
    private Bets bets;
    @ManagedProperty(value = "#{userInfo}")
    private UserInfo userInfo;
  
    private ArrayList<String> deck;
    private ArrayList<String> hand;
    private ArrayList<String> dealerHand;
  
    public String placeBet(String username) 
    {
        String returnValue = "Error";
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try 
        {
            //double betAmount = bets.getBet();
            //bets.setBet(betAmount);
            double money = 0;
        
            User user = entityManager.find(User.class, username);
        
            String selectSQL = "select u.userInfoId from UserInfo u where u.user = :username";
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("username", user);
            Long UserInfoId = (Long)selectQuery.getSingleResult();     
            userInfo = entityManager.find(UserInfo.class, UserInfoId);
        
            //String selectSQLtest = "select u.money from UserInfo u where u.user = :username";
            //Query selectQuerytest = entityManager.createQuery(selectSQL);
            //selectQuery.setParameter("username", user);
            money = userInfo.getMoney();
            if(bets.getBet() > money)
            {
                returnValue = "BlackJackBetError";
                entityManager.close();
                return returnValue;
            }
            else
            {          
                userInfo.setNewMoney(bets.getBet());
                bets.setBjScore(0.0);
                bets.setWin(0.0);
                bets.setUserInfo(userInfo);
                userInfo.commitBet();
                //startNewDeck();                               
                String updateSQL = "update UserInfo u set u.money= :money where u.userInfoId = :userInfoIdNo";
                Query updateQuery = entityManager.createQuery(updateSQL);
                updateQuery.setParameter("userInfoIdNo", userInfo.getUserInfoId());
                updateQuery.setParameter("money", userInfo.getMoney());
                userTransaction.begin();
                entityManager.joinTransaction();
                int rowsAffected = updateQuery.executeUpdate();
                entityManager.persist(bets);           
                userTransaction.commit();
                if(deck==null)
                {
                    hand = new ArrayList<String>();
                    deck = new ArrayList<String>();           
                    if(deck.size() != 52)
                    {
                        startNewDeck();           
                    } 
                }
                for(int i = 0; i < 2; i++)
                {
                    String cardDrawn = getNewCard();
                    hand.add(cardDrawn);
                    String sValue = cardDrawn.substring(0,2);
                    int iValue = 0;
                    char charTest = sValue.charAt(1);
                    if(charTest == '0' || charTest == '1')
                    {
                        iValue = Integer.parseInt(sValue);
                    }
                    else
                    {
                        sValue = sValue.substring(0,1);
                        iValue = Integer.parseInt(sValue);
                    }
                    bets.setBjScore(bets.getBjScore()+iValue);
                    bets.setBjHand(hand);
                }
            double bjScore = bets.getBjScore();          
            try
            {
                userTransaction.begin();
                entityManager.joinTransaction();
                String updateSQL2 = "update Bets b set b.bjScore = :newBjScore where b.betId = :betId";
                Query updateQuery2 = entityManager.createQuery(updateSQL2);
                updateQuery2.setParameter("betId", bets.getBetId());
                updateQuery2.setParameter("newBjScore", bjScore);
                int rowsAffected2 = updateQuery2.executeUpdate();
                
                String updateSQL3 = "update Bets b set b.bjHand = :newBjHand where b.betId = :betId";
                Query updateQuery3 = entityManager.createQuery(updateSQL3);
                updateQuery3.setParameter("betId", bets.getBetId());
                updateQuery3.setParameter("newBjHand", hand);
                int rowsAffected3 = updateQuery2.executeUpdate();
                userTransaction.commit();
            }
            catch (Exception e) 
            {
                  System.out.println(e.toString());
                  e.printStackTrace();     
            }     
            returnValue = "BlackJack";
            entityManager.close();
            return returnValue;
        }
    
        } 
    catch (Exception e) 
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
    public String newGameUser()
    {
        String returnValue = "Error";
        //bets = new Bets();
        setBets(bets = new Bets());
        returnValue = "BlackJackBet";
        return returnValue;
    }
  
    public String newGame()
    {
        String returnValue = "Error";
        //bets = new Bets();
      
        returnValue = "user/BlackJackBet";
        return returnValue;
    }
  
    public void playHit()
    {
        if(deck==null)
        {
            hand = new ArrayList<String>();
            deck = new ArrayList<String>();           
            if(deck.size() != 52)
            {
                startNewDeck();           
            } 
        }
       
        String cardDrawn = getNewCard();
        hand.add(cardDrawn);
        String sValue = cardDrawn.substring(0,2);
        int iValue = 0;
        char charTest = sValue.charAt(1);
        if(charTest == '0' || charTest == '1')
        {
            iValue = Integer.parseInt(sValue);
        }
        else
        {
            sValue = sValue.substring(0,1);
            iValue = Integer.parseInt(sValue);
        }
        bets.setBjScore(bets.getBjScore()+iValue);
        double bjScore = bets.getBjScore();
        bets.setBjHand(hand);  
        if(bjScore > 21.0)
        {
            String testAce;
            char charTestAce;
            boolean aceFound = false;
            String cardToReplace;
            char[] cardToReplaceArray;
            for(int i = 0; i < hand.size()-1; i++)
            {
                testAce = hand.get(i);
                testAce = testAce.substring(0,2);
                charTestAce = testAce.charAt(1); 

                if(charTestAce == '1')
                {
                    cardToReplace = hand.get(i);
                    cardToReplaceArray = cardToReplace.toCharArray();
                    cardToReplaceArray[1] = ' ';
                    cardToReplace = String.valueOf(cardToReplaceArray);
                    hand.remove(i);
                    hand.add(cardToReplace);
                    bjScore = bjScore - 10;
                    bets.setBjScore(bjScore);
                    aceFound = true;
                }               
            }
            if(aceFound == false)
            {                   
                ArrayList<String> busted = new ArrayList<String>();
                busted.add("Played Busted!");
                bets.setBjHand(busted);
                bets.setBjDealerHand(busted);
            }     
        }      
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try
        {
            userTransaction.begin();
            entityManager.joinTransaction();
            String updateSQL2 = "update Bets b set b.bjScore = :newBjScore where b.betId = :betId";
            Query updateQuery2 = entityManager.createQuery(updateSQL2);
            updateQuery2.setParameter("betId", bets.getBetId());
            updateQuery2.setParameter("newBjScore", bjScore);
            int rowsAffected2 = updateQuery2.executeUpdate();

//          String updateSQL3 = "update Bets b set b.bjHand = :newBjHand where b.betId = :betId";
//          Query updateQuery3 = entityManager.createQuery(updateSQL3);
//          updateQuery3.setParameter("betId", bets.getBetId());
//          updateQuery3.setParameter("newBjHand", hand);
//          int rowsAffected3 = updateQuery3.executeUpdate();
            userTransaction.commit();
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
            e.printStackTrace();     
        }     
    entityManager.close();                
  }
  
  public String playStay()
  {
        String returnValue = "Error";
        dealerHand = new ArrayList<String>();
        bets.setBjDealerScore(0.0);
        if(bets.getBjScore() <= 21.0 )
        {
            while(bets.getBjDealerScore()<bets.getBjScore())
            {
                String cardDrawn = getNewCard();
                dealerHand.add(cardDrawn);
                String sValue = cardDrawn.substring(0,2);
                int iValue = 0;
                char charTest = sValue.charAt(1);
                if(charTest == '0' || charTest == '1')
                {
                    iValue = Integer.parseInt(sValue);
                }
                else
                {
                    sValue = sValue.substring(0,1);
                    iValue = Integer.parseInt(sValue);
                }
                bets.setBjDealerScore(bets.getBjDealerScore() + iValue);
                bets.setBjDealerHand(dealerHand);
                if(bets.getBjDealerScore() > 21.0)
                {
                    String testAce;
                    char charTestAce;
                    boolean aceFound = false;
                    String cardToReplace;
                    char[] cardToReplaceArray;
                    for(int i = 0; i > dealerHand.size(); i++)
                    {
                        testAce = dealerHand.get(i);
                        testAce = testAce.substring(0,2);
                        charTestAce = testAce.charAt(1);   
                        if(charTestAce == '1')
                        {
                            cardToReplace = dealerHand.get(i);
                            cardToReplaceArray = cardToReplace.toCharArray();
                            cardToReplaceArray[1] = ' ';
                            cardToReplace = String.valueOf(cardToReplaceArray);
                            dealerHand.remove(i);
                            dealerHand.add(cardToReplace);
                            bets.setBjDealerScore(bets.getBjDealerScore() - 10.0);
                            aceFound = true;
                        }               
                    }
                    bets.setBjDealerHand(dealerHand);
                    if(aceFound == false)
                    {                   
                        ArrayList<String> busted = new ArrayList<String>();
                        busted.add("Dealer Busted!");
                        bets.setBjDealerHand(busted);
                        returnValue = "BlackJackResult";
                    } 
                }
            }   
            
      }
      
      
       
      if(bets.getBjScore() <= 21.0 )
      {
        if(bets.getBjDealerScore() >= bets.getBjScore() && bets.getBjDealerScore() <= 21.0)
        { 
            //Dealer Wins
            if(bets.getBjDealerScore().compareTo(bets.getBjScore()) == 0)
            {
                bets.setWin(bets.getBet());
            }
            else
            {
                bets.setWin(0.0);
            }
        }
        else
        {
            //User Wins
            bets.setWin(bets.getBet()*2.0);
        }
        if(bets.getBjScore() == 21.0)
        {
            bets.setWin(bets.getBet()*3.0);
        }
      }
      else
      {
          bets.setWin(0.0);
      }
      double newMoneyTotal = (userInfo.getMoney() + bets.getWin());  
      
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try
      {
          userTransaction.begin();
          entityManager.joinTransaction();
          String updateSQL = "update Bets b set b.bjDealerScore = :newBjDealerScore where b.betId = :betId";
          Query updateQuery = entityManager.createQuery(updateSQL);
          updateQuery.setParameter("betId", bets.getBetId());
          updateQuery.setParameter("newBjDealerScore", bets.getBjDealerScore());
          int rowsAffected = updateQuery.executeUpdate();

//          String updateSQL2 = "update Bets b set b.bjDealerHand = :newBjDealerHand where b.betId = :betId";
//          Query updateQuery2 = entityManager.createQuery(updateSQL2);
//          updateQuery2.setParameter("betId", bets.getBetId());
//          updateQuery2.setParameter("newBjDealerHand", dealerHand);
//          int rowsAffected2 = updateQuery2.executeUpdate();
          
          String updateSQL3 = "update Bets b set b.win = :newWin where b.betId = :betId";
          Query updateQuery3 = entityManager.createQuery(updateSQL3);
          updateQuery3.setParameter("betId", bets.getBetId());
          updateQuery3.setParameter("newWin", bets.getWin());
          int rowsAffected3 = updateQuery3.executeUpdate();
                   
          String updateSQL4 = "update UserInfo u set u.money= :newMoney where u.userInfoId = :userInfoId";
          Query updateQuery4 = entityManager.createQuery(updateSQL4);
          updateQuery4.setParameter("userInfoId", userInfo.getUserInfoId());
          updateQuery4.setParameter("newMoney", newMoneyTotal);
          int rowsAffected4 = updateQuery4.executeUpdate();
         
          
          userTransaction.commit();
          returnValue = "BlackJackResult";
      }
      catch (Exception e) 
      {
            System.out.println(e.toString());
            e.printStackTrace();     
      }
      entityManager.close();    
      return returnValue;
  }
  
  public void startNewDeck()
  {
      deck.clear();
      hand.clear();
      deck.add("11ASpades"); deck.add("2Spades"); deck.add("3Spades"); deck.add("4Spades"); deck.add("5Spades"); deck.add("6Spades"); deck.add("7Spades"); deck.add("8Spades"); deck.add("9Spades"); deck.add("10Spades"); deck.add("10JSpades"); deck.add("10QSpades"); deck.add("10KSpades");
      deck.add("11ADiamonds"); deck.add("2Diamonds"); deck.add("3Diamonds"); deck.add("4Diamonds"); deck.add("5Diamonds"); deck.add("6Diamonds"); deck.add("7Diamonds"); deck.add("8Diamonds"); deck.add("9Diamonds"); deck.add("10Diamonds"); deck.add("10JDiamonds"); deck.add("10QDiamonds"); deck.add("10KDiamonds");
      deck.add("11AHearts"); deck.add("2Hearts"); deck.add("3Hearts"); deck.add("4Hearts"); deck.add("5Hearts"); deck.add("6Hearts"); deck.add("7Hearts"); deck.add("8Hearts"); deck.add("9Hearts"); deck.add("10Hearts"); deck.add("10JHearts"); deck.add("10QHearts"); deck.add("10KHearts");
      deck.add("11AClubs"); deck.add("2Clubs"); deck.add("3Clubs"); deck.add("4Clubs"); deck.add("5Clubs"); deck.add("6Clubs"); deck.add("7Clubs"); deck.add("8Clubs"); deck.add("9Clubs"); deck.add("10Clubs"); deck.add("10JClubs"); deck.add("10QClubs"); deck.add("10KClubs");
  }
  
  public String getNewCard()
  {
      int cardValue = 0;
      int deckSize = deck.size();
      int randomCard = 0 + (int)(Math.random()*(deckSize-1));
      String card = deck.get(randomCard);
      deck.remove(randomCard);    
      
      return card;
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
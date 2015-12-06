package edu.ccsu.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.persistence.Table;
import javax.persistence.Transient;

@ManagedBean
@SessionScoped
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    



  @Id
  private String username;

  private String password;
  
  //private double money = 50.0;
  @OneToOne(mappedBy = "user")
  private UserInfo userInfo;
  //private double newMoney;

  @ManyToMany(mappedBy = "users")
  private Set<Group> groups = new HashSet();

  @Transient
  private String[] groupNames;

//  public void addFunds(ActionEvent event) {
//       money = money + getNewMoney();      
//  }
  
  public User() {
  }
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the groups
   */
  public String[] getGroupNames() {
    return groupNames;
  }

  /**
   * @param groups the groups to set
   */
  public void setGroupNames(String[] groupNames) {
    this.groupNames = groupNames;
  }

  /**
   * @return the groups
   */
  public Set<Group> getGroups() {
    return groups;
  }

  /**
   * @param groups the groups to set
   */
  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

//    /**
//     * @return the money
//     */
//    public double getMoney() {
//        return money;
//    }
//
//    /**
//     * @param money the money to set
//     */
//    public void setMoney(double money) {
//        this.money = money;
//    }
//
//    /**
//     * @return the newMoney
//     */
//    public double getNewMoney() {
//        return newMoney;
//    }
//
//    /**
//     * @param newMoney the newMoney to set
//     */
//    public void setNewMoney(double newMoney) {
//        this.newMoney = newMoney;
//    }
}

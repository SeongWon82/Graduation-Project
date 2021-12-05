package com.seongwon.publictransport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
public class User
{

  @Id
  @Column(name="id")
  private String id;

  @Column(name="pwd")
  private String pwd;

  @Column(name="email")
  private String email;
  
  @Column(name="user_type")
  private String user_type;

  public User(String id,String pwd,String email,String user_type)
  {
	  this.id = id;
	  this.pwd= pwd;
	  this.email =email;
	  this.user_type = user_type;
  }
  
  public User() {}
  
  public String getUser_type() {
	return user_type;
  }
  public void setUser_type(String user_type) {
	this.user_type = user_type;
  }
  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPwd() {
    return this.pwd;
  }
  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
}

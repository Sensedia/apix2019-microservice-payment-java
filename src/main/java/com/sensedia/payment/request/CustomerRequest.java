package com.sensedia.payment.request;

public class CustomerRequest {
  
  private String document;
  private String name;
  private String email;
  private String phone;
  private String password;
  private Integer expirationDay;
  
  public String getDocument() {
    return document;
  }
  public void setDocument(String document) {
    this.document = document;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public Integer getExpirationDay() {
    return expirationDay;
  }
  public void setExpirationDay(Integer expirationDay) {
    this.expirationDay = expirationDay;
  }
  
  

}

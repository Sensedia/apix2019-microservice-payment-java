package com.sensedia.payment.response;

import java.util.UUID;

public class CustomerResponse {

  private UUID id;
  private String document;
  private String name;
  private String email;
  private String phone;
  private Integer expirationDay;

  public CustomerResponse(UUID id, String document, String name, String email, String phone, Integer expirationDay) {
    this.id = id;
    this.document = document;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.expirationDay = expirationDay;
  }

  public CustomerResponse() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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

  public Integer getExpirationDay() {
    return expirationDay;
  }

  public void setExpirationDay(Integer expirationDay) {
    this.expirationDay = expirationDay;
  }

}

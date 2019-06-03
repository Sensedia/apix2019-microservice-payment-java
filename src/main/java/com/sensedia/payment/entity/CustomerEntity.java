package com.sensedia.payment.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "customer")
public class CustomerEntity {

  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "VARCHAR(36)", nullable = false)
  private UUID id;

  @Column
  private String document;

  @Column
  private String password;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private Integer expirationDay;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
  private List<DebitEntity> debits;

  public CustomerEntity(String document, String password, String name, String email, String phone, Integer expirationDay) {
    this.document = document;
    this.password = password;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.expirationDay = expirationDay;
  }

  public CustomerEntity() {
  }
  
  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public List<DebitEntity> getDebits() {
    return debits;
  }

  public UUID getId() {
    return id;
  }

}

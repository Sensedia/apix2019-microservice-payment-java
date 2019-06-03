package com.sensedia.payment.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "debit")
public class DebitEntity {

  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "VARCHAR(36)", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customerId", nullable = false)
  private CustomerEntity customer;

  @Column
  private String productId;

  @Column
  private String description;

  @Column
  private BigDecimal value;

  @Column
  private Integer installmentsNumber;

  @Column
  private Integer discountPercentage;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "debit")
  private List<InstallmentEntity> installments;

  public DebitEntity(CustomerEntity customer, String productId, String description, BigDecimal value, Integer installmentsNumber) {
    this.customer = customer;
    this.productId = productId;
    this.description = description;
    this.value = value;
    this.installmentsNumber = installmentsNumber;
  }
  
  public DebitEntity() {
  }
  
  public UUID getId() {
    return id;
  }

  public CustomerEntity getCustomer() {
    return customer;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public Integer getInstallmentsNumber() {
    return installmentsNumber;
  }

  public void setInstallmentsNumber(Integer installmentsNumber) {
    this.installmentsNumber = installmentsNumber;
  }

  public Integer getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(Integer discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public List<InstallmentEntity> getInstallments() {
    return installments;
  }

  public void setInstallments(List<InstallmentEntity> installments) {
    this.installments = installments;
  }
  
}

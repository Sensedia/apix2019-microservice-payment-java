package com.sensedia.payment.request;

import java.math.BigDecimal;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.entity.DebitEntity;

public class DebitRequest {

  private String productId;

  private String description;

  private BigDecimal value;

  private Integer installmentsNumber;

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

  public DebitEntity toEntity(CustomerEntity customer) {
    return new DebitEntity(customer, productId, description, value, installmentsNumber);
  }

}

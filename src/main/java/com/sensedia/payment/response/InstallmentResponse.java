package com.sensedia.payment.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.enumeration.InstallmentStatus;

@JsonInclude(value = Include.NON_NULL)
public class InstallmentResponse {

  private BigDecimal value;

  private InstallmentStatus status;

  private String expirationDate;

  private String payday;

  private BigDecimal paidValue;

  private Integer appliedDiscountPercentage;

  public static InstallmentResponse valueOf(InstallmentEntity installmentEntity) {
    return new InstallmentResponse(installmentEntity.getValue(), installmentEntity.getStatus(), installmentEntity.getExpirationDate().toString(),
        Optional.ofNullable(installmentEntity.getPayday()).map(LocalDateTime::toString).orElse(null), installmentEntity.getPaidValue(),
        installmentEntity.getAppliedDiscount());
  }

  public InstallmentResponse(BigDecimal value, InstallmentStatus status, String expirationDate, String payday, BigDecimal paidValue,
      Integer appliedDiscountPercentage) {
    this.value = value;
    this.status = status;
    this.expirationDate = expirationDate;
    this.payday = payday;
    this.paidValue = paidValue;
    this.appliedDiscountPercentage = appliedDiscountPercentage;
  }

  public InstallmentResponse() {}

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public InstallmentStatus getStatus() {
    return status;
  }

  public void setStatus(InstallmentStatus status) {
    this.status = status;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getPayday() {
    return payday;
  }

  public void setPayday(String payday) {
    this.payday = payday;
  }

  public BigDecimal getPaidValue() {
    return paidValue;
  }

  public void setPaidValue(BigDecimal paidValue) {
    this.paidValue = paidValue;
  }

  public Integer getAppliedDiscountPercentage() {
    return appliedDiscountPercentage;
  }

  public void setAppliedDiscountPercentage(Integer appliedDiscountPercentage) {
    this.appliedDiscountPercentage = appliedDiscountPercentage;
  }

}

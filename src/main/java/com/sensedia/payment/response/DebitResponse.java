package com.sensedia.payment.response;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;

public class DebitResponse {

  private String id;

  private String productId;

  private String description;

  private BigDecimal value;

  private Integer antecipationDiscountPercentage;

  private Integer installmentsNumber;

  private List<InstallmentResponse> installments;

  public static DebitResponse valueOf(DebitEntity debitEntity) {
    debitEntity.getInstallments().sort(Comparator.comparing(InstallmentEntity::getExpirationDate));
    List<InstallmentResponse> installments = debitEntity.getInstallments().stream().map(InstallmentResponse::valueOf).collect(Collectors.toList());

    return new DebitResponse(debitEntity.getId().toString(), debitEntity.getProductId(), debitEntity.getDescription(), debitEntity.getValue(),
        debitEntity.getDiscountPercentage(), debitEntity.getInstallmentsNumber(), installments);
  }

  public DebitResponse(String id, String productId, String description, BigDecimal value, Integer antecipationDiscountPercentage, Integer installmentsNumber,
      List<InstallmentResponse> installments) {
    this.id = id;
    this.productId = productId;
    this.description = description;
    this.value = value;
    this.antecipationDiscountPercentage = antecipationDiscountPercentage;
    this.installmentsNumber = installmentsNumber;
    this.installments = installments;
  }

  public DebitResponse() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Integer getAntecipationDiscountPercentage() {
    return antecipationDiscountPercentage;
  }

  public void setAntecipationDiscountPercentage(Integer antecipationDiscountPercentage) {
    this.antecipationDiscountPercentage = antecipationDiscountPercentage;
  }

  public Integer getInstallmentsNumber() {
    return installmentsNumber;
  }

  public void setInstallmentsNumber(Integer installmentsNumber) {
    this.installmentsNumber = installmentsNumber;
  }

  public List<InstallmentResponse> getInstallments() {
    return installments;
  }

  public void setInstallments(List<InstallmentResponse> installments) {
    this.installments = installments;
  }

}

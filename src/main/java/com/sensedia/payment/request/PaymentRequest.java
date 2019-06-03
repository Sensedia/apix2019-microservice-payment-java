package com.sensedia.payment.request;

public class PaymentRequest {

  private String debitId;

  private Integer installments;

  private String cardNumber;

  public String getDebitId() {
    return debitId;
  }

  public void setDebitId(String debitId) {
    this.debitId = debitId;
  }

  public Integer getInstallments() {
    return installments;
  }

  public void setInstallments(Integer installments) {
    this.installments = installments;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

}

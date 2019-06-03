package com.sensedia.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import com.sensedia.payment.enumeration.InstallmentStatus;

@Entity
@Table(name = "installment")
public class InstallmentEntity {

  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "VARCHAR(36)", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "debitId")
  private DebitEntity debit;

  @Column
  private BigDecimal value;

  @Column
  @Enumerated(EnumType.STRING)
  private InstallmentStatus status;

  @Column
  private LocalDate expirationDate;

  @Column
  private LocalDateTime payday;

  @Column
  private BigDecimal paidValue;

  @Column
  private Integer appliedDiscount;

  public InstallmentEntity(DebitEntity debit, BigDecimal value, InstallmentStatus status, LocalDate expirationDate) {
    this.debit = debit;
    this.value = value;
    this.status = status;
    this.expirationDate = expirationDate;
  }
  
  public InstallmentEntity() {
  }

  public UUID getId() {
    return id;
  }

  public DebitEntity getDebit() {
    return debit;
  }

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

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  public LocalDateTime getPayday() {
    return payday;
  }

  public void setPayday(LocalDateTime payday) {
    this.payday = payday;
  }

  public BigDecimal getPaidValue() {
    return paidValue;
  }

  public void setPaidValue(BigDecimal paidValue) {
    this.paidValue = paidValue;
  }

  public Integer getAppliedDiscount() {
    return appliedDiscount;
  }

  public void setAppliedDiscount(Integer appliedDiscount) {
    this.appliedDiscount = appliedDiscount;
  }

}

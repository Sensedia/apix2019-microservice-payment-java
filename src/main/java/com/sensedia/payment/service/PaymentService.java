package com.sensedia.payment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sensedia.payment.client.service.NotifyService;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.enumeration.InstallmentStatus;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.repository.DebitRepository;
import com.sensedia.payment.repository.InstallmentRepository;
import com.sensedia.payment.request.PaymentRequest;
import com.sensedia.payment.validator.UuidValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private static final String TEXT_MESSAGE = "Payment of %s installments made for the product %s";
  private final DebitRepository debitRepository;
  private final InstallmentRepository installmentRepository;
  private final NotifyService notifyService;

  public void payment(PaymentRequest request) {
    UUID debitUUID = UuidValidator.validateIdAndGetUUID(request.getDebitId());
    DebitEntity debit = debitRepository.findById(debitUUID)
        .orElseThrow(() -> new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Debit", request.getDebitId())));
    List<InstallmentEntity> installments = installmentRepository.findByDebitIdAndStatusOrderByExpirationDateAsc(debit.getId(), InstallmentStatus.PENDING);

    if (installments.size() < request.getInstallments()) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.INVALID_INSTALLMENTS));
    }

    List<InstallmentEntity> installmentsToPay = installments.stream().limit(request.getInstallments()).collect(Collectors.toList());

    boolean isAntecipation = request.getInstallments() > 1;

    for (int i = 0; i < installmentsToPay.size(); i++) {
      InstallmentEntity installment = installmentsToPay.get(i);
      installment.setStatus(InstallmentStatus.PAID);
      installment.setPayday(LocalDateTime.now());

      BigDecimal paidValue = installment.getValue();
      Integer appliedDiscount = 0;
      if (isAntecipation) {
        paidValue = installment.getValue()
            .subtract(installment.getValue().divide(new BigDecimal("100"), RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(debit.getDiscountPercentage())));
        appliedDiscount = debit.getDiscountPercentage();
      }
      installment.setPaidValue(paidValue);
      installment.setAppliedDiscount(appliedDiscount);
    }
    
    installmentRepository.saveAll(installmentsToPay);
    notifyService.sendSmsMessage(debit.getCustomer().getPhone(), String.format(TEXT_MESSAGE, installmentsToPay.size(), debit.getDescription()));
  }

}

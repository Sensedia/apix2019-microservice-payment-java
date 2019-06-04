package com.sensedia.payment.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sensedia.payment.client.service.RegisterClientService;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.exception.EntityNotFoundException;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.repository.DebitRepository;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.DebitResponse;

@Service
public class DebitService {

  private final DebitRepository debitRepository;
  private final CustomerService customerService;
  private final InstallmentService installmentService;
  private final RegisterClientService registerClientService;
  
  public DebitService(DebitRepository debitRepository, CustomerService customerService, InstallmentService installmentService,
      RegisterClientService registerClientService) {
    this.debitRepository = debitRepository;
    this.customerService = customerService;
    this.installmentService = installmentService;
    this.registerClientService = registerClientService;
  }

  public UUID create(UUID customerUUID, DebitRequest debitRequest) {
    CustomerEntity customerEntity = customerService.validateAndGetCustomerById(customerUUID);
    DebitEntity debit = debitRequest.toEntity(customerEntity);
    List<InstallmentEntity> installments = installmentService.generateInstallments(customerEntity.getExpirationDay(), debitRequest.getInstallmentsNumber(), debitRequest.getValue(), debit);
    debit.setInstallments(installments);
    
    Integer score = registerClientService.getScore(customerEntity.getDocument());
    debit.setDiscountPercentage(score / 100);
    
    return debitRepository.save(debit).getId();
  }

  public DebitResponse findById(UUID customerUUID, UUID debitUUID) {
    customerService.validateAndGetCustomerById(customerUUID);
    DebitEntity debitEntity = debitRepository.findById(debitUUID).orElseThrow(EntityNotFoundException::new);
    return DebitResponse.valueOf(debitEntity);
  }

  public List<DebitResponse> findByCustomerId(UUID customerUUID) {
    return debitRepository.findByCustomerId(customerUUID).stream().map(DebitResponse::valueOf).collect(Collectors.toList());
  }

  public void deleteDebit(UUID customerUUID, UUID debitUUID) {
    customerService.validateAndGetCustomerById(customerUUID);
    if (!debitRepository.existsById(debitUUID)) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Debit", debitUUID));
    }
    debitRepository.deleteById(debitUUID);
  }

}

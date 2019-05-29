package com.sensedia.payment.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sensedia.payment.client.service.RegisterClientService;
import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.exception.EntityNotFoundException;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.repository.DebitRepository;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.DebitResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebitService {

  private final DebitRepository debitRepository;
  private final ClientService clientService;
  private final InstallmentService installmentService;
  private final RegisterClientService registerClientService;

  public UUID create(UUID clientUUID, DebitRequest debitRequest) {
    ClientEntity clientEntity = clientService.validateAndGetClientById(clientUUID);
    DebitEntity debit = debitRequest.toEntity(clientEntity);
    List<InstallmentEntity> installments = installmentService.generateInstallments(clientEntity.getExpirationDay(), debitRequest.getInstallmentsNumber(), debitRequest.getValue(), debit);
    debit.setInstallments(installments);
    
    Integer score = registerClientService.getScore(clientEntity.getDocument());
    debit.setDiscountPercentage(score / 100);
    
    return debitRepository.save(debit).getId();
  }

  public DebitResponse findById(UUID clientUUID, UUID debitUUID) {
    clientService.validateAndGetClientById(clientUUID);
    DebitEntity debitEntity = debitRepository.findById(debitUUID).orElseThrow(EntityNotFoundException::new);
    return DebitResponse.valueOf(debitEntity);
  }

  public List<DebitResponse> findByClientId(UUID clientUUID) {
    clientService.validateAndGetClientById(clientUUID);
    return debitRepository.findByClientId(clientUUID).stream().map(DebitResponse::valueOf).collect(Collectors.toList());
  }

  public void deleteDebit(UUID clientUUID, UUID debitUUID) {
    clientService.validateAndGetClientById(clientUUID);
    if (!debitRepository.existsById(debitUUID)) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Debit", debitUUID));
    }
    debitRepository.deleteById(debitUUID);
  }


}

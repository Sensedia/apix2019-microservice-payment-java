package com.sensedia.payment.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sensedia.payment.domain.ClientEntity;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.exceptions.EntityNotFoundException;
import com.sensedia.payment.exceptions.ErrorMessage;
import com.sensedia.payment.exceptions.MessageError;
import com.sensedia.payment.exceptions.UnprocessableEntityException;
import com.sensedia.payment.repository.DebitRepository;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.DebitResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebitService {

  private final DebitRepository debitRepository;
  private final ClientService clientService;

  public UUID create(UUID clientUUID, DebitRequest paymentDebitRequest) {
    ClientEntity clientEntity = clientService.validateAndGetClientById(clientUUID);
    DebitEntity debitEntity = debitRepository.save(paymentDebitRequest.toEntity(clientEntity));
    return debitEntity.getId();
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
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Debit " + debitUUID));
    }
    debitRepository.deleteById(debitUUID);
  }


}

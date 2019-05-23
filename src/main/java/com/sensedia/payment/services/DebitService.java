package com.sensedia.payment.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.exceptions.EntityNotFoundException;
import com.sensedia.payment.repository.DebitRepository;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.DebitResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebitService {

  private final DebitRepository debitRepository;

  public UUID create(DebitRequest debitRequest) {
    DebitEntity debitEntity = debitRepository.save(debitRequest.toEntity());
    return debitEntity.getId();
  }

  public DebitResponse findById(UUID id) {
    DebitEntity debitEntity = debitRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return DebitResponse.valueOf(debitEntity);
  }

  public List<DebitResponse> findByClientId(String clientId) {
    return debitRepository.findByClientId(clientId).stream().map(DebitResponse::valueOf).collect(Collectors.toList());
  }

  public void deleteDebit(UUID id) {
    debitRepository.deleteById(id);
  }

}

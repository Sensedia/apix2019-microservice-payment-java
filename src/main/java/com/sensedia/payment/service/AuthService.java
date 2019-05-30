package com.sensedia.payment.service;

import org.springframework.stereotype.Service;
import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.request.AuthRequest;
import com.sensedia.payment.utils.HashUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final ClientService clientService;

  public void validateUser(AuthRequest request) {
    ClientEntity clientEntity = clientService.validateAndGetClientByDocument(request.getDocument());
    String passwordHash = HashUtils.generateHash(request.getPassword(), "password");
    if (!clientEntity.getPassword().contentEquals(passwordHash)) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.INVALID_FIELD, "document or password"));
    }
  }
}

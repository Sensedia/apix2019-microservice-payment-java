package com.sensedia.payment.service;

import org.springframework.stereotype.Service;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.request.AuthRequest;
import com.sensedia.payment.utils.HashUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final CustomerService customerService;

  public void validateUser(AuthRequest request) {
    CustomerEntity customerEntity = customerService.validateAndGetCustomerByDocument(request.getDocument());
    String passwordHash = HashUtils.generateHash(request.getPassword(), "password");
    if (!customerEntity.getPassword().contentEquals(passwordHash)) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.AUTH_FAILED));
    }
  }
}

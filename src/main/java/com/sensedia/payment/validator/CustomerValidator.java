package com.sensedia.payment.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.request.CustomerRequest;
import com.sensedia.payment.request.DebitRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerValidator {

  public static void validate(CustomerRequest request) {

    List<MessageError> errors = new ArrayList<>();

    String document = request.getDocument();
    if (StringUtils.isEmpty(document)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "document"));
    } else if (document.length() != 11) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, "document"));
    }

    String name = request.getName();
    if (StringUtils.isEmpty(name)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "name"));
    }

    String email = request.getEmail();
    if (StringUtils.isEmpty(email)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "email"));
    }

    String phone = request.getPhone();
    if (StringUtils.isEmpty(phone)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "phone"));
    } else if (!isValidPhone(phone)) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, "phone"));
    }

    Integer expirationDay = request.getExpirationDay();
    if (expirationDay == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "expirationDay"));
    } else if (expirationDay < 1 || expirationDay > 28) {
      errors.add(new MessageError(ErrorMessage.INVALID_EXPIRATION_DAY, expirationDay));
    }

    if (StringUtils.isEmpty(request.getPassword())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "password"));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
  }

  public static void validateDebit(DebitRequest debitRequest) {
    List<MessageError> errors = new ArrayList<>();

    
    if (StringUtils.isEmpty(debitRequest.getProductId())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "productId"));
    }
    
    if (StringUtils.isEmpty(debitRequest.getDescription())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "description"));
    }
    
    if (debitRequest.getInstallmentsNumber() == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "installmentsNumber"));
    } else if (debitRequest.getInstallmentsNumber() < 1) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, "installmentsNumber"));
    }
    
    if (debitRequest.getValue() == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "value"));
    } else if (debitRequest.getValue().compareTo(BigDecimal.ZERO) <= 0) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, "value"));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
    
  }
  
  private static boolean isValidPhone(String phone) {
	return StringUtils.trimAllWhitespace(phone).matches("^\\+[1-9][1-9][1-9][1-9](?:[2-8]|9[1-9])[0-9]{7}$");
  }

}

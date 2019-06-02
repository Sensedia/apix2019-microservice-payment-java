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

  private static final String PHONE_PATTERN = "^\\+[1-9][1-9][1-9][1-9](?:[2-8]|9[1-9])[0-9]{7}$";
  private static final String VALUE_FIELD = "value"; 
  private static final String INSTALLMENTS_NUMBER_FIELD = "installmentsNumber";
  private static final String DESCRIPTION_FIELD = "description";
  private static final String PRODUCT_ID_FIELD = "productId";
  private static final String PASS_FIELD = "password";
  private static final String EXPIRATION_DAY_FIELD = "expirationDay";
  private static final String PHONE_FIELD = "phone";
  private static final String EMAIL_FIELD = "email";
  private static final String NAME_FIELD = "name";
  private static final String DOCUMENT_FIELD = "document";

  public static void validate(CustomerRequest request) {
    List<MessageError> errors = new ArrayList<>();

    String document = request.getDocument();
    if (StringUtils.isEmpty(document)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, DOCUMENT_FIELD));
    } else if (document.length() != 11) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, DOCUMENT_FIELD));
    }

    String name = request.getName();
    if (StringUtils.isEmpty(name)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, NAME_FIELD));
    }

    String email = request.getEmail();
    if (StringUtils.isEmpty(email)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, EMAIL_FIELD));
    }

    String phone = request.getPhone();
    if (StringUtils.isEmpty(phone)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, PHONE_FIELD));
    } else if (!isValidPhone(phone)) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, PHONE_FIELD));
    }

    Integer expirationDay = request.getExpirationDay();
    if (expirationDay == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, EXPIRATION_DAY_FIELD));
    } else if (expirationDay < 1 || expirationDay > 28) {
      errors.add(new MessageError(ErrorMessage.INVALID_EXPIRATION_DAY, expirationDay));
    }

    if (StringUtils.isEmpty(request.getPassword())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, PASS_FIELD));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
  }

  public static void validateDebit(DebitRequest debitRequest) {
    List<MessageError> errors = new ArrayList<>();

    
    if (StringUtils.isEmpty(debitRequest.getProductId())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, PRODUCT_ID_FIELD));
    }
    
    if (StringUtils.isEmpty(debitRequest.getDescription())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, DESCRIPTION_FIELD));
    }
    
    if (debitRequest.getInstallmentsNumber() == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, INSTALLMENTS_NUMBER_FIELD));
    } else if (debitRequest.getInstallmentsNumber() < 1) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, INSTALLMENTS_NUMBER_FIELD));
    }
    
    if (debitRequest.getValue() == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, VALUE_FIELD));
    } else if (debitRequest.getValue().compareTo(BigDecimal.ZERO) <= 0) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, VALUE_FIELD));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
    
  }
  
  public static void validatePartialUpdate(CustomerRequest request) {
    List<MessageError> errors = new ArrayList<>();

    String document = request.getDocument();
    if (!StringUtils.isEmpty(document) && document.length() != 11) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, DOCUMENT_FIELD));
    }

    String phone = request.getPhone();
    if (!StringUtils.isEmpty(phone) && !isValidPhone(phone)) {
      errors.add(new MessageError(ErrorMessage.INVALID_FIELD, PHONE_FIELD));
    }

    Integer expirationDay = request.getExpirationDay();
    if (expirationDay != null && (expirationDay < 1 || expirationDay > 28)) {
      errors.add(new MessageError(ErrorMessage.INVALID_EXPIRATION_DAY, expirationDay));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
  }
  
  private static boolean isValidPhone(String phone) {
	return StringUtils.trimAllWhitespace(phone).matches(PHONE_PATTERN);
  }

}

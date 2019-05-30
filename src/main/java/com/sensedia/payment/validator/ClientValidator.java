package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.request.DebitRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientValidator {

  public static void validate(ClientRequest request) {

    List<MessageError> errors = new ArrayList<>();

    String document = request.getDocument();
    if (StringUtils.isEmpty(document)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "document"));
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
    if (StringUtils.isEmpty(expirationDay)) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "expirationDay"));
    }

    if (StringUtils.isEmpty(request.getPassword())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "password"));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
  }

  private static boolean isValidPhone(String phone) {
    return StringUtils.trimAllWhitespace(phone).matches("^\\+[1-9][1-9][1-9][1-9](?:[2-8]|9[1-9])[0-9]{7}$");
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
    }
    
    if (debitRequest.getValue() == null) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "value"));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
    
  }

}

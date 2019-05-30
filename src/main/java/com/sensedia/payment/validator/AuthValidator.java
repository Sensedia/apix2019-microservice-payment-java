package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.request.AuthRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthValidator {

  public static void validate(AuthRequest request) {

    List<MessageError> errors = new ArrayList<>();

    if (StringUtils.isEmpty(request.getDocument())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "document"));
    }

    if (StringUtils.isEmpty(request.getPassword())) {
      errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "password"));
    }

    if (!errors.isEmpty()) {
      throw new PreconditionFailedException(errors);
    }
  }
}

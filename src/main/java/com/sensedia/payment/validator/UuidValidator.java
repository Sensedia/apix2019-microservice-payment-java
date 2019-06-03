package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.utils.UUIDUtils;

public class UuidValidator {

  public static UUID validateIdAndGetUUID(String id) {
    if (!UUIDUtils.validateId(id)) {
      List<MessageError> errors = new ArrayList<>();
      errors.add(new MessageError(ErrorMessage.INVALID_UUID, id));
      throw new PreconditionFailedException(errors);
    }
    return UUID.fromString(id);
  }

  private UuidValidator() {}
}

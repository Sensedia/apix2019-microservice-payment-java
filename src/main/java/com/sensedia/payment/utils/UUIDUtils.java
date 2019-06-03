package com.sensedia.payment.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;

public class UUIDUtils {

  public static boolean validateId(String uuid) {
    try {
      return UUID.fromString(uuid).toString().equals(uuid);
    } catch (IllegalArgumentException ex) {
      List<MessageError> errors = new ArrayList<>();
      errors.add(new MessageError(ErrorMessage.INVALID_UUID, uuid));
      throw new PreconditionFailedException(errors);
    }
  }

  public static boolean validateId(String uuid, String fieldName) {
    try {
      return UUID.fromString(uuid).toString().equals(uuid);
    } catch (IllegalArgumentException ex) {
      List<MessageError> errors = new ArrayList<>();
      errors.add(new MessageError(ErrorMessage.INVALID_UUID, fieldName));
      throw new PreconditionFailedException(errors);
    }
  }

  private UUIDUtils() {}
  
}

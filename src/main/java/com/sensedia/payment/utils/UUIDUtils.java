package com.sensedia.payment.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sensedia.payment.exceptions.ErrorMessage;
import com.sensedia.payment.exceptions.MessageError;
import com.sensedia.payment.exceptions.PreconditionFailedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
}
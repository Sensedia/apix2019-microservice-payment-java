package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sensedia.payment.exceptions.ErrorMessage;
import com.sensedia.payment.exceptions.MessageError;
import com.sensedia.payment.exceptions.PreconditionFailedException;
import com.sensedia.payment.utils.UUIDUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UuidValidator {

    public static UUID validateIdAndGetUUID(String id) {
        if (!UUIDUtils.validateId(id)) {
            List<MessageError> errors = new ArrayList<>();
            errors.add(new MessageError(ErrorMessage.INVALID_UUID, id));
            throw new PreconditionFailedException(errors);
        }
        return UUID.fromString(id);
    }
}
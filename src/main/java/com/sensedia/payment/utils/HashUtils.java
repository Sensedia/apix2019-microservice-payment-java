package com.sensedia.payment.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.InternalServerErrorException;
import com.sensedia.payment.exception.MessageError;

public class HashUtils {

  public static String generateHash(String data, String field) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      return toBase64(messageDigest.digest(data.getBytes()));
    } catch (NoSuchAlgorithmException e) {
      throw new InternalServerErrorException(new MessageError(ErrorMessage.ENCRYPT_FAILED, field));
    }
  }

  private static String toBase64(final byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  private HashUtils() {}

}

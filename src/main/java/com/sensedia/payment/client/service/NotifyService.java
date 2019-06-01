package com.sensedia.payment.client.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotifyService {

  @Value("${TWILIO_ACCOUNT_SID}")
  private String accountSid;
  
  @Value("${TWILIO_AUTH_TOKEN}")
  private String authToken;
  
  @Value("${TWILIO_ORIGIN_PHONE}")
  private String originPhone;

  public void sendSmsMessage(String recipientPhone, String textMessage) {
    try {
      log.debug("Try send notify message");
      Twilio.init(accountSid, authToken);

      Message message = Message.creator(new PhoneNumber(recipientPhone), new PhoneNumber(originPhone), textMessage).create();

      log.debug("notify message id {}", message.getSid());
    } catch (Exception e) {
      log.error("Error on send notify message. {}", e);
    }
  }
}

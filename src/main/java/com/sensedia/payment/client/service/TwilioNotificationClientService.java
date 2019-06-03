package com.sensedia.payment.client.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioNotificationClientService {

  private Logger log = LogManager.getLogger(this);

  @Value("${notification.twilio.account-sid}")
  private String accountSid;

  @Value("${notification.twilio.auth-token}")
  private String authToken;

  @Value("${notification.twilio.origin-phone}")
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

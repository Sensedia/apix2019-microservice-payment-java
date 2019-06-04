package com.sensedia.payment.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sensedia.payment.request.PaymentRequest;
import com.sensedia.payment.service.PaymentService;

@Controller
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {

  private PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> payments(@RequestBody PaymentRequest request) {
    paymentService.payment(request);
    return ResponseEntity.noContent().build();
  }

}

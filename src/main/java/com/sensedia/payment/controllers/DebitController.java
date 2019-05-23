package com.sensedia.payment.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.DebitResponse;
import com.sensedia.payment.services.DebitService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/debits")
public class DebitController {

  private final DebitService debitService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> createDebit(@RequestBody DebitRequest paymentDebitRequest) {
    UUID uuid = debitService.create(paymentDebitRequest);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uuid).toUri()).build();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<DebitResponse> getDebitById(@PathVariable String id) {
    DebitResponse debitResponse = debitService.findById(UUID.fromString(id));
    return ResponseEntity.ok(debitResponse);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<DebitResponse>> getDebitsByClientId(@RequestParam(value = "clientId", required = false) String clientId) {
    List<DebitResponse> debitResponse = debitService.findByClientId(clientId);
    if (debitResponse.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(debitResponse);
  }
}

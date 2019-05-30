package com.sensedia.payment.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.ClientResponse;
import com.sensedia.payment.response.DebitResponse;
import com.sensedia.payment.service.ClientService;
import com.sensedia.payment.service.DebitService;
import com.sensedia.payment.validator.ClientValidator;
import com.sensedia.payment.validator.UuidValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

  private final ClientService clientService;
  private final DebitService debitService;

  @GetMapping
  public ResponseEntity<List<ClientResponse>> retrieveAllClients(@RequestParam(name = "document", required = false) String document) {
    List<ClientResponse> clientList = clientService.retrieveAllClients(document);
    if (clientList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(clientList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponse> retrieveClientById(@PathVariable String id) {
    UUID clientId = UuidValidator.validateIdAndGetUUID(id);
    ClientResponse client = clientService.retrieveClientById(clientId);
    return ResponseEntity.ok(client);
  }

  @PostMapping
  public ResponseEntity<Void> createNewClient(@RequestBody ClientRequest client) {
    ClientValidator.validate(client);
    UUID clientId = clientService.saveClientInfo(client);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientId).toUri()).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateClient(@RequestBody ClientRequest updateClient, @PathVariable String id) {
    UUID clientId = UuidValidator.validateIdAndGetUUID(id);
    ClientValidator.validate(updateClient);
    clientService.updateClientInfo(updateClient, clientId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> partialUpdateClient(@RequestBody ClientRequest partialUpdate, @PathVariable String id) {
    UUID clientId = UuidValidator.validateIdAndGetUUID(id);
    clientService.partialUpdateClientById(partialUpdate, clientId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public void deleteClient(@PathVariable String id) {
    UUID clientId = UuidValidator.validateIdAndGetUUID(id);
    clientService.deleteClientById(clientId);
  }

  @PostMapping(value = "/{clientId}/debits", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> createDebit(@PathVariable String clientId, @RequestBody DebitRequest debitRequest) {
    ClientValidator.validateDebit(debitRequest);
    UUID clientUUID = UuidValidator.validateIdAndGetUUID(clientId);
    UUID uuid = debitService.create(clientUUID, debitRequest);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uuid).toUri()).build();
  }

  @GetMapping(value = "/{clientId}/debits/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<DebitResponse> getDebitById(@PathVariable String clientId, @PathVariable String id) {
    UUID clientUUID = UuidValidator.validateIdAndGetUUID(clientId);
    UUID debitUUID = UuidValidator.validateIdAndGetUUID(id);
    DebitResponse debitResponse = debitService.findById(clientUUID, debitUUID);
    return ResponseEntity.ok(debitResponse);
  }

  @GetMapping(value = "/{clientId}/debits", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<DebitResponse>> getDebitsByClientId(@PathVariable String clientId) {
    UUID clientUUID = UuidValidator.validateIdAndGetUUID(clientId);
    List<DebitResponse> debitResponse = debitService.findByClientId(clientUUID);
    if (debitResponse.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(debitResponse);
  }

  @DeleteMapping(value = "/{clientId}/debits/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<DebitResponse>> deleteDebit(@PathVariable String clientId, @PathVariable String id) {
    UUID clientUUID = UuidValidator.validateIdAndGetUUID(clientId);
    UUID debitUUID = UuidValidator.validateIdAndGetUUID(id);
    debitService.deleteDebit(clientUUID,debitUUID);
    return ResponseEntity.noContent().build();
  }

}

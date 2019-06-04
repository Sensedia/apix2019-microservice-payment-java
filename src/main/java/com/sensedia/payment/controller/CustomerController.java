package com.sensedia.payment.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.sensedia.payment.request.CustomerRequest;
import com.sensedia.payment.request.DebitRequest;
import com.sensedia.payment.response.CustomerResponse;
import com.sensedia.payment.response.DebitResponse;
import com.sensedia.payment.service.CustomerService;
import com.sensedia.payment.service.DebitService;
import com.sensedia.payment.validator.CustomerValidator;
import com.sensedia.payment.validator.UuidValidator;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {

  private CustomerService customerService;
  private DebitService debitService;

  public CustomerController(CustomerService customerService, DebitService debitService) {
    this.customerService = customerService;
    this.debitService = debitService;
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> retrieveAllCustomers(@RequestParam(name = "document", required = false) String document) {
    List<CustomerResponse> customerList = customerService.findAllCustomers(document);
    if (customerList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(customerList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> retrieveCustomerById(@PathVariable String id) {
    UUID customerId = UuidValidator.validateIdAndGetUUID(id);
    CustomerResponse customer = customerService.findCustomerById(customerId);
    return ResponseEntity.ok(customer);
  }

  @PostMapping
  public ResponseEntity<Void> createNewCustomer(@RequestBody CustomerRequest customer) {
    CustomerValidator.validate(customer);
    UUID customerId = customerService.saveCustomerInfo(customer);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerId).toUri()).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateCustomer(@RequestBody CustomerRequest updateCustomer, @PathVariable String id) {
    UUID customerId = UuidValidator.validateIdAndGetUUID(id);
    CustomerValidator.validate(updateCustomer);
    customerService.updateCustomerInfo(updateCustomer, customerId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> partialUpdateCustomer(@RequestBody CustomerRequest partialUpdate, @PathVariable String id) {
    UUID customerId = UuidValidator.validateIdAndGetUUID(id);
    CustomerValidator.validatePartialUpdate(partialUpdate);
    customerService.partialUpdateCustumerById(partialUpdate, customerId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
    UUID customerId = UuidValidator.validateIdAndGetUUID(id);
    customerService.deleteCustomerById(customerId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/{customerId}/debits", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> createDebit(@PathVariable String customerId, @RequestBody DebitRequest debitRequest) {
    CustomerValidator.validateDebit(debitRequest);
    UUID customerUUID = UuidValidator.validateIdAndGetUUID(customerId);
    UUID uuid = debitService.create(customerUUID, debitRequest);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uuid).toUri()).build();
  }

  @GetMapping(value = "/{customerId}/debits/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<DebitResponse> getDebitById(@PathVariable String customerId, @PathVariable String id) {
    UUID customerUUID = UuidValidator.validateIdAndGetUUID(customerId);
    UUID debitUUID = UuidValidator.validateIdAndGetUUID(id);
    DebitResponse debitResponse = debitService.findById(customerUUID, debitUUID);
    return ResponseEntity.ok(debitResponse);
  }

  @GetMapping(value = "/{customerId}/debits", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<DebitResponse>> getDebitsByCustomerId(@PathVariable String customerId) {
    UUID customerUUID = UuidValidator.validateIdAndGetUUID(customerId);
    List<DebitResponse> debitResponse = debitService.findByCustomerId(customerUUID);
    if (debitResponse.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(debitResponse);
  }

  @DeleteMapping(value = "/{customerId}/debits/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<DebitResponse>> deleteDebit(@PathVariable String customerId, @PathVariable String id) {
    UUID customerUUID = UuidValidator.validateIdAndGetUUID(customerId);
    UUID debitUUID = UuidValidator.validateIdAndGetUUID(id);
    debitService.deleteDebit(customerUUID, debitUUID);
    return ResponseEntity.noContent().build();
  }

}

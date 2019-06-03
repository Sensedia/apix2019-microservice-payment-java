package com.sensedia.payment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sensedia.payment.converter.CustomerConverter;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.exception.EntityAlreadyExistsException;
import com.sensedia.payment.exception.EntityNotFoundException;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.repository.CustomerRepository;
import com.sensedia.payment.request.CustomerRequest;
import com.sensedia.payment.response.CustomerResponse;
import com.sensedia.payment.utils.HashUtils;

@Service
public class CustomerService {

  private static final String PHONE_FIELD = "phone";
  private static final String DOCUMENT_FIELD = "document";
  private static final String PASS_FIELD = "password";

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public UUID saveCustomerInfo(CustomerRequest request) {

    Optional<CustomerEntity> customerByDocument = customerRepository.findByDocument(request.getDocument());
    if (customerByDocument.isPresent()) {
      throw new EntityAlreadyExistsException(new MessageError(ErrorMessage.ALREADY_REGISTERED, DOCUMENT_FIELD));
    }

    Optional<CustomerEntity> customerByPhone = customerRepository.findByPhone(request.getPhone());
    if (customerByPhone.isPresent()) {
      throw new EntityAlreadyExistsException(new MessageError(ErrorMessage.ALREADY_REGISTERED, PHONE_FIELD));
    }

    String generatedPasswordHash = HashUtils.generateHash(request.getPassword(), PASS_FIELD);
    request.setPassword(generatedPasswordHash);
    CustomerEntity customer = customerRepository.save(CustomerConverter.toCustomer(request));
    return customer.getId();
  }

  public List<CustomerResponse> findAllCustomers(String document) {
    List<CustomerEntity> customers = new ArrayList<>();
    if (StringUtils.isEmpty(document)) {
      customerRepository.findAll().forEach(customers::add);
    } else {
      customerRepository.findByDocument(document).ifPresent(customers::add);
    }
    return customers.stream().map(CustomerConverter::toCustomerResponse).collect(Collectors.toList());
  }

  public CustomerResponse findCustomerById(UUID id) {
    return customerRepository.findById(id).map(CustomerConverter::toCustomerResponse).orElseThrow(EntityNotFoundException::new);
  }

  public void partialUpdateCustumerById(CustomerRequest customer, UUID id) {
    CustomerEntity customerUpdate = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    if (!StringUtils.isEmpty(customer.getDocument())) {
      Optional<CustomerEntity> customerByDocument = customerRepository.findByDocument(customer.getDocument());
      validateIfFieldAlreadyExistsToAnotherRegister(customerUpdate, customerByDocument, customer.getDocument());
      customerUpdate.setDocument(customer.getDocument());
    }
    if (!StringUtils.isEmpty(customer.getEmail())) {
      customerUpdate.setEmail(customer.getEmail());
    }
    if (!StringUtils.isEmpty(customer.getPhone())) {
      Optional<CustomerEntity> customerByPhone = customerRepository.findByPhone(customer.getPhone());
      validateIfFieldAlreadyExistsToAnotherRegister(customerUpdate, customerByPhone, customer.getPhone());
      customerUpdate.setPhone(customer.getPhone());
    }
    if (customer.getExpirationDay() != null) {
      customerUpdate.setExpirationDay(customer.getExpirationDay());
    }
    if (!StringUtils.isEmpty(customer.getName())) {
      customerUpdate.setName(customer.getName());
    }

    if (!StringUtils.isEmpty(customer.getPassword())) {
      customerUpdate.setPassword(HashUtils.generateHash(customer.getPassword(), PASS_FIELD));
    }

    customerRepository.save(customerUpdate);
  }

  public void updateCustomerInfo(CustomerRequest customer, UUID id) {
    CustomerEntity customerUpdate = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    Optional<CustomerEntity> customerByDocument = customerRepository.findByDocument(customer.getDocument());
    validateIfFieldAlreadyExistsToAnotherRegister(customerUpdate, customerByDocument, customer.getDocument());

    Optional<CustomerEntity> customerByPhone = customerRepository.findByPhone(customer.getPhone());
    validateIfFieldAlreadyExistsToAnotherRegister(customerUpdate, customerByPhone, customer.getPhone());

    customerUpdate.setDocument(customer.getDocument());
    customerUpdate.setEmail(customer.getEmail());
    customerUpdate.setPhone(customer.getPhone());
    customerUpdate.setExpirationDay(customer.getExpirationDay());
    customerUpdate.setName(customer.getName());
    customerUpdate.setPassword(HashUtils.generateHash(customer.getPassword(), PASS_FIELD));
    customerRepository.save(customerUpdate);
  }

  public void deleteCustomerById(UUID id) {
    if (!customerRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }
    customerRepository.deleteById(id);
  }

  public CustomerEntity validateAndGetCustomerById(UUID customerId) {
    Optional<CustomerEntity> opCustomer = customerRepository.findById(customerId);
    if (!opCustomer.isPresent()) {
      throw new EntityNotFoundException();
    }
    return opCustomer.get();
  }

  public CustomerEntity validateAndGetCustomerByDocument(String document) {
    Optional<CustomerEntity> opCustomer = customerRepository.findByDocument(document);
    if (!opCustomer.isPresent()) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, DOCUMENT_FIELD, document));
    }
    return opCustomer.get();
  }

  private void validateIfFieldAlreadyExistsToAnotherRegister(CustomerEntity customerToUpdate, Optional<CustomerEntity> customerFounded, String field) {
    if (customerFounded.isPresent()) {
      CustomerEntity customerEntity = customerFounded.get();
      if (!customerEntity.getId().equals(customerToUpdate.getId())) {
        throw new UnprocessableEntityException(new MessageError(ErrorMessage.ALREADY_REGISTERED_TO_ANOTHER_USER, field));
      }
    }
  }

}

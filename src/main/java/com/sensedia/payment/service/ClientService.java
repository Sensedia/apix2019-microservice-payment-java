package com.sensedia.payment.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sensedia.payment.converter.ClientConverter;
import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.exception.EntityAlreadyExistsException;
import com.sensedia.payment.exception.EntityNotFoundException;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.UnprocessableEntityException;
import com.sensedia.payment.repository.ClientRepository;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;
import com.sensedia.payment.utils.HashUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  /**
   * Method to get all clients.
   * 
   * @param document
   * @param email
   * @param phone
   * @return
   * @throws Exception
   */
  public List<ClientResponse> retrieveAllClients(String document) {
    List<ClientEntity> clients = new ArrayList<>();
    if (StringUtils.isEmpty(document)) {
      clientRepository.findAll().forEach(clients::add);
    } else {
      clientRepository.findByDocument(document).ifPresent(clients::add);
    }
    return clients.stream().map(ClientConverter::toClientResponse).collect(Collectors.toList());
  }

  /**
   * Method to get one client by id.
   * 
   * @param id
   * @return
   * @throws Exception
   * @throws EntityNotFoundException
   */
  public ClientResponse retrieveClientById(UUID id) {
    return clientRepository.findById(id).map(ClientConverter::toClientResponse).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Method to partial update of client's information.
   * 
   * @param id
   * @return
   * @throws EntityNotFoundException
   */
  public void partialUpdateClientById(ClientRequest client, UUID id) {

    ClientEntity clientUpdate = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    if (!StringUtils.isEmpty(client.getDocument())) {
      Optional<ClientEntity> clientByDocument = clientRepository.findByDocument(client.getDocument());
      validateIfFieldAlreadyExistsToAnotherRegister(clientUpdate, clientByDocument, client.getDocument());
      clientUpdate.setDocument(client.getDocument());
    }
    if (!StringUtils.isEmpty(client.getEmail())) {
      clientUpdate.setEmail(client.getEmail());
    }
    if (!StringUtils.isEmpty(client.getPhone())) {
      Optional<ClientEntity> clientByPhone = clientRepository.findByPhone(client.getPhone());
      validateIfFieldAlreadyExistsToAnotherRegister(clientUpdate, clientByPhone, client.getPhone());
      clientUpdate.setPhone(client.getPhone());
    }
    if (client.getExpirationDay() != null) {
      clientUpdate.setExpirationDay(client.getExpirationDay());
    }
    if (!StringUtils.isEmpty(client.getName())) {
      clientUpdate.setName(client.getName());
    }

    if (!StringUtils.isEmpty(client.getPassword())) {
      clientUpdate.setPassword(HashUtils.generateHash(client.getPassword(), "password"));
    }

    clientRepository.save(clientUpdate);
  }

  public void updateClientInfo(ClientRequest client, UUID id) {
    ClientEntity clientUpdate = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    Optional<ClientEntity> clientByDocument = clientRepository.findByDocument(client.getDocument());
    validateIfFieldAlreadyExistsToAnotherRegister(clientUpdate, clientByDocument, client.getDocument());

    Optional<ClientEntity> clientByPhone = clientRepository.findByPhone(client.getPhone());
    validateIfFieldAlreadyExistsToAnotherRegister(clientUpdate, clientByPhone, client.getPhone());

    clientUpdate.setDocument(client.getDocument());
    clientUpdate.setEmail(client.getEmail());
    clientUpdate.setPhone(client.getPhone());
    clientUpdate.setExpirationDay(client.getExpirationDay());
    clientUpdate.setName(client.getName());
    clientUpdate.setPassword(HashUtils.generateHash(client.getPassword(), "password"));
    clientRepository.save(clientUpdate);
  }

  /**
   * Method to save the client's information.
   * 
   * @param client
   * @return
   * @throws NoSuchAlgorithmException
   * @throws EntityNotFoundException
   */
  public UUID saveClientInfo(ClientRequest request) {

    Optional<ClientEntity> clientByDocument = clientRepository.findByDocument(request.getDocument());
    if (clientByDocument.isPresent()) {
      throw new EntityAlreadyExistsException(new MessageError(ErrorMessage.ALREADY_REGISTERED, "client"));
    }

    Optional<ClientEntity> clientByPhone = clientRepository.findByPhone(request.getPhone());
    if (clientByPhone.isPresent()) {
      throw new EntityAlreadyExistsException(new MessageError(ErrorMessage.ALREADY_REGISTERED, "phone"));
    }

    String generatedPasswordHash = HashUtils.generateHash(request.getPassword(), "password");
    request.setPassword(generatedPasswordHash);
    ClientEntity client = clientRepository.save(ClientConverter.toClient(request));
    return client.getId();
  }

  /**
   * Method to delete one client by id.
   * 
   * @param id
   */
  public void deleteClientById(UUID id) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }
    clientRepository.deleteById(id);
  }

  public ClientEntity validateAndGetClientById(UUID clientId) {
    Optional<ClientEntity> opClient = clientRepository.findById(clientId);
    if (!opClient.isPresent()) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Client", clientId));
    }
    return opClient.get();
  }

  public ClientEntity validateAndGetClientByDocument(String document) {
    Optional<ClientEntity> opClient = clientRepository.findByDocument(document);
    if (!opClient.isPresent()) {
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Document", document));
    }
    return opClient.get();
  }

  private void validateIfFieldAlreadyExistsToAnotherRegister(ClientEntity clientToUpdate, Optional<ClientEntity> clientFounded, String field) {
    if (clientFounded.isPresent()) {
      ClientEntity clientEntity = clientFounded.get();
      if (!clientEntity.getId().equals(clientToUpdate.getId())) {
        throw new UnprocessableEntityException(new MessageError(ErrorMessage.ALREADY_REGISTERED_TO_ANOTHER_USER, field));
      }
    }
  }

}

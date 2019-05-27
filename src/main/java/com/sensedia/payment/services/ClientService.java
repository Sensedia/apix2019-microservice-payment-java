package com.sensedia.payment.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sensedia.payment.converter.ClientConverter;
import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.exceptions.EntityNotFoundException;
import com.sensedia.payment.exceptions.ErrorMessage;
import com.sensedia.payment.exceptions.MessageError;
import com.sensedia.payment.exceptions.UnprocessableEntityException;
import com.sensedia.payment.repository.ClientRepository;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;
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
  public List<ClientResponse> retrieveAllClients(String document, String email, String phone) {
    return clientRepository.findAll().stream().map(ClientConverter::toClientResponse).collect(Collectors.toList());
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
      clientUpdate.setDocument(client.getDocument());
    }
    if (!StringUtils.isEmpty(client.getEmail())) {
      clientUpdate.setEmail(client.getEmail());
    }
    if (!StringUtils.isEmpty(client.getPhone())) {
      clientUpdate.setPhone(client.getPhone());
    }
    if (client.getPayday() != null) {
      clientUpdate.setPayday(client.getPayday());
    }
    if (!StringUtils.isEmpty(client.getName())) {
      clientUpdate.setName(client.getName());
    }
    clientRepository.save(clientUpdate);
  }

  public void updateClientInfo(ClientRequest client, UUID id) {
    ClientEntity clientUpdate = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    clientUpdate.setDocument(client.getDocument());
    clientUpdate.setEmail(client.getEmail());
    clientUpdate.setPhone(client.getPhone());
    clientUpdate.setPayday(client.getPayday());
    clientUpdate.setName(client.getName());
    clientRepository.save(clientUpdate);
  }

  /**
   * Method to save the client's information.
   * 
   * @param client
   * @return
   * @throws EntityNotFoundException
   */
  public UUID saveClientInfo(ClientRequest request) {
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
      throw new UnprocessableEntityException(new MessageError(ErrorMessage.FIELD_VALUE_NOT_EXISTS, "Client " + clientId));
    }
    return opClient.get();
  }
}

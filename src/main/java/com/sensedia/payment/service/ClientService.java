package com.sensedia.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sensedia.payment.converter.ClientConverter;
import com.sensedia.payment.domain.Client;
import com.sensedia.payment.exception.NotFoundException;
import com.sensedia.payment.repository.ClientRepository;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

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
		Client client = new Client(document, email, phone);
		return clientRepository.findAll(Example.of(client)).stream()
				.map(ClientConverter::toClientResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Method to get one client by id.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws NotFoundException
	 */
	public ClientResponse retrieveClientById(String id) {
		return clientRepository.findById(id)
				.map(ClientConverter::toClientResponse)
				.orElseThrow(NotFoundException::new);
	}

	/**
	 * Method to partial update of client's information.
	 * 
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	public void partialUpdateClientById(ClientRequest client, String id) {
		
		Client clientUpdate = clientRepository.findById(id).orElseThrow(NotFoundException::new);
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

	public void updateClientInfo(ClientRequest client, String id) {
		Client clientUpdate = clientRepository.findById(id).orElseThrow(NotFoundException::new);
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
	 * @throws NotFoundException
	 */
	public String saveClientInfo(ClientRequest request) {
		Client client = clientRepository.save(ClientConverter.toClient(request));
		return client.getId();
	}

	/**
	 * Method to delete one client by id.
	 * 
	 * @param id
	 */
	public void deleteClientById(String id) {
		if (!clientRepository.existsById(id)) {
			throw new NotFoundException();
		}
		clientRepository.deleteById(id);
	}
}
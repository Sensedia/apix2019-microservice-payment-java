package com.sensedia.payment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sensedia.payment.domain.Client;
import com.sensedia.payment.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public List<Client> retrieveAllClients(Client filter) {
		return clientRepository.findAll(Example.of(filter));
	}

	public Optional<Client> retrieveClientById(String id) {
		return clientRepository.findById(id);
	}

	public Client saveClientInfo(Client client) {
		return clientRepository.save(client);
	}

	public void deleteClientById(String id) {
		clientRepository.deleteById(id);
	}
}

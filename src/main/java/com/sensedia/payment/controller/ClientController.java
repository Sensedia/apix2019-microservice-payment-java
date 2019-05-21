package com.sensedia.payment.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sensedia.payment.domain.Client;
import com.sensedia.payment.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	/**
	 * Get all clients.
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/clients")
	public List<Client> retrieveAllClients(@RequestParam(name = "document", required = false) String document,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone) {

		Client filter = new Client(document, email, phone);

		return clientService.retrieveAllClients(filter);
	}

	/**
	 * Get one client by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/clients/{id}")
	public Client retrieveClientById(@PathVariable String id) {
		Optional<Client> client = clientService.retrieveClientById(id);

		return client.get();
	}

	/**
	 * Create a new client
	 * 
	 * @param client
	 * @return
	 */
	@PostMapping("/clients")
	public ResponseEntity<Object> createNewClient(@RequestBody Client client) {
		Client savedClient = clientService.saveClientInfo(client);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedClient.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Update client's info
	 * 
	 * @param client
	 * @param id
	 * @return
	 */
	@PutMapping("/clients/{id}")
	public ResponseEntity<Object> updateClient(@RequestBody Client updateClient, @PathVariable String id) {
		Optional<Client> clientOptional = clientService.retrieveClientById(id);

		if (!clientOptional.isPresent())
			return ResponseEntity.notFound().build();

		updateClient.setId(id);
		clientService.saveClientInfo(updateClient);

		return ResponseEntity.noContent().build();
	}

	/**
	 * Partial update of client by id
	 * 
	 * @param partialUpdate
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PatchMapping("/clients/{id}")
	public ResponseEntity<?> partialUpdateClient(@RequestBody Client partialUpdate, @PathVariable String id)
			throws Exception {

		Client client = clientService.retrieveClientById(id).orElseThrow(Exception::new);

		if (partialUpdate.getDocument() != null) {
			client.setDocument(partialUpdate.getDocument());
		}
		if (partialUpdate.getName() != null) {
			client.setName(partialUpdate.getName());
		}
		if (partialUpdate.getEmail() != null) {
			client.setEmail(partialUpdate.getEmail());
		}
		if (partialUpdate.getPhone() != null) {
			client.setPhone(partialUpdate.getPhone());
		}
		if (partialUpdate.getPayday() != null) {
			client.setPayday(partialUpdate.getPayday());
		}

		clientService.saveClientInfo(client);
		return ResponseEntity.ok("Partial update done sucessfully on client's id: " + id);
	}

	/**
	 * Delete a client by id
	 * 
	 * @param id
	 */
	@DeleteMapping("/clients/{id}")
	public void deleteStudent(@PathVariable String id) {
		clientService.deleteClientById(id);
	}
}
package com.sensedia.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;
import com.sensedia.payment.service.ClientService;
import com.sensedia.payment.validator.ClientValidator;

import javassist.NotFoundException;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	/**
	 * Get all clients. optional filters: document, email, phone
	 * @param document
	 * @param email
	 * @param phone
	 * @return
	 */
	@GetMapping("/clients")
	public ResponseEntity<List<ClientResponse>> retrieveAllClients(
			@RequestParam(name = "document", required = false) String document,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone) {

		List<ClientResponse> clientList = clientService.retrieveAllClients(document, email, phone);
		if (clientList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(clientList);
	}

	/**
	 * Get client by id
	 * @param id
	 * @return
	 */
	@GetMapping("/clients/{id}")
	public ResponseEntity<ClientResponse> retrieveClientById(@PathVariable String id) {

		ClientResponse client = clientService.retrieveClientById(id);
		return ResponseEntity.ok(client);
	}

	/**
	 * Create a new client
	 * @param client
	 * @param uriBuilder
	 * @return
	 */
	@PostMapping("/clients")
	public ResponseEntity<Void> createNewClient(@RequestBody ClientRequest client, UriComponentsBuilder uriBuilder) {

		ClientValidator.validate(client);
		String clientId = clientService.saveClientInfo(client);

		return ResponseEntity.status(HttpStatus.CREATED.value())
				.location(uriBuilder.path("/{id}").buildAndExpand(clientId).toUri()).build();
	}

	/**
	 * Update on client's information
	 * @param updateClient
	 * @param id
	 * @return
	 */
	@PutMapping("/clients/{id}")
	public ResponseEntity<Object> updateClient(@RequestBody ClientRequest updateClient, @PathVariable String id) {

		ClientValidator.validate(updateClient);
		clientService.updateClientInfo(updateClient, id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 *  Partial update on client's information
	 * @param partialUpdate
	 * @param id
	 * @return
	 */
	@PatchMapping("/clients/{id}")
	public ResponseEntity<Void> partialUpdateClient(@RequestBody ClientRequest partialUpdate, @PathVariable String id) {

		clientService.partialUpdateClientById(partialUpdate, id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * Delete client by id
	 * @param id
	 */
	@DeleteMapping("/clients/{id}")
	public void deleteClient(@PathVariable String id) {
		clientService.deleteClientById(id);
	}
}
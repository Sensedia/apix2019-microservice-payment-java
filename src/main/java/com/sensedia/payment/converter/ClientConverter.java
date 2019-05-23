package com.sensedia.payment.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.sensedia.payment.domain.Client;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;

public class ClientConverter {

	public static List<ClientResponse> toClientResponse(List<Client> clients) {
		return clients.stream().map(ClientConverter::toClientResponse).collect(Collectors.toList());
	}

	public static ClientResponse toClientResponse(Client client) {
		return new ClientResponse(client.getId(),
				client.getDocument(),
				client.getName(),
				client.getEmail(),
				client.getPhone(),
				client.getPayday());
	}
	
	public static Client toClient(ClientRequest client) {
		return new Client(client.getDocument(),
				client.getName(),
				client.getEmail(),
				client.getPhone(),
				client.getPayday());
	}
}
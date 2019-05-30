package com.sensedia.payment.converter;

import java.util.List;
import java.util.stream.Collectors;
import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.request.ClientRequest;
import com.sensedia.payment.response.ClientResponse;

public class ClientConverter {
  
  private ClientConverter () {
    
  }

  public static List<ClientResponse> toClientResponse(List<ClientEntity> clients) {
    return clients.stream().map(ClientConverter::toClientResponse).collect(Collectors.toList());
  }

  public static ClientResponse toClientResponse(ClientEntity client) {
    return ClientResponse.builder()
    		.id(client.getId())
    		.document(client.getDocument())
    		.name(client.getName())
    		.email(client.getEmail())
    		.phone(client.getPhone())
    		.expirationDay(client.getExpirationDay())
    		.build();
  }

  public static ClientEntity toClient(ClientRequest client) {
    return ClientEntity.builder()
    		.document(client.getDocument())
    		.name(client.getName())
    		.email(client.getEmail())
    		.phone(client.getPhone())
    		.expirationDay(client.getExpirationDay())
    		.password(client.getPassword())
    		.build();

  }

}

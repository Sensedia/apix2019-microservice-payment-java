package com.sensedia.payment.client.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sensedia.payment.client.response.RegistrationsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterClientService {
	
	private final RestTemplate restTemplate;
	
	@Value("${ms-register.registrations.endpoint}")
	private String registrationsEndpoint;
	
	public Integer getScore(String document) {
		String uri = UriComponentsBuilder.fromHttpUrl(registrationsEndpoint)
				.queryParam("document", document)
				.toUriString();
		
		try {
			RequestEntity<Void> request = RequestEntity
					.get(new URI(uri))
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.build();
			
			ResponseEntity<RegistrationsResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, RegistrationsResponse.class);
			
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody().getScore();
			} else {
				return 100;
			}
		} catch (Exception e) {
			log.error("Error calling register.", e);
			return 100;
		}
    }

}

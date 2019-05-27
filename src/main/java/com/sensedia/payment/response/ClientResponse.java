package com.sensedia.payment.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientResponse {

	private UUID id;
	private String document;
	private String name;
	private String email;
	private String phone;
	private Integer payday;

}

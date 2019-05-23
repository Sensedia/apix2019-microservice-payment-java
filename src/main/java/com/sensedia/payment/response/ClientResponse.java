package com.sensedia.payment.response;

public class ClientResponse {

	private String id;
	private String document;
	private String name;
	private String email;
	private String phone;
	private Integer payday;

	public ClientResponse() {
		// empty constructor
	}

	public ClientResponse(String document, String email, String phone) {
		this.document = document;
		this.email = email;
		this.phone = phone;
	}

	public ClientResponse(String id, String document, String name, String email, String phone, Integer payday) {

		this.id = id;
		this.document = document;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.payday = payday;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPayday() {
		return payday;
	}

	public void setPayday(Integer payday) {
		this.payday = payday;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

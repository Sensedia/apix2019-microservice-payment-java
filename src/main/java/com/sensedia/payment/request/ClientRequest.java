package com.sensedia.payment.request;

public class ClientRequest {
	private String document;
	private String name;
	private String email;
	private String phone;
	private Integer payday;

	public ClientRequest() {
		// empty constructor
	}

	public ClientRequest(String document, String email, String phone) {
		this.document = document;
		this.email = email;
		this.phone = phone;
	}

	public ClientRequest(String document, String name, String email, String phone, Integer payday) {

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
}

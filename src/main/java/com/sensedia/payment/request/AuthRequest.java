package com.sensedia.payment.request;

import lombok.Data;

@Data
public class AuthRequest {
  
  private String document;
  private String password;

}

package com.sensedia.payment.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {
  
  private String document;
  private String name;
  private String email;
  private String phone;
  private Integer payday;

}

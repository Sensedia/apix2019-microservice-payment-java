package com.sensedia.payment.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sensedia.payment.request.AuthRequest;
import com.sensedia.payment.service.AuthService;
import com.sensedia.payment.validator.AuthValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> authUser(@RequestBody AuthRequest request) {
    AuthValidator.validate(request);
    authService.validateUser(request);
    return ResponseEntity.noContent().build();
  }

}

package com.example.demo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint for user registration.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        // Call the service to register the user and get a token
        AuthenticationResponse response = authService.register(request);
        // Return the response with a 200 OK status
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for user authentication (login).
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        // Call the service to authenticate the user and get a token
        AuthenticationResponse response = authService.authenticate(request);
        // Return the response with a 200 OK status
        return ResponseEntity.ok(response);
    }
}
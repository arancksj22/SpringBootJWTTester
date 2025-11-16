package com.example.demo.auth;

import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Constructor injection
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        // Create new user object
        var user = new User(
                request.getEmail(),     // 1. String (email)
                Role.USER,              // 2. Role
                request.getFirstName(), // 3. String (firstName)
                request.getLastName(),  // 4. String (lastName)
                passwordEncoder.encode(request.getPassword()) // 5. String (password)
        );

        // Save the user to the database
        userRepository.save(user);

        // Generate a JWT for the new user
        var jwtToken = jwtService.generateToken(user);

        // Return the response containing the token
        return new AuthenticationResponse(jwtToken);
    }

    /**
     * Authenticates an existing user.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // This will try to authenticate the user.
        // If credentials are bad, it throws an AuthenticationException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),    // <-- Use getter
                        request.getPassword()  // <-- Use getter
                )
        );

        // If authentication is successful, find the user
        var user = userRepository.findByEmail(request.getEmail()) // <-- Use getter
                .orElseThrow(); // Should not happen if auth succeeded

        // Generate a JWT for the user
        var jwtToken = jwtService.generateToken(user);

        // Return the response containing the token
        return new AuthenticationResponse(jwtToken);
    }
}
package com.polystore.polystorebackend.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = service.register(request);
            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            AuthenticationResponse auth = service.authenticate(request);
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello!";
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationResponse authenticationResponse = service.refreshToken(request, response);
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/logout")
    public ResponseEntity logout(Principal principal) {
        service.logoutUser(principal.getName());
        return ResponseEntity.ok(null);
    }
}

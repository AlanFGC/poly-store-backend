package com.polystore.polystorebackend.api.auth;


import com.polystore.polystorebackend.model.Role;
import com.polystore.polystorebackend.model.Token;
import com.polystore.polystorebackend.model.TokenType;
import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.repository.TokenRepository;
import com.polystore.polystorebackend.repository.UserRepository;
import com.polystore.polystorebackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public RegisterResponse register(RegisterRequest request) throws SQLException {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                // TODO add more roles.
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        this.saveUserToken(user, jwtToken);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(request.getUsername());
        registerResponse.setRole(Role.USER.toString());
        registerResponse.setToken(jwtToken);
        return registerResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getUsername()).orElse(new User());
        System.out.println("USERNAME: " + user.getUsername() + user.getPassword() + user.getEmail());
        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);


        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().toString())
                .token(jwtToken)
                .error("")
                .email(user.getEmail())
                .build();

        return authenticationResponse;

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        System.out.println("USER ID:" + user.getId());
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}


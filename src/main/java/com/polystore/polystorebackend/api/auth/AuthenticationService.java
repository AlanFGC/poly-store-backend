package com.polystore.polystorebackend.api.auth;


import com.polystore.polystorebackend.model.Role;
import com.polystore.polystorebackend.model.Token;
import com.polystore.polystorebackend.model.TokenType;
import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.repository.TokenRepository;
import com.polystore.polystorebackend.repository.UserRepository;
import com.polystore.polystorebackend.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

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
        String requestedRole = request.getRole().strip().toLowerCase();
        Role role = null;

        if ( requestedRole.contains("creator")){
            role = Role.CREATOR;
        } else if (requestedRole.contains("admin") ){
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        this.saveUserToken(user, jwtToken);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(request.getUsername());
        registerResponse.setRole(request.getRole().toString());
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

        revokeUser(user);
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


    public void logoutUser(String username){
        User user = repository.findByUsername(username).orElseThrow();
        revokeUser(user);
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



    private void  revokeUser(User user) {

        List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (tokens.isEmpty()) return;

        for (Token token: tokens){
            token.setExpired(true);
            token.setRevoked(true);
        }
        tokenRepository.saveAll(tokens);
    }


    // grabs the token from the header and returns a new token
    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response){



        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Bad Request");
        }

        String refreshToken = header.substring(7);

        String username = jwtService.extractUsername(refreshToken);
        if (username == null) throw new IllegalArgumentException("Invalid token");
        User user = repository.findByUsername(username).orElseThrow();


        // if the time left is > one hour
        // return same thing!

        if (jwtService.getTimeBeforeExpiration(refreshToken) < (25 * 10000)){
            //System.out.println("TIME IS LESS THAN THIS, we can continue browsing");
            return AuthenticationResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().toString())
                    .token(refreshToken)
                    .error("")
                    .email(user.getEmail())
                    .build();
        }



        if (jwtService.isTokenValid(refreshToken, user)) {
            System.out.println("REVOKING!!");
            this.revokeUser(user);
            String newToken = jwtService.generateToken(user);
            saveUserToken(user, newToken);
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().toString())
                    .token(newToken)
                    .error("")
                    .email(user.getEmail())
                    .build();
            return authenticationResponse;
        }

        throw new IllegalArgumentException("Token is not valid");
    }





}


package com.portfolio.cms.controller;

import com.portfolio.cms.dto.LoginRequest;
import com.portfolio.cms.dto.RefreshTokenRequest;
import com.portfolio.cms.dto.TokenResponse;
import com.portfolio.cms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshRequest) {
        TokenResponse tokenResponse = authService.refreshAccessToken(refreshRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest refreshRequest) {
        authService.logout(refreshRequest.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}

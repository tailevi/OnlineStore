package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.User;
import com.example.OnlineStore.payload.request.AuthenticationRequest;
import com.example.OnlineStore.payload.request.RegisterRequest;
import com.example.OnlineStore.payload.response.AuthenticationResponse;
import com.example.OnlineStore.reposetories.UserRepo;
import com.example.OnlineStore.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserRepo userRepo;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request){
       return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.AuthDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.LoginResponseDTO;
import com.lucas.bank.mini_bank_api.domain.entity.customer.CustomerDetails;
import com.lucas.bank.mini_bank_api.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO authDTO){


        var authToken =
                new UsernamePasswordAuthenticationToken(authDTO.cpf(), authDTO.password());

        var authentication = this.authenticationManager.authenticate(authToken);

        var principal = (CustomerDetails) authentication.getPrincipal();
        var jwt = tokenService.generateToken(principal);

        return ResponseEntity.ok(new LoginResponseDTO(jwt));

    }

}

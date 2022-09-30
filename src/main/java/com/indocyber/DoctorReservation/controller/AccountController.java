package com.indocyber.DoctorReservation.controller;

import com.indocyber.DoctorReservation.dto.account.InsertAccountDTO;
import com.indocyber.DoctorReservation.dto.account.RequestTokenDTO;
import com.indocyber.DoctorReservation.dto.account.ResponseTokenDTO;
import com.indocyber.DoctorReservation.service.AccountService;
import com.indocyber.DoctorReservation.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @PostMapping("/authenticate")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto){

        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            System.out.println("Is authenticate : " + authentication.isAuthenticated());
            System.out.println("Principal : " + authentication.getPrincipal());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not authenticate", e);
        }
        String role = accountService.getAccountRole(dto.getUsername());
        String token = jwtToken.generateToken(
                dto.getSubject(),
                dto.getUsername(),
                dto.getSecretKey(),
                role,
                dto.getAudience()
        );

        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(dto.getUsername(), role, token);

        return responseTokenDTO;
    }

    //daftar account
    @PostMapping("/addAccount")
    public ResponseEntity<String> addAccount(@RequestBody InsertAccountDTO dto){
        accountService.addAccount(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Account berhasil ditambahkan");
    }
}

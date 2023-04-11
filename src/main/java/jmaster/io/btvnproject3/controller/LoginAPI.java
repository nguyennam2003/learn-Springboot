package jmaster.io.btvnproject3.controller;

import jmaster.io.btvnproject3.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LoginAPI {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @GetMapping("/me")
//    @PreAuthorize("hasAuthority('USER_ADMIN')")
//    @CrossOrigin("http://localhost")
    public Principal me(Principal p) {
        return p;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenService.createToken(username);
    }
}

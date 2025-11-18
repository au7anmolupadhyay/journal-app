package net.au7.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.UserDetailServiceImpl;
import net.au7.journalApp.service.UserService;
import net.au7.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User newUser){
        try{
            userService.saveNewUser(newUser);
            URI location = URI.create("/public/" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User newUser){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(newUser.getUsername());
            jwtUti
        }catch(Exception e){
            log.error("Exception occurred while creating logging in and creating token ", e);
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }
}

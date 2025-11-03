package net.au7.journalApp.controller;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        try{
            userService.saveNewUser(newUser);
            URI location = URI.create("/public/" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

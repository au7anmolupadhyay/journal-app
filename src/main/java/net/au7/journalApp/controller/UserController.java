package net.au7.journalApp.controller;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> allUsers = userService.getAllEntries();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        try{
            userService.saveEntry(newUser);
            URI location = URI.create("/users/" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        try{

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

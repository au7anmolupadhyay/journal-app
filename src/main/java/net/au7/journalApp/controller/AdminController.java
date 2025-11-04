package net.au7.journalApp.controller;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>>  getAllUsers(){
        List<User> allUsers = userService.getAllEntries();
        if(!allUsers.isEmpty()){
            return ResponseEntity.ok(allUsers);
        }else return ResponseEntity.notFound().build();
    }

    @PostMapping("create-admin-user")
    public ResponseEntity<User> createAdminUser(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}

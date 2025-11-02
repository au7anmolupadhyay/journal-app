package net.au7.journalApp.controller;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
            userService.saveNewUser(newUser);
            URI location = URI.create("/users/" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        try{
            List<User> allUsers = userService.getAllEntries();

            Optional<User> foundUser = allUsers.stream().
                    filter(user -> user.getId().equals(id))
                    .findAny();

            if(foundUser.isPresent()) return ResponseEntity.ok(foundUser.get());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        try{
            User userInDb = userService.findByUsername(userName);
            if(userInDb != null){
                userInDb.setUsername(user.getUsername());
                userInDb.setPassword(user.getPassword());
                userService.saveEntry(userInDb);
                return ResponseEntity.ok(userInDb);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user){
        try{
            User userToBeDeleted = userService.findByUsername(user.getUsername());
            if(userToBeDeleted != null){
                userService.deleteByUsername(userToBeDeleted.getUsername());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

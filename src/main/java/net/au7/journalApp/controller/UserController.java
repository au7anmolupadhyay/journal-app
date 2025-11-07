package net.au7.journalApp.controller;

import net.au7.journalApp.api.response.QuotesResponse;
import net.au7.journalApp.api.response.WeatherResponse;
import net.au7.journalApp.entity.User;
import net.au7.journalApp.service.QuotesService;
import net.au7.journalApp.service.UserService;
import net.au7.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private QuotesService quotesService;

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

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userInDb = userService.findByUsername(userName);
                userInDb.setUsername(user.getUsername());
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.saveNewUser(userInDb);
                return ResponseEntity.ok(userInDb);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userToBeDeleted = userService.findByUsername(username);
            if(userToBeDeleted != null){
                userService.deleteByUsername(userToBeDeleted.getUsername());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = "";
        if(weatherResponse != null){
            greetings = "Temperature is " + weatherResponse.getCurrent().getTemperature() + " and it feels like :"
            + weatherResponse.getCurrent().getFeelslike();
        }

        Map<String, String> response = new HashMap<>();
        response.put("user", authentication.getName());
        response.put("message", greetings);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/quotes")
    public ResponseEntity<Map<String, String>> getQuotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        QuotesResponse[] quotes = quotesService.getQuotes();

        Map<String, String> response = new HashMap<>();

        if (quotes != null && quotes.length > 0) {
            QuotesResponse firstQuote = quotes[0];
            String message = "\"" + firstQuote.getQuote() + "\" — " + firstQuote.getAuthor();

            response.put("author", authentication.getName());
            response.put("message", message);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("author", authentication.getName());
        response.put("message", "No quotes available at the moment.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package net.au7.journalApp.service;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User userEntry){
        userRepository.save(userEntry);
    }

    public void saveNewUser(User userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(List.of("USER"));
        userRepository.save(userEntry);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAllEntries(){
        return userRepository.findAll();
    }

    public Optional<User> findEntryById(ObjectId id){
        return userRepository.findById(String.valueOf(id));
    }

    public boolean deleteEntryById(ObjectId id){
        userRepository.deleteById(String.valueOf(id));
        return false;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }


}

package net.au7.journalApp.service;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User userEntry){
        userRepository.save(userEntry);
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

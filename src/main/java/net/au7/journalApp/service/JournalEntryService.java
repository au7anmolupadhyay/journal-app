package net.au7.journalApp.service;

import net.au7.journalApp.entity.JournalEntry;
import net.au7.journalApp.entity.User;
import net.au7.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String username){
        boolean found = false;
        try{
            User user = userService.findByUsername(username);
            found = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(found){
                userService.saveUser(user);
                journalEntryRepository.deleteById(String.valueOf(id));
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting an entry: ", e);
        }
        return found;
    }
}

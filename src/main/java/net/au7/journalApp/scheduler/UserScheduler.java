package net.au7.journalApp.scheduler;

import net.au7.journalApp.repository.UserRepositoryImpl;
import net.au7.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    public void fetchUserAndSendMail(){

    }
}

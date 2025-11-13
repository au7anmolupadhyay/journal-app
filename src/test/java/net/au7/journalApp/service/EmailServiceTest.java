package net.au7.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest(){
        String sub = "Good evening love, automated sending you mail with Spring Boot code";
        String text = "hima gashi oh my ae sun, i am your gwan sik, can't wait to resume my oxford year with you today!";
        emailService.sendEmail("dograharshi236@gmail.com", sub, text);
    }
}

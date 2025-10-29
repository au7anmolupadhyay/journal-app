package net.au7.journalApp.repository;

import net.au7.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername (String userName);
    void deleteByUsername (String userName);
}
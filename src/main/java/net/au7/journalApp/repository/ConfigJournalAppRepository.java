package net.au7.journalApp.repository;

import net.au7.journalApp.entity.ConfigJournalAppEntity;
import net.au7.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
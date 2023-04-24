package com.kromanenko.ssauserservice.reposotiry;

import com.kromanenko.ssauserservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

package com.h1totsu.ssauserservice.reposotiry;

import java.util.UUID;

import com.h1totsu.ssauserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}

package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsernameAndPassword(String username, String password);  //Charles in the video uses optional instead of Account, come back to this if need be (~32 minutes)
    Account findByUsername(String username);
}

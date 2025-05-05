package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired      //should I remove this duplicate, or the other, or keep?
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account registerAccount(Account account) {
        //error handling for:
        //username cannot be blank, password must be at least 4 characters long, and an account with that username cannot already exist.
        //if successful, 200 status. If duplicate username, 409. If any other failure, 400
        String username = account.getUsername();
        String password = account.getPassword();
        if(username.length() == 0 || password.length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid musername or password text."); 
        }
        if(accountRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate user."); 
        }
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Account returnedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(returnedAccount == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account does not exist"); 
        } else {
            return returnedAccount;
        }
        
    }

}

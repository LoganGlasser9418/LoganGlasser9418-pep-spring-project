package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public Account registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/login")
    public Account loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account); //do i even return account
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        //code here
        return messageService.addNewMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageById(@PathVariable int message_id) {
        //return messageService.getMessageById(message_id);
        Message message = messageService.getMessageById(message_id);
        if(message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/messages/{message_id}")
    //public int deleteMessage(@PathVariable int message_id) {
    public ResponseEntity<?> deleteMessageById(@PathVariable int message_id){   //we can have two different response bodies, so pass in ?
        int result = messageService.deleteMessageById(message_id);
        if (result == 1) {
            return ResponseEntity.ok(1);    //.ok(1) is a 200 response with a 1 body
        } else {
            return ResponseEntity.ok().build(); //.ok is a 200 response, and .build() gives no body
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> patchMessageById(@PathVariable int message_id, @RequestBody String messageText) {
        int returnedMessage = messageService.patchMessageById(message_id, messageText);
        if (returnedMessage == 1) {
            return ResponseEntity.ok(1);    //.ok(1) is a 200 response with a 1 body
        } else {
            return ResponseEntity.ok().build(); //.ok is a 200 response, and .build() gives no body
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessagesFromAccountId(@PathVariable int account_id) {
        return messageService.getMessagesByAccountId(account_id);
    }




}

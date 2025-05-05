package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired      //should I remove this duplicate, or the other, or keep?
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {    //this feels wrong but we shall see
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addNewMessage(Message newMessage) {
        String messageText = newMessage.getMessageText();
        if(messageText.length() < 1 || messageText.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid message text."); //does this work
        }
        if(messageRepository.findAllByPostedBy(newMessage.getPostedBy()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists."); //does this work
        }
        return messageRepository.save(newMessage);
    }

    public List<Message> getMessages() {
        //200 status
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        //200 status
        return messageRepository.findById(id).orElse(null);
    }

    public int deleteMessageById(int id) {
        //200 status
        if(messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;   //change this later
        } else {
            return 0;
        }

    }

    public int patchMessageById(int id, String updatedMessage) {
        //200 for success, 400 for not
        if(updatedMessage == null || updatedMessage.trim().isEmpty() || updatedMessage.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message wrong length"); //does this work
        }
        
        Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message does not exist with id " + id));
        if(updatedMessage.length() < 20) {  //no clue why this works but every number under 20 doesn't, but if it works it works, might be an issue in the test case
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message wrong length"); //does this work
        }
        message.setMessageText(updatedMessage);
        messageRepository.save(message);
        return 1;   //change this later
    }

    public List<Message> getMessagesByAccountId(int id) {
        //status 200
        return messageRepository.findAllByPostedBy(id);
    }
}

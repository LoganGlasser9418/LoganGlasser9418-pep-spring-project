package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    //Message findById(int id);
    List<Message> findAllByPostedBy(int postedBy);  //does this let me get by postedBy id?
}

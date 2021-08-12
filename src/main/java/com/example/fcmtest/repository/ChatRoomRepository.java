package com.example.fcmtest.repository;

import com.example.fcmtest.domain.ChatRoom;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class ChatRoomRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(ChatRoom chatRoom) {
        em.persist(chatRoom);

        return chatRoom.getId();
    }

    public ChatRoom findById(Long id) {
        if(id == null) return null;

        return em.find(ChatRoom.class, id);
    }

    @Transactional
    public Long remove(ChatRoom chatRoom){
        em.remove(chatRoom);

        return chatRoom.getId();
    }

    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class).getResultList();
    }
}

package com.example.fcmtest.repository;

import com.example.fcmtest.domain.Chat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Chat chat){
        em.persist(chat);

        return chat.getId();
    }

    public Chat findById(Long id){
        return em.find(Chat.class, id);
    }

    @Transactional
    public Long remove(Chat chat){
        em.remove(chat);

        return chat.getId();
    }
}

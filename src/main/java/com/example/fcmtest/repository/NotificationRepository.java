package com.example.fcmtest.repository;

import com.example.fcmtest.domain.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@Repository
public class NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Notification notification){
        em.persist(notification);

        return notification.getId();
    }

    public Notification findById(Long id){
        return em.find(Notification.class, id);
    }

    @Transactional
    public Long remove(Notification notification){
        em.remove(notification);
        return notification.getId();
    }
}

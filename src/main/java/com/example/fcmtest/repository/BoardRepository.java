package com.example.fcmtest.repository;

import com.example.fcmtest.domain.Board;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Board board){
        em.persist(board);

        return board.getId();
    }

    public Board findById(Long id){
        if(id == null) return null;
        return em.find(Board.class, id);
    }

    @Transactional
    public Long remove(Board board){
        em.remove(board);

        return board.getId();
    }
}

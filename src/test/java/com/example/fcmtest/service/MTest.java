//package com.example.fcmtest.service;
//
//import com.example.fcmtest.domain.Member;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//
//@SpringBootTest
//public class MTest {
//
////    @Autowired
////    EntityManager em;
//
////    @Test
////    @DisplayName("유니크테스트1")
////    void uniqueTest(){
////        Member member = new Member();
////        Member other = new Member();
////
////        member.setNickname("origin");
////        other.setNickname("origin");
////
////        em.persist(member);
////        em.persist(other);
////
////        System.out.println("----------------flush---------------");
////
////        em.flush();
////        em.clear();
////    }
//
//    @Autowired
//    EntityManagerFactory emf;
//
//    @Test
//    @DisplayName("유니크테스트2")
//    void uniqueTest2(){
//
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        tx.begin();
//
//        try {
//            Member member = new Member();
//            member.setNickname("origin");
//            em.persist(member);
//
//            System.out.println("----------------flush---------------");
//
//            em.flush();
//            em.clear();
//
//            System.out.println("----------------other---------------");
//
//            Member other = new Member();
//            other.setNickname("origin");
//            em.persist(other);
//
//            tx.commit();
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Unique 제약조건 걸림");
//            tx.rollback();
//        }
//    }
//}

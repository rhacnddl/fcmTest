package com.example.fcmtest.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    private String title;
}

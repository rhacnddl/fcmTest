package com.example.fcmtest.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //도착지
    private Long targetId;
    //보낸사람
    private String nickname;
    //받는사람 토큰
    private String token;
    //타입
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    //시간
    private LocalDateTime time;
}

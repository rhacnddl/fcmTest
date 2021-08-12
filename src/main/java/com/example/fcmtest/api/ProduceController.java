package com.example.fcmtest.api;

import com.example.fcmtest.domain.Chat;
import com.example.fcmtest.domain.Comment;
import com.example.fcmtest.domain.Notification;
import com.example.fcmtest.domain.NotificationType;
import com.example.fcmtest.service.NotificationService;
import com.example.fcmtest.storage.TokenStorage;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProduceController {

    private final NotificationService notificationService;
    private final static TokenStorage storage = TokenStorage.getInstance();
    private final RabbitTemplate template;

    private final String EXCHANGE_NAME = "ex1";
    private final String CHAT_ROUTING_KEY = "chat.";
    private final String COMMENT_ROUTING_KEY = "comment.";

    @PostMapping("/chat")
    public ResponseEntity<String> pubChat(@RequestBody Chat chat){
        System.out.println("chat = " + chat);
        Notification notification = chatToNotification(chat);
        System.out.println(CHAT_ROUTING_KEY + notification.getTargetId());
        notificationService.saveNotification(notification);
        template.convertAndSend(EXCHANGE_NAME, CHAT_ROUTING_KEY + notification.getTargetId().toString(), notification);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<String> pubComment(@RequestBody Comment comment){

        Notification notification = commentToNotification(comment);
        System.out.println(CHAT_ROUTING_KEY + notification.getTargetId());
        notificationService.saveNotification(notification);
        template.convertAndSend(EXCHANGE_NAME, COMMENT_ROUTING_KEY + notification.getTargetId().toString(), notification);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    private Notification chatToNotification(Chat chat){
        return Notification.builder()
                //.targetId(chat.getChatRoom().getId())
                .targetId(chat.getChatRoomId())
                .time(LocalDateTime.now())
                .nickname(chat.getNickname())
                .token(storage.getToken(chat.getNickname()))
                .type(NotificationType.CHAT)
                .build();
    }

    private Notification commentToNotification(Comment comment){
        return Notification.builder()
                .targetId(comment.getBoardId())
                .time(LocalDateTime.now())
                .nickname(comment.getNickname())
//                .nickname(comment.getMember().getNickname())
                .token(storage.getToken(comment.getNickname()))
                .type(NotificationType.COMMENT)
                .build();
    }
}

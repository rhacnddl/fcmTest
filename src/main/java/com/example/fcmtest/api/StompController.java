package com.example.fcmtest.api;

import com.example.fcmtest.domain.Chat;
import com.example.fcmtest.domain.Notification;
import com.example.fcmtest.domain.NotificationType;
import com.example.fcmtest.repository.ChatRepository;
import com.example.fcmtest.service.NotificationService;
import com.example.fcmtest.storage.TokenStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompController {

    private final static TokenStorage storage = TokenStorage.getInstance();

    private final ChatRepository chatRepository;
    private final NotificationService notificationService;

    private final SimpMessagingTemplate template;
    private final RabbitTemplate rabbitTemplate;

    private final String EXCHANGE_NAME = "ex1";
    private final String CHAT_ROUTING_KEY = "chat.";

    @MessageMapping("/chat/enter")
    public void enter(Chat chat){
        chat.setMessage("님이 입장하셨습니다.");
        System.out.println("chat = " + chat);

        template.convertAndSend("/sub/room/" + chat.getChatRoomId(), chat);
    }
    @MessageMapping("/chat/exit")
    public void exit(Chat chat){

        chat.setMessage("님이 퇴장하셨습니다.");
        template.convertAndSend("/sub/room/" + chat.getChatRoomId(), chat);
    }
    @MessageMapping("/chat/message")
    public void message(Chat chat){

        /* Persist Chat */
        chatRepository.save(chat);

        /* Send to other subcribers */
        template.convertAndSend("/sub/room/" + chat.getChatRoomId(), chat);

        /* push notification to RabbitMQ */
        Notification notification = chatToNotification(chat);
        notificationService.saveNotification(notification);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, CHAT_ROUTING_KEY + notification.getTargetId(), notification);
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
}

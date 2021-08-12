package com.example.fcmtest.service;

import com.example.fcmtest.domain.Board;
import com.example.fcmtest.domain.ChatRoom;
import com.example.fcmtest.domain.Notification;
import com.example.fcmtest.domain.NotificationType;
import com.example.fcmtest.exception.NoReceiverException;
import com.example.fcmtest.repository.BoardRepository;
import com.example.fcmtest.repository.ChatRepository;
import com.example.fcmtest.repository.ChatRoomRepository;
import com.example.fcmtest.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final BoardRepository boardRepository;

    private final String CHAT_QUEUE_NAME = "chat-queue";
    private final String COMMENT_QUEUE_NAME = "comment-queue";

    @RabbitListener(queues = {CHAT_QUEUE_NAME, COMMENT_QUEUE_NAME})
    public void chatSend(final Notification notification) {

        /* 등록된 토큰이 없거나, 토큰이 비어있을 경우 Exception */
        try{
            if(notification.getToken() == null)
                throw new NoReceiverException("알림의 대상이 존재하지 않습니다.");
        } catch (NoReceiverException e){
            e.printStackTrace();
            return;
        }

        /* 알림 컨텐츠 제작 DTO로 보낼 것 */
        WebpushNotification webpushNotification = null;
        if(notification.getType() == NotificationType.CHAT){
            ChatRoom chatRoom = chatRoomRepository.findById(notification.getTargetId());

            webpushNotification = new WebpushNotification("2", notification.getNickname() + "님이 메세지를 보냈습니다.");
        }
        else{
            Board board = boardRepository.findById(notification.getTargetId());

            webpushNotification = new WebpushNotification(notification.getToken(), notification.getNickname() + "님이 댓글을 작성했습니다.");
        }

        /* 푸시 알림 준비 */
        Message message = Message.builder()
                .setToken(notification.getToken())
                .setWebpushConfig(WebpushConfig.builder()
                            .putHeader("TTL", "300")
                            .setNotification(new WebpushNotification(notification.getToken(), notification.getNickname() + "님이 댓글을 작성했습니다."))
                            .build())
                .build();

        /* 전송 */
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        log.info("<- Received : " + response);
    }

    public void saveNotification(Notification notification){

        notificationRepository.save(notification);
    }
}

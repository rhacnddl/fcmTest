package com.example.fcmtest.api;

import com.example.fcmtest.domain.ChatRoom;
import com.example.fcmtest.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ChatRoomController {

    private final ChatRoomRepository repository;

    @GetMapping(value = "/rooms")
    public ResponseEntity<List<ChatRoom>> getRooms(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/room/{id}")
    public ResponseEntity<ChatRoom> getRoom(@PathVariable("id") Long chatRoomId){
        return new ResponseEntity<>(repository.findById(chatRoomId), HttpStatus.OK);
    }

    @PostMapping(value = "/room")
    public ResponseEntity<Long> insertRoom(@RequestBody ChatRoom chatRoom){

        Long chatRoomId = repository.save(chatRoom);

        return new ResponseEntity<>(chatRoomId, HttpStatus.OK);
    }
}

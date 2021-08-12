package com.example.fcmtest.api;

import com.example.fcmtest.domain.Member;
import com.example.fcmtest.repository.MemberRepository;
import com.example.fcmtest.storage.TokenStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private static final TokenStorage storage = TokenStorage.getInstance();
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<String> loginPost(@RequestBody LoginDTO loginDTO){

        String nickname = loginDTO.getNickname();
        String token = loginDTO.getToken();

        Member member = new Member();
        member.setNickname(loginDTO.getNickname());
        member.setPassword("12345");

        Long memberId = memberRepository.save(member);

        System.out.println("nickname = " + nickname);
        System.out.println("token = " + token);

        if(nickname == null || token == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        storage.setToken(nickname, token);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LoginDTO loginDTO){

        String nickname = loginDTO.getNickname();

        if(storage.getToken(nickname) == null) {
            return new ResponseEntity<>("현재 로그인 한 유저가 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        log.info("nickname : " + nickname + " 님이 로그아웃 했습니다.");
        storage.removeToken(nickname);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Data
    private static class LoginDTO{
        private String nickname;
        private String token;
    }
}

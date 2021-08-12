package com.example.fcmtest.storage;

import java.util.HashMap;
import java.util.Map;

public class TokenStorage {

    private static TokenStorage instance;
    private Map<String, String> tokenMap;

    private TokenStorage() {
        System.out.println("## TokenStorage created ###");
        tokenMap = new HashMap<>();
    }

    public static synchronized TokenStorage getInstance(){
        if(instance == null)
            instance = new TokenStorage();

        return instance;
    }

    public String getToken(String nickname){
        return tokenMap.getOrDefault(nickname, null);
    }

    public void setToken(String nickname, String token){
        if(tokenMap.containsKey(nickname)) return;

        tokenMap.put(nickname, token);
    }

    public void removeToken(String nickname){
        if(!tokenMap.containsKey(nickname)) return;

        tokenMap.remove(nickname);
    }
}

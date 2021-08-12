package com.example.fcmtest.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {

    public FirebaseInitializer() throws Exception{

        InputStream serviceAccount =
                new ClassPathResource("ichatu-d9085-firebase-adminsdk-l2q4i-a1a2f75d6a.json").getInputStream();

        if(!FirebaseApp.getApps().isEmpty()) return;

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

    }
}

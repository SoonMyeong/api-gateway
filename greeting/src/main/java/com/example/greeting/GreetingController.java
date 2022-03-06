package com.example.greeting;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    @GetMapping("/api/greeting")
    public ResponseEntity<String> greeting(HttpServletRequest request) throws InterruptedException {

//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String token = null;
//
//        if(header !=null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//        }

//        return ResponseEntity.ok("Hello");

        Thread.sleep(3000);

        return ResponseEntity.badRequest().body("error");
    }
}

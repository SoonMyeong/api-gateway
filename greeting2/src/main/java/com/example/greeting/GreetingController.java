package com.example.greeting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    @GetMapping("/api/greeting")
    public ResponseEntity<String> greeting(HttpServletRequest request) {

//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String token = null;
//        String username = null;
//
//        if(header !=null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//        }
//
//        username = jwtBuildUtil.parseClaims(token).getBody().getSubject();

        return ResponseEntity.ok("Hello");
    }
}

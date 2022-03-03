package com.example.greeting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    @GetMapping("/api/greeting")
    public ResponseEntity<String> greeting(HttpServletRequest request) {


        /*TODO 아... 생각해보니 Spring Cloud token relay 기능을 활용하지 않으면 마이크로 서비스들 모두 JWTUtil 클래스를 가지고 있어야한다.. 이는 매우 불편할테니 token relay 를 구성하자*/
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

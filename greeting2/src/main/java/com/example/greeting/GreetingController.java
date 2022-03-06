package com.example.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    private final JwtBuildUtil jwtBuildUtil;

    @Autowired
    public GreetingController(JwtBuildUtil jwtBuildUtil)
    {
        this.jwtBuildUtil = jwtBuildUtil;
    }

    @GetMapping("/api/greeting")
    public ResponseEntity<String> greeting(HttpServletRequest request) {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;

        if(header !=null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        username = jwtBuildUtil.parseClaims(token).getBody().getSubject();

        System.out.println("username = " + username);

        return ResponseEntity.ok("Hello");
    }
}

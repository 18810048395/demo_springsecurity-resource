package com.example.springsecurityresource.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MemberController {
    @GetMapping("/getMember")
    public String getMember(HttpServletRequest request) throws Exception {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header,"Bearer ");
        Claims body = Jwts.parser().setSigningKey("yh-secret".getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        return body.toString();
    }

}
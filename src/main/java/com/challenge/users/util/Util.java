package com.challenge.users.util;

import com.challenge.users.payloads.responses.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Util {

    public static String createJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 18000000))
                .signWith(SignatureAlgorithm.HS512, "usersecret")
                .compact();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JwtResponse asObjectToken(final String str) {
        try {
            return new ObjectMapper().readValue(str, JwtResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

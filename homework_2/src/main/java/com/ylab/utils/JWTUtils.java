package com.ylab.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JWTUtils {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 864_000_000; // 10 дней

//    public static String generateToken(String username) {
//        return JWT
//    }
//
//    public static boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static String getUsernameFromToken(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }
}
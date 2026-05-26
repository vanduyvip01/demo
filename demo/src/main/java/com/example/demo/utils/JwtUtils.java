package com.example.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {

    // SECRET KEY PHẢI >= 32 ký tự cho HS256
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey123456";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 1 giờ
    private static final long EXPIRATION = 1000 * 60 * 60;

    /**
     * CREATE TOKEN
     */
    public static String generateToken(String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * VALIDATE TOKEN
     */
    public static boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT unsupported");
        } catch (MalformedJwtException e) {
            System.out.println("JWT malformed");
        } catch (SecurityException e) {
            System.out.println("Invalid signature");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT empty");
        }
        return false;
    }

    /**
     * GET CLAIMS
     */
    public static Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * GET USERNAME
     */
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}

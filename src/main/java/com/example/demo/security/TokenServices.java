package com.example.demo.security;


import com.example.demo.users.data.users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenServices {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.secret.reset}")
    private String jwtSecretReset;

    public String generateTokenUser(users user) {
        Date date =  date = Date.from(Instant.now().plus(365, ChronoUnit.DAYS));

        String jws = Jwts.builder().
                setSubject(user.getEmail()).
                claim("role", user.getUserRole()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return jws;
    }

    public String generateTokenUserReset(users user) {
        Date date =  date = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

        String jws = Jwts.builder().
                setSubject(user.getEmail()).
                claim("role", user.getUserRole()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return jws;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}

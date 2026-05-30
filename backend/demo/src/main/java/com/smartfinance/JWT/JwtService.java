package com.smartfinance.JWT;

import com.smartfinance.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {

    private final int ONE_HOUR_IN_MS = 1000 * 60 * 60;

    private final String SECRET = "SecretKeySecretKeySecretKeySecretKey";

    private String CLAIM_NAME = "role";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(User user){
        String claimNameValue = user.getRole().name();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(CLAIM_NAME, claimNameValue)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR_IN_MS))
                .signWith(key)
                .compact();
    }

    public String extractUserEmail(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }catch (Exception e)
        {
            throw new RuntimeException("Invalid or expired token");
        }

    }


    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        }catch (Exception e){
            return false;
        }
    }

}

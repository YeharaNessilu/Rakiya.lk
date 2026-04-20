package lk.ijse.gdse.springboot.back_end.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role","ROLE_"+ role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())
                        , SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 🔹 Extract username
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//    public String extractRole(String token) {
//        return extractAllClaims(token).get("role", String.class);
//    }
//    public boolean validateToken(String token) {
//        try {
//            extractAllClaims(token); // If parsing fails -> invalid
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}

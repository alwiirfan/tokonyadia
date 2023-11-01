package com.enima.tokonyadia.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    // fungsi untuk secret dan expiration jwt token

    @Value("secret")
    private String jwtSecret;

    @Value("86400000")
    private Long jwtExpiration;

    // mengambil email dari token
    public String getEmailByToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
    }

    // membuat token
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
            return true;
        }catch (MalformedJwtException e){ // apakah tokennya valid
            log.error("Invalid JWT token {}", e.getMessage());
        }catch (ExpiredJwtException e){ // apakah tokennya sudah expirate
            log.error("JWT token is expired {}", e.getMessage());
        }catch (UnsupportedJwtException e){ // apakah tokennya masih support
            log.error("JWT token is unsupported {}", e.getMessage());
        }catch (IllegalArgumentException e){ // apakah tokennya tidak kosong
            log.error("JWT claims string is empty {}", e.getMessage());
        }
        return false;
    }
}

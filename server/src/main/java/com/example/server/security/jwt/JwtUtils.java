package com.example.server.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.example.server.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.expiration-time}")
    private int jwtExpirationTime;
 
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.cookie.key}")
    private String jwtCookieKey;

    
    // generate jwt from user name : 
    public String generateJwtFromUserName(String username){
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + jwtExpirationTime))
            .signWith(key())
            .compact();
    }
    
    
    // stock jwt on the cookie :
    public ResponseCookie stockingJwtOnCookie(UserDetailsImpl userDetails){
        String token = generateJwtFromUserName(userDetails.getUsername());

        ResponseCookie cookie = ResponseCookie
            .from(jwtCookieKey, token)
            .path("/api/v2/")
            .httpOnly(true)
            .maxAge(24*60*60)
            .build();
        return cookie;   
    }

    // get jwt from cookie: 
    public String getJwtFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookieKey);
        if(cookie != null){
            return cookie.getValue();
        }else{
            return null;
        }
       
    }

    // validate jwt : 
    public boolean validateJwtToken(String token){
        try {
            System.out.print("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            logger.error("Jwt is expired : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalide Jwt format : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Jwt token is unsupported : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims string is empty :  {}", e.getMessage());
        }
        return false;
    }

    // get user name from jwt :
    public String getUserNameFromToken(String token){
        
        return Jwts.parser()
            .verifyWith((SecretKey) key())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject()
        ;
    }

    
    // create secretKey :
    private SecretKey key() {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalStateException("spring.app.jwtSecretKey must be at least 32 characters for HS256");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

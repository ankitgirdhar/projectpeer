package com.jira.security;

import com.jira.models.UserModel;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.PushBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jira.security.SecurityConstants.EXPIRATION_TIME;
import static com.jira.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        UserModel user = (UserModel) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userId);
        claims.put("username", user.getUsername());
        claims.put("fullname", user.getFullname());

        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("InvalidJWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            System.out.println("ExpiredJWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Jwt is unsupported!!");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty!!");
        }

        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}

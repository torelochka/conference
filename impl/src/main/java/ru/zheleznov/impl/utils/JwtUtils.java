package ru.zheleznov.impl.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zheleznov.impl.models.User;

import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("authority", user.getRole().toString())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public User getUserFromJwtToken(String token) {
        Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return User.builder()
                .id((long) (Integer) body.get("id"))
                .email((String) body.get("email"))
                .role(User.Role.valueOf((String) body.get("authority")))
                .build();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}

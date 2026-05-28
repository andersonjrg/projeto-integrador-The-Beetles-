package com.Beetles.systempayout.backend.security.tokenService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secretKey;


    public String generateToken(UserDetails user){
         try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("api-system-payout")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getAuthorities().iterator().next().getAuthority())
                    .withExpiresAt(genExpireAt())
                    .sign(algorithm);
                
            return token;
        }catch(JWTCreationException exception){
            throw new RuntimeException("erro ao gerar um token", exception);
        }
    }

    public String validateToken(String token){
        try{
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .withIssuer("api-system-payout")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception){
            return null;        }
    }
    private Instant genExpireAt(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

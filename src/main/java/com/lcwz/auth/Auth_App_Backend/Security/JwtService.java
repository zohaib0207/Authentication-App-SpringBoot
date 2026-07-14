package com.lcwz.auth.Auth_App_Backend.Security;

import com.lcwz.auth.Auth_App_Backend.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class JwtService {

    private final SecretKey key;
    private final long accessTtlseconds; //access token ki expiry
    private final long refreshTtlseconds;  //refresh token ki expiry
    private final String issuer; //issuer of the token

    //abb we need to fetch this
    public JwtService(
                      @Value("${security.jwt.secret}") String secretKey,
                      @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
                      @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
                      @Value("${security.jwt.issuer}") String issuer) {

        //now how to fetch key from the secretKey string?
        if(secretKey==null || secretKey.length()<64){
            throw new IllegalArgumentException("JWT secret key must not be null or empty");
        }
        this.key= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTtlseconds = accessTtlSeconds;
        this.refreshTtlseconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    //Now we will create methods to generate access token and refresh token and all

    //generate access token and pass the data
    public String generateAccessToken(User user){
        Instant now=Instant.now();
        List<String> roles=user.getRoles()==null? List.of():user.getRoles().stream().map(role->role.getName()).toList();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(now.plusSeconds(accessTtlseconds)))
                .claims(
                        java.util.Map.of(
                                "email",user.getEmail(),
                                "name",user.getName(),
                                "typ","access"
                        )
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //Method to generate refresh token
    public String generateRefreshToken(User user , String jti)//Jti is a unique identifier for the token, we can use it to identify the token in the database and all
    {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(now.plusSeconds(refreshTtlseconds)))
                .claims(
                        java.util.Map.of(
                                "typ", "refresh"
                        )
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //Method to parse the token and get the claims
    public Jws <Claims> parse(String token){
        try{
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        }
        catch (Exception e){
            throw new RuntimeException("Invalid JWT token",e);
        }
    }

    public boolean isAccessToken(String token){
       Claims c=parse (token).getPayload();
         return "access".equals(c.get("typ"));
    }

    public boolean isRefreshToken(String token){
        Claims c=parse (token).getPayload();
        return "refresh".equals(c.get("typ"));
    }

    public UUID getUserIdFromToken(String token){
        Claims c=parse(token).getPayload();
        return UUID.fromString(c.getSubject());
    }

    //to get the jti from the token of refresh agar uski id chahiye toh

    public String getJtiFromToken(String token){
        Claims c=parse(token).getPayload();
        return c.getId();
    }
}

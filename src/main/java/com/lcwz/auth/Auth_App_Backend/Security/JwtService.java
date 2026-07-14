package com.lcwz.auth.Auth_App_Backend.Security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtService {

    private final SecretKey key;
    pivate final long accessTtlseconds; //access token ki expiry
    private final long refreshTtlseconds;  //refresh token ki expiry
    private final String issuer; //issuer of the token

    //abb we need to fetch this
    public JwtService(
                      @Value("${security.jwt.secret}") String secretKey,
                      @Value("${security.jwt.access-ttl-seconds}") long accessTtlseconds,
                      @Value("${security.jwt.refresh-ttl-seconds") long refreshTtlseconds,
                      @Value("${security.jwt.issuer") String issuer) {

        //now how to fetch key from the secretKey string?
        if(secretKey==null || secretKey.length()<64){
            throw new IllegalArgumentException("JWT secret key must not be null or empty");
        }
        this.key= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTtlseconds = accessTtlseconds;
        this.refreshTtlseconds = refreshTtlseconds;
        this.issuer = issuer;
    }

    //Now we will create methods to generate access token and refresh token and all

    //generate access token
    
}

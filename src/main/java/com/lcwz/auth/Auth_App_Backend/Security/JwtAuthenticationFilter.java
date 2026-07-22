package com.lcwz.auth.Auth_App_Backend.Security;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.lcwz.auth.Auth_App_Backend.helpers.UserHelper;
import com.lcwz.auth.Auth_App_Backend.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //The token which is coming first we will fetch that (from where is it coming?)
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            //If everything is fine and valid then extract token , validate and extract and authenticate and set inside secuitry context

            //extracting the header
            String token = header.substring(7);
            if(!jwtService.isAccessToken(token)){
                filterChain.doFilter(request,response);
                return ;
            }
            //Now we need to pass the token but it can give error
            try{
                   Jws<Claims>parse =  jwtService.parse(token);
                   //if it is valid then we can extract the claims
                     Claims payload= parse.getPayload();
                     //check for access token

                     String subject = payload.getSubject();
                     //User ID we have taken is in UUID format
                String userId = payload.getSubject();
                UUID userUuid = UserHelper.parseUUID(userId);

                    userRepository.findById(userUuid)
                            .ifPresent(user ->{
                                //user hamein mill chuka hai abb we can set the authentication inside security context
                                List<GrantedAuthority> authorities = user.getRoles() == null
                                        ? List.of()
                                        : user.getRoles()
                                        .stream()
                                        .<GrantedAuthority>map(role -> new SimpleGrantedAuthority(role.getName()))
                                        .toList();

                            //Now we will create the authentication object and set it inside security context
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null ,
                                        authorities
                                );
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                //final line to set authentication to security context
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            });

            }catch (ExpiredJwtException e){
                    e.printStackTrace();
            }catch (MalformedJwtException e){
                    e.printStackTrace();
            }catch(JwtException e){
                    e.printStackTrace();
            }catch (Exception e){
                    e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}

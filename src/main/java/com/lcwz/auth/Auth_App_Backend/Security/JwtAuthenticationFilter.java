package com.lcwz.auth.Auth_App_Backend.Security;

import com.lcwz.auth.Auth_App_Backend.helpers.UserHelper;
import com.lcwz.auth.Auth_App_Backend.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Fetch the Authorization header from the incoming HTTP request.
        // Example:
        // Authorization: Bearer eyJhbGc...
        String header = request.getHeader("Authorization");

        // Check whether the Authorization header exists
        // and contains a Bearer Token.
        if (header != null && header.startsWith("Bearer ")) {

            // Extract only the JWT by removing "Bearer "
            String token = header.substring(7);

            // This filter should authenticate only Access Tokens.
            // Ignore Refresh Tokens.
            if (!jwtService.isAccessToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            try {

                // Parse the JWT.
                // This internally:
                // 1. Verifies Signature
                // 2. Checks Expiry
                // 3. Returns Claims
                Jws<Claims> parse = jwtService.parse(token);

                // Extract JWT Payload (Claims)
                Claims payload = parse.getPayload();

                // Subject contains the User UUID
                // because while generating token we stored:
                // .subject(user.getId().toString())
                String userId = payload.getSubject();

                // Convert String UUID to UUID object
                UUID userUuid = UserHelper.parseUUID(userId);

                // Fetch latest user from database.
                // Even if JWT is valid,
                // the user may have been deleted or disabled.
                userRepository.findById(userUuid)
                        .ifPresent(user -> {

                            // If user account is disabled,
                            // don't authenticate.
                            if (!user.isEnabled()) {
                                return;
                            }

                            // Convert application's Roles into
                            // Spring Security Authorities.
                            List<GrantedAuthority> authorities =
                                    user.getRoles() == null
                                            ? List.of()
                                            : user.getRoles()
                                            .stream()
                                            .<GrantedAuthority>map(role ->
                                                    new SimpleGrantedAuthority(role.getName()))
                                            .toList();

                            // Create Authentication Object.
                            //
                            // Principal   -> Logged-in User
                            // Credentials -> null (already authenticated)
                            // Authorities -> User Roles
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(
                                            user,
                                            null,
                                            authorities
                                    );

                            // Attach request specific details
                            // like IP Address, Session ID etc.
                            authentication.setDetails(
                                    new WebAuthenticationDetailsSource()
                                            .buildDetails(request)
                            );

                            // Store Authentication inside Security Context.
                            // After this Spring Security knows
                            // who the currently logged-in user is.
                            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }
                        });

            } catch (ExpiredJwtException e) {
                // JWT has expired
                e.printStackTrace();

            } catch (MalformedJwtException e) {
                // JWT format is invalid
                e.printStackTrace();

            } catch (JwtException e) {
                // Any other JWT related exception
                e.printStackTrace();

            } catch (Exception e) {
                // Any unexpected exception
                e.printStackTrace();
            }
        }

        // Continue the filter chain.
        // If authentication succeeded,
        // SecurityContext now contains the authenticated user.
        filterChain.doFilter(request, response);
    }
}
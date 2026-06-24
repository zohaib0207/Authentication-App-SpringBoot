package com.lcwz.auth.Auth_App_Backend.dtos;

import com.lcwz.auth.Auth_App_Backend.entities.Provider;
import com.lcwz.auth.Auth_App_Backend.entities.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id; //UUID ensures the ID in our application is unqique
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable=true;
    private Instant createdAt=Instant.now();
    private Instant updatedAt=Instant.now();

    private Provider provider=Provider.LOCAL; //because user can use any medium to login right
    private Set<RoleDto> roles=new HashSet<>();
}

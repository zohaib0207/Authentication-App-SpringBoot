package com.lcwz.auth.Auth_App_Backend.entities;

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


@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false) //@Column annotation helps to specify the condition / constraints
    private UUID id; //UUID ensures the ID in our application is unqique
    @Column(name = "user_email", unique = true)
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable=true;
    private Instant createdAt=Instant.now();
    private Instant updatedAt=Instant.now();

    @Enumerated(EnumType.STRING)
    private Provider provider=Provider.LOCAL; //because user can use any medium to login right

    @ManyToMany(fetch = FetchType.EAGER) //EAGER Basically is whenever we fetch Users , the role is automatically fetched as well
    //Now we have to create a join table to connect the user and role table
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();

    @PrePersist
    protected void onCreate() {
        Instant now=Instant.now();
        if(createdAt==null) {
            createdAt = now;
        }
        updatedAt = now;
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }



}

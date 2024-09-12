package com.demo.security.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
}

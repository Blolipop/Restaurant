package com.order.Restarant.model.login;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Spring Security 建議用 ROLE_ 前綴
    @Column(nullable = false, unique = true)
    private String name; // 範例："ROLE_USER", "ROLE_ADMIN"


    public Role(String roleUser) {
        this.name= roleUser;
    }
}
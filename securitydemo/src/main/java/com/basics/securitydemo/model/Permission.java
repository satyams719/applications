package com.basics.securitydemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "")
@Entity(name = "permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

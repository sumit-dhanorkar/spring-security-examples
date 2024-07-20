package com.sumit.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();

}

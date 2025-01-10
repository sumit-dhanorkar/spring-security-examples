package com.sumit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("role_hierarchy")
public class RoleHierarchy {

    @Id
    private UUID id;
    private String parentRole;    // LIBRARY_USER
    private String branch;        // IT, CS, ME
    private String yearLevel;     // FIRST_YEAR
    private List<String> permissions;

}

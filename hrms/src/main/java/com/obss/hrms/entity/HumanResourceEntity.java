package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanResourceEntity {
    private String dn;


    private String fullName;


    private String lastName;


    private String displayName;
}

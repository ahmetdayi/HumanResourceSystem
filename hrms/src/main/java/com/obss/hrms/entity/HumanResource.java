package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;


@Entry(objectClasses = {"inetOrgPerson"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanResource  {

    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "displayName")
    private String displayName;

    @Attribute(name = "userPassword")
    private String userPassword;



    public HumanResource(String fullName, String lastName, String displayName) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.displayName = displayName;


    }


}
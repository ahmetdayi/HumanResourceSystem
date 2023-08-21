package com.obss.hrms.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("localizedFirstName")
    private String firstName;

    @JsonProperty("localizedLastName")
    private String lastName;

//    @JsonProperty("email")
//    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }


}
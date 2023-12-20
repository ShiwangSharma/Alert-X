package com.shiwangapp.homepagesih.Adapters;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String firstname;
    private String lastname;
    private String userPhone;

    public UserModel(String firstname, String lastname, String userPhone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.userPhone = userPhone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}

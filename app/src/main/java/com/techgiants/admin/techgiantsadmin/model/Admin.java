package com.techgiants.admin.techgiantsadmin.model;

/**
 * Created by Shailesh on 9/27/2017.
 */

public class Admin {
    private String name;
    private String phNo;
    private String pass;
    private String email;
    private String imageUrl;

    public Admin() {
    }

    public Admin(String name, String phNo, String pass, String email, String imageUrl) {
        this.name = name;
        this.phNo = phNo;
        this.pass = pass;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) {  this.imageUrl = imageUrl;   }
}

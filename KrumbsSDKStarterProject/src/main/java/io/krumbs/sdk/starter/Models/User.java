package io.krumbs.sdk.starter.Models;

/**
 * Created by SYED on 15-02-2017.
 */
public class User {
    String uid, name, email, gender,  createdAt, updatedAt;
    Integer age;
    public User(String uid, String name, String email, String gender, Integer age) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }


    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

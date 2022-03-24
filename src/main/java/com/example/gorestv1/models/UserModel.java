package com.example.gorestv1.models;

/*
{
        "id": 4036,
        "name": "Abhaidev Panicker",
        "email": "panicker_abhaidev@gusikowski-wilkinson.co",
        "gender": "male",
        "status": "inactive"
        }
*/
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    private int id;

    private String name;
    private String email;
    private String gender;
    private String status;

    //Do not edit or delete
    public UserModel() {
    }

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   // public String generateReport(){return}

    @Override
    public String toString(){
        return "UserModel{" + "id=" + id + ", name='" + name + '\'' + ", email=" + email + '\'' + ", gender="
                + gender + '\'' + ", status=" + status;
    }
}


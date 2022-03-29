package com.example.gorestv1.models;

//  {
//          "id": 1955,
//          "post_id": 1951,
//          "name": "Msgr. Agneya Sinha",
//          "email": "agneya_msgr_sinha@schaden.biz",
//          "body": "Consequatur voluptas fugiat. Cum consequatur id. Iste maxime nobis."
//          },


import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentModel {

    private int id;

    @JsonProperty("post_id")
    private int postId;

    private String name;
    private String email;
    private String body;

    //don't delete, Keep your default construction just in case

    public CommentModel() {
    }

    public int getId() {
        return id;
    }

    public int getPost_id() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", post_id=" + postId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

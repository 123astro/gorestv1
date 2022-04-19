package com.example.gorestv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostModel {
//    {
//        id: 1641,
//                user_id: 3449,
//            title: "Arto impedit terra sordeo talio cribro minus acervus consequuntur consequatur caste venustas.",
//            body: "Molestiae derideo cetera. Surgo cibo tenus. Venio dignissimos ambulo. Comedo absum vomito.
//            Reprehenderit absconditus defero. Quos cultellus suffoco. Creber tergum tabesco. Sono architecto curis.
//            Sophismata coniuratio inflammatio. Cohibeo tenetur aut. Officiis teneo viriliter. Textus accipio concedo.
//            Ulciscor advenio theca. Casus anser deinde. Ars suus color. Aegrotatio venio surgo. Demulceo auditor
//            itaque. Tumultus nostrum culpo. Est paens crux. Accommodo possimus velum."
//    },

    private int id;
    @JsonProperty("post_id")
    private int user_id;
    private String title;
    private String body;

    public PostModel() {
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}


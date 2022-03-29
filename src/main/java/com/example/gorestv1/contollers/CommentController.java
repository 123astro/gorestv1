package com.example.gorestv1.contollers;


import com.example.gorestv1.models.CommentModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/comment")
public class CommentController {


    @GetMapping("/firstpage")
    public CommentModel getFirstPage (RestTemplate restTemplate){
        String url = "https://gorest.co.in/public/v2/comments";
        CommentModel comment = restTemplate.getForObject(url, CommentModel.class);

        return comment;

        // return restTemplate.getForObject(url, CommentModel.class); //no headers needed => getForObject

    }
}

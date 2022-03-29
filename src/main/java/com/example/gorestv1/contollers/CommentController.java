package com.example.gorestv1.contollers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/comment")
public class CommentController {


    @GetMapping("/firstpage")
    public Object getFirstPage (RestTemplate restTemplate){
        String url = "https://gorest.co.in/public/v2/comments";
        return restTemplate.getForObject(url, Object.class); //no headers needed => getForObject

    }
}

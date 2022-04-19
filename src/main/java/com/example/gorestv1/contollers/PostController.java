package com.example.gorestv1.contollers;

import com.example.gorestv1.models.CommentModel;
import com.example.gorestv1.models.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/post")
public class PostController {


    @Autowired
    Environment evn;

    @GetMapping("/{id}")
    public Object getOnePost(RestTemplate restTemplate, @PathVariable("id") int postId) {
        String url = "https://gorest.co.in/public/v2/posts?id=" + postId;
        return restTemplate.getForObject(url, PostModel[].class);
    }

    @DeleteMapping("/{id}")
    public Object deleteOnePost(RestTemplate restTemplate, @PathVariable("id") int postId) {  // change to object
        String url = "https://gorest.co.in/public/v2/posts/" + postId;  // added the /
        String token = evn.getProperty("GO_REST_TOKEN");
        url += "?access-token=" + token;
        restTemplate.delete(url);
        return "Successfully Deleted user# " + postId;
    }

    @PostMapping
    public ResponseEntity<Object> postBody(RestTemplate restTemplate, @RequestBody PostModel newPost
    ) {
        String url = "https://gorest.co.in/public/v2/posts/";
        String token = evn.getProperty("GO_REST_TOKEN");
        url += "?access-token=" + token;

        HttpEntity<PostModel> request = new HttpEntity<>(newPost);
        PostModel createPost = restTemplate.postForObject(url, request, PostModel.class);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);


    }

}
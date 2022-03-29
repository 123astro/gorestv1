package com.example.gorestv1.contollers;


import com.example.gorestv1.models.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    Environment evn;


    @GetMapping("/firstpage")
    public CommentModel[] getFirstPage(RestTemplate restTemplate) {
        String url = "https://gorest.co.in/public/v2/comments";
        return restTemplate.getForObject(url, CommentModel[].class);
       // return restTemplate.getForObject(url, Object.class);

    }

    @GetMapping("/{id}")
    public ResponseEntity getOneComment(RestTemplate restTemplate, @PathVariable("id") int commentId) {

        try {
            String url = "https://gorest.co.in/public/v2/comments" + commentId;
            //turn new ResponseEntity(restTemplate.getForObject(url, CommentModel.class), HttpStatus.OK);
            return new ResponseEntity(restTemplate.getForObject(url, CommentModel.class), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // return restTemplate.getForObject(url, CommentModel.class); //no headers needed => getForObject

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneComment(RestTemplate restTemplate, @PathVariable("id") int commentId) {

        try {
            String url = "https://gorest.co.in/public/v2/comments" + commentId;
            String token = evn.getProperty("GO_REST_TOKEN");
            url += "?access-token=" + token;
            restTemplate.delete(url);

            CommentModel deletedComment = restTemplate.getForObject(url, CommentModel.class);
            return new ResponseEntity<>(deletedComment, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> postComment(RestTemplate restTemplate, @RequestBody CommentModel newComment
    ) {
        try {
        String url = "https://gorest.co.in/public/v2/comments";
        String token = evn.getProperty("GO_REST_TOKEN");
        url += "?access-token=" + token;

        HttpEntity<CommentModel> request = new HttpEntity<>(newComment);
        CommentModel createComment = restTemplate.postForObject(url, request, CommentModel.class);
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);

    } catch (Exception e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PutMapping ("/{id}")
    public ResponseEntity<Object> putComment(
            RestTemplate restTemplate,
            @RequestBody CommentModel updateComment
    ) {
        try {
            String url = "https://gorest.co.in/public/v2/comments" + updateComment.getId();
            String token = evn.getProperty("GO_REST_TOKEN");
            url += "?access-token=" + token;

            HttpEntity<CommentModel> request = new HttpEntity<>(updateComment);

            ResponseEntity<CommentModel> response = restTemplate.exchange(url, HttpMethod.PUT, request,
                    CommentModel.class);

            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



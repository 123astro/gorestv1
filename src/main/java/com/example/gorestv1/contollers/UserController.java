package com.example.gorestv1.contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    Environment env;

    // url / endpoint http://localhost:4444/api/user/token
    @GetMapping("/token")
    public String getToken() {
        return env.getProperty("GO_REST_TOKEN");
    }

    //URL / endpoint GET http://localhost:4444/api/user/{id}
    @GetMapping("/{id}")
    public Object getOneUser(@PathVariable("id") String userId,
                             RestTemplate restTemplate
    ) {
        try {
            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String apiToken =env.getProperty("GO_REST_TOKEN");

//            HttpHeaders headers = new HttpHeaders();
//            headers.setBearerAuth(env.getProperty("GO_REST_TOKEN"));
//            HttpEntity request = new HttpEntity(headers);
//
//            return restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    request,
//                    Object.class
//            );

           // return restTemplate.getForObject(url, Object.class);
        url += "?access-token=" + apiToken;

        return restTemplate.getForObject(url, Object.class);

        } catch (Exception exception) {
            return "404: No user exist with the ID " + userId;
        }
    }

    //URL / endpoint DELETE http://localhost:4444/api/user/{id}
    @DeleteMapping("/{id}")
    public Object deleteOneUser(@PathVariable("id") String userId,
                                RestTemplate restTemplate) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String token = env.getProperty("GOREST_TOKEN");
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            //headers.set("Authorization", Bearer " + token);

            HttpEntity request = new HttpEntity(headers);
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    Object.class
            );
            //  restTemplate.delete(url);
            return "Successfully Deleted user# " + userId;
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }


}
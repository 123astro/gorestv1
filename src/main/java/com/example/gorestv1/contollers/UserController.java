package com.example.gorestv1.contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping ("/api/user")
public class UserController {

    @Autowired
    Environment env;

    // url / endpoint http://localhost:4444/api/user/token
    @GetMapping ("/token")
    public String getToken() {
        return env.getProperty("GOREST_TOKEN");
    }

    @GetMapping("/{id}")
    public Object getOneUser(@PathVariable ("id") String userId,
        RestTemplate restTemplate){
        try {
            String url = "https://gorest.co.in/public/v2/users/" + userId;
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception exception){
            return "404: No user exist with the ID " + userId;
        }
    }
}

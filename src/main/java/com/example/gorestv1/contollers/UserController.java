package com.example.gorestv1.contollers;


import jdk.swing.interop.SwingInterOpUtils;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
            String apiToken = env.getProperty("GO_REST_TOKEN");

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
            String token = env.getProperty("GO_REST_TOKEN");
            url += "?access-token=" + token;
            restTemplate.delete(url);
            return "Successfully Deleted user# " + userId;
        } catch (HttpClientErrorException.NotFound exception) {
            return "User could not be deleted, user # " + userId + " does not exist";
        } catch (HttpClientErrorException.Unauthorized exception) {
            return "You are not authorized to delete user #" + userId;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return exception.getMessage();
        }
    }
}
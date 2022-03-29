package com.example.gorestv1.contollers;


import com.example.gorestv1.models.UserModel;
import com.example.gorestv1.models.UserModelArray;
import jdk.swing.interop.SwingInterOpUtils;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    Environment env;

    // URL / endpoint http://localhost:4444/api/user/token
    @GetMapping("/token")
    public String getToken() {
        return env.getProperty("GO_REST_TOKEN");
    }

    // URL / endpoint GET http://localhost:4444/api/user/firstpage
    @GetMapping("/page/{pageNum}")
    public Object getPage(RestTemplate restTemplate, @PathVariable("pageNum") String pageNumber) {
        try {

            //String url = "https://gorest.co.in/public/v2/users/";
            String url = "https://gorest.co.in/public/v2/users?page=" + pageNumber;

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);

            UserModel[] firstPageUsers = response.getBody();

            HttpHeaders responseHeaders = response.getHeaders();
            String totalPages = Objects.requireNonNull(responseHeaders.get("X-Pagination-Pages")).get(0);

//            ArrayList<String> aList = new ArrayList<String>(Arrays.asList(question1));
//            aList.addAll(Arrays.asList(question2));


            System.out.println("Total Pages: " + totalPages);

            return new ResponseEntity<>(firstPageUsers, HttpStatus.OK);
//
//            for (int i = 0; i < firstPageUsers.length; i++) {
//                UserModel tempUser = firstPageUsers[i];
//                System.out.println(tempUser.toString());
//
//            }
//
//            return firstPage;

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page/all")
    public Object getAll(RestTemplate restTemplate) {
        try {
            ArrayList<UserModel> allUsers = new ArrayList<UserModel>();
            String url = "https://gorest.co.in/public/v2/users";

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);

            allUsers.addAll(Arrays.asList(Objects.requireNonNull(response.getBody()))); //get the first page

            // getting the total number of pages
            HttpHeaders responseHeaders = response.getHeaders();
            String totalPages = Objects.requireNonNull(responseHeaders.get("X-Pagination-Pages")).get(0);
            int totalPageNum = Integer.parseInt(totalPages);

            for (int i = 2; i <= totalPageNum; i++) {
                UserModel[] pageOfUsers = restTemplate.getForObject(url + "?page=" + i , UserModel[].class);
                allUsers.addAll(Arrays.asList(pageOfUsers));
            }
                System.out.println("Total Pages: " + totalPages);
                return new ResponseEntity<>(allUsers, HttpStatus.OK);

            } catch(Exception e){
                System.out.println(e.getClass());
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        // URL / endpoint GET http://localhost:4444/api/user/{id}
        @GetMapping("/{id}")
        public Object getOneUser (@PathVariable("id") String userId,
                RestTemplate restTemplate){
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
                url += "?access-token=" + apiToken;  //  "https://gorest.co.in/public/v2/users/" + userId +
                // "?access-token=" + apiToken

                return restTemplate.getForObject(url, Object.class);

            } catch (Exception exception) {
                return "404: No user exist with the ID " + userId;
            }
        }

        //URL / endpoint DELETE http://localhost:4444/api/user/{id}
        @DeleteMapping("/{id}")
        public Object deleteOneUser (@PathVariable("id") String userId,
                RestTemplate restTemplate){
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

//    @PostMapping ("/")
//    public Object postUser (
//            @RequestParam ("name") String name,
//            @RequestParam ("email") String email,
//            @RequestParam ("gender") String gender,
//            @RequestParam ("status") String status
//    ) {
//        try {
//            String url = "https://gorest.co.in/public/v2/users/";
//            String token = env.getProperty("GO_REST_TOKEN");
//            url += "?access-token=" + token;
//
//            UserModel newUser =new UserModel(name, email, gender, status);
//
//            //TODO: validate that gender and status are valid before continuing
//
//            //TODO: validate that the email is valid email
//
//            return  "Created new user";
//
//
//        } catch (Exception exception){
//            System.out.println(exception.getClass());
//            return exception.getMessage();
//        }

        @PostMapping("/qp")
        public Object postUserQueryParameter (
                @RequestParam("name") String name,
                @RequestParam("email") String email,
                @RequestParam("gender") String gender,
                @RequestParam("status") String status,
                RestTemplate restTemplate
    ){
            try {
                String url = "https://gorest.co.in/public/v2/users/";
                String token = env.getProperty("GO_REST_TOKEN");
                url += "?access-token=" + token;

                UserModel newUser = new UserModel(name, email, gender, status);

//            HttpHeaders headers = new HttpHeaders();
//            assert token != null;
//            headers.setBearerAuth(token);
//            HttpEntity request = new HttpEntity(newUser, headers);

                HttpEntity request = new HttpEntity(newUser);

                //TODO: validate that gender and status are valid before continuing

                //TODO: validate that the email is valid email

                return restTemplate.postForEntity(url, request, UserModel.class);


            } catch (Exception exception) {
                System.out.println(exception.getClass());
                return exception.getMessage();
            }
        }

        @PostMapping("/")
        public ResponseEntity postUser (
                RestTemplate restTemplate,
                @RequestBody UserModel newUser
    ){
            try {
                String url = "https://gorest.co.in/public/v2/users/";
                String token = env.getProperty("GO_REST_TOKEN");
                url += "?access-token=" + token;

                HttpEntity<UserModel> request = new HttpEntity<>(newUser);

                return restTemplate.postForEntity(url, request, UserModel.class);
            } catch (Exception e) {
                System.out.println(e.getClass() + " \n " + e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

//      @PutMapping("/{id}")
//    public ResponseEntity putUser(
//
//            RestTemplate restTemplate,
//            @RequestBody UserModel updateData
//      )

        // Serialized Spring boot data is JSON data
        // Deserialized Spring boot data is an Object

        // link plug in post man
    }
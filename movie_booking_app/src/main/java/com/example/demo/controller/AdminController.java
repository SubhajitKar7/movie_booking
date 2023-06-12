package com.example.demo.controller;


import com.example.demo.model.Key;
import com.example.demo.model.UserDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("call/admin")
@CrossOrigin(origins = "*")
public class AdminController
{
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody UserDTO userdto) throws RestClientException, Exception
    {
        String baseUrl="http://localhost:8084/auth/v1.0/moviebooking/login";// API consumption.. actual api is hidden -not exposed
        //String baseUrl="http://44.227.177.145:8084/auth/v1.0/moviebooking/login";
//        String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/login";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, String>> result;


        try
        {

            result= restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userdto), new ParameterizedTypeReference<Map<String,String>>(){});
            //System.out.println("****");

            Map<String,String> map=new HashMap<>();
            map=result.getBody();
            Key.key=map.get("Token:");
            //System.out.println(Key.key);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>("Login failed", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> adminRegister(@RequestBody UserDTO userdto) throws RestClientException, Exception
    {
        String baseUrl="http://localhost:8084/auth/v1.0/moviebooking/register";// API consumption.. actual api is hidden -not exposed
      //  String baseUrl="http://44.227.177.145:8084/auth/v1.0/moviebooking/register";
//        String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/register";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, String>> result;


        try
        {

            result= restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userdto), new ParameterizedTypeReference<Map<String,String>>(){});

        }
        catch(Exception e)
        {
            return new ResponseEntity<String>("Admin Signup failed", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.UNAUTHORIZED);
    }


    private static HttpEntity<UserDTO> getHeaders(UserDTO userdto)
    {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Access-Control-Allow-Origin","*"); // change
        return new HttpEntity<UserDTO>(userdto,headers);
    }

}

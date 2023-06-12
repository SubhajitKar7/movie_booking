package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import com.example.demo.model.Key;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.UserDTO;



@RestController
@RequestMapping("call/consumer")
@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin")
public class ConsumerController 
{
	@PostMapping("/login")
	public ResponseEntity<?> consumeLogin(@RequestBody UserDTO userdto) throws RestClientException, Exception
	{
		String baseUrl="http://localhost:8084/auth/v1.0/moviebooking/login";// API consumption.. actual api is hidden -not exposed
		//String baseUrl="http://44.227.177.145:8084/auth/v1.0/moviebooking/login";
//		String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/login";
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Map<String, String>> result;
		

	try
		{

	result= restTemplate.exchange(baseUrl,HttpMethod.POST, getHeaders(userdto), new ParameterizedTypeReference<Map<String,String>>(){});
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
	public ResponseEntity<?> consumeRegister(@RequestBody UserDTO userdto) throws RestClientException, Exception
	{
		String baseUrl="http://localhost:8084/auth/v1.0/moviebooking/register";// API consumption.. actual api is hidden -not exposed
	//String baseUrl="http://44.227.177.145:8084/auth/v1.0/moviebooking/register";
//	String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/register";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Map<String, String>> result;


		try
		{

			result= restTemplate.exchange(baseUrl,HttpMethod.POST, getHeaders(userdto), new ParameterizedTypeReference<Map<String,String>>(){});

		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("Signup failed", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.OK);
	}


	
	private static HttpEntity<UserDTO> getHeaders(UserDTO userdto)
	{HttpHeaders headers = new HttpHeaders();
	
	  headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
	  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin","*");
	//	headers.set("Access-Control-Allow-Headers",["X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method, Authorization"]);
		headers.set("Access-Control-Allow-Methods","[GET, POST, OPTIONS, PUT, DELETE, PATCH]");
		return new HttpEntity<UserDTO>(userdto,headers);
	}

}


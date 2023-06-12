package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.*;
//import com.example.demo.service.DataPublisherServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import com.example.demo.exception.MovieIdAlreadyExistsExceptions;
import com.example.demo.service.MovieService;
import com.example.demo.service.TicketService;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1.0")
@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin")
public class MovieController {

	//private String url="http://44.227.177.145:8084";
	private String url="http://44.227.177.145:8084";

	//@Autowired
	//DataPublisherServiceImpl dataPublisherService;

	@Autowired
	 private TicketService ticketService;
	
	@Autowired
	private MovieService movieService;
	
	
		
		@GetMapping("user/moviebooking/all")
		public ResponseEntity<?> viewAllMovies() {
			
			List<Movie> movies=movieService.getAllMovies();
			
			if(movies!=null)
			{

				//dataPublisherService.setTemp("Showing movies...");
				System.out.println(movies);
				System.out.println("*****----");
				return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
			}
			return new ResponseEntity<String>("No movies to show", HttpStatus.OK);
			
			
		}
		
		@PostMapping("admin/moviebooking/addMovie")
		public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws MovieIdAlreadyExistsExceptions 
		{
			if(movieService.addMovie(movie)!=null)
			{
				return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
					}
			else {
				return new ResponseEntity<String>("Movie cannot be aded", HttpStatus.OK);
				
			}
		}
		
		
		
		@GetMapping("user/moviebooking/movies/search/moviedByID/{mid}")
		public ResponseEntity<?> getMovieById(@PathVariable("mid") int mid)
		{
			Movie movie = movieService.getMovieById(mid);
			if(movie !=null)
			{
				return new ResponseEntity<Movie>(movie, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Movie  does not exist",HttpStatus.OK);
		}
		
		
		
		@DeleteMapping("admin/moviebooking/delete/movieById/{mid}")
		public ResponseEntity<?> deleteMovie(@PathVariable ("mid") int mid)
		{
			Map<String,String> map=new HashMap<>();
			if(movieService.deleteMovie(mid))
			{
				map.put("msg","Movie got deleted");
				return new ResponseEntity<Map<String,String>>(map ,HttpStatus.OK);
			}
			map.put("msg","Movie is not  deleted");
			return new ResponseEntity<Map<String,String>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		@PutMapping("admin/moviebooking/updateMovie")
		public ResponseEntity<?> updateMovie(@RequestBody Movie movie)
		{
			if(movieService.updateMovie(movie))
			{

				return new ResponseEntity<Movie>(movie,HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("Movie updation failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

			@Hidden
			@PutMapping("user/moviebooking/movie/update/ticket/{mid}")
		public ResponseEntity<?> updateTicketStatus(@PathVariable ("mid") int mid,@RequestBody Ticket ticket)
		{
			System.out.println("mid= "+mid);
			System.out.println(ticket);

			if(movieService.updateTicketStatus(mid,ticket))
			{
				System.out.println("Running");
				return new ResponseEntity<String>("Movie got updated successfully",HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("Movie updation failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}



		@PostMapping("user/moviebooking/book/{movieName}")
		public ResponseEntity<?> addTicket(@PathVariable("movieName") String movieName, @RequestBody TicketDto ticketDto)
		{
			Movie movie=movieService.getMovieByName(movieName);
			System.out.println(movie);
			Ticket ticket=new Ticket();
			if(movie!=null)
			{
				ticket.setMovie(movie);
				ticket.setMovieName(movie.getMovieName());
				ticket.setTheatreName(movie.getTheatreName());
				ticket.setTotalSeat(movie.getTotalSeat());
				ticket.setAvailableSeat(movie.getSeatAvailable()-ticketDto.getTickets());
				ticket.setBookedSeat(ticketDto.getTickets());
				ticket.setUserId(ticketDto.getUserId());

				if(movie.getSeatAvailable()>0)
				{
					if(movieService.updateMovie(movie) && ticketService.addTicket(ticket))
					{

						RestTemplate restTemplate=new RestTemplate();


						restTemplate.exchange("http://localhost:8081/api/v1.0/user/moviebooking/movie/update/ticket/"+movie.getMovieId(), HttpMethod.PUT, getHeaders(ticket),new ParameterizedTypeReference<String>(){});
						// restTemplate.exchange("http://44.227.177.145:8081/api/v1.0/user/moviebooking/movie/update/ticket/"+movie.getMovieId(), HttpMethod.PUT, getHeaders(ticket),new ParameterizedTypeReference<String>(){});
//			restTemplate.exchange("https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/booking/updateticket/"+movie.getMovieId(),HttpMethod.PUT,  getHeaders(ticket),new ParameterizedTypeReference<String>(){});

						return new ResponseEntity<Ticket>(ticket,HttpStatus.CREATED);
					}

				}

			}
			

			return new ResponseEntity<String>("Ticket not booked", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

		@GetMapping("moviebooking/forgot/{loginId}")
		public ResponseEntity<?> forgetPassword(@PathVariable("loginId") String loginId)
		{
			String baseUrl="http://localhost:8084/auth/v1.0/forgot/"+loginId;// API consumption.. actual api is hidden -not exposed
		//String baseUrl=url+"/auth/v1.0/forgot/"+loginId;
//	String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/forgot/"+loginId;

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<Map<String, String>> result;


			try
			{

				result= restTemplate.exchange(baseUrl,HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<Map<String, String>>(){});

			}
			catch(Exception e)
			{
				return new ResponseEntity<String>("No user found to update password ", HttpStatus.UNAUTHORIZED);
			}

			return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.OK);
		}


	@GetMapping("moviebooking/forgot/getUser/{loginId}")
	public ResponseEntity<?> getUser(@PathVariable("loginId") String loginId)
	{
		String baseUrl="http://localhost:8084/auth/v1.0/forgot/getUser/"+loginId;// API consumption.. actual api is hidden -not exposed
		//String baseUrl=url+"/auth/v1.0/forgot/getUser/"+loginId;
//	String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/forgotuser/"+loginId;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Map<String, String>> result;


		try
		{

			result= restTemplate.exchange(baseUrl,HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<Map<String, String>>(){});

		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("No user found to update password ", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.OK);
	}


	@PutMapping("moviebooking/forgot/updatepassword/{loginId}")
	public ResponseEntity<?> updatePassword(@PathVariable("loginId") String loginId,@RequestBody UserDTO user)
	{
		String baseUrl="http://localhost:8084/auth/v1.0/forgot/updatepassword/"+loginId;// API consumption.. actual api is hidden -not exposed
	//String baseUrl=url+"/auth/v1.0/forgot/updatepassword/"+loginId;
//	String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/forgotupdate/"+loginId;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity< Map<String, String>> result;


		try
		{

			result= restTemplate.exchange(baseUrl,HttpMethod.PUT, getHeaders(user), new ParameterizedTypeReference< Map<String, String>>(){});

		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("No user found to update password ", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.OK);
	}


	@GetMapping("admin/moviebooking/getAllUsers")
	public ResponseEntity<?> getAllUsers()
	{
		System.out.println("*/*/*/*/*/*/*/");
		String baseUrl="http://localhost:8084/api/v1.0/getAllUsers";// API consumption.. actual api is hidden -not exposed
		//String baseUrl=url+"/api/v1.0/getAllUsers";
		//String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/users";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<UserDTO>> result;


		try
		{

			result= restTemplate.exchange(baseUrl,HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<List<UserDTO>>(){});
			System.out.println(result.getBody());
		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("No user found to view ", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<List<UserDTO>>(result.getBody(), HttpStatus.OK);
	}

	@DeleteMapping("admin/moviebooking/deleteUserById/{loginId}")
	public ResponseEntity<?> deleteUserBYId(@PathVariable("loginId") String loginId)
	{

		String baseUrl="http://localhost:8084/api/v1.0/deleteUserById/"+loginId;// API consumption.. actual api is hidden -not exposed
		//String baseUrl=url+"/api/v1.0/deleteUserById/"+loginId;
	//String baseUrl="https://457ert06q2.execute-api.us-west-2.amazonaws.com/movie_app/myuserresource/users/"+loginId;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Map<String,String>> result;

		try
		{

			result= restTemplate.exchange(baseUrl,HttpMethod.DELETE, getHeaders(), new ParameterizedTypeReference<Map<String,String>>(){});
			System.out.println("Hit");
		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("No user found to delete ", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Map<String,String>>(result.getBody(), HttpStatus.OK);
	}

	//new add
	@GetMapping("moviebooking/getBookingList/{loginId}")
	public ResponseEntity<?>  getBooingList(@PathVariable("loginId") String loginId)
	{

		if(ticketService.getBookingList(loginId)!=null)
		{
			System.out.println("Running");
			return new ResponseEntity<List<Ticket>>(ticketService.getBookingList(loginId),HttpStatus.OK);
		}

		return new ResponseEntity<String>("No tickets to show",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private static HttpEntity<Ticket> getHeaders(Ticket ticket)
	{HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + Key.key);

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin","*");
		headers.set("Access-Control-Allow-Methods","[GET, POST, OPTIONS, PUT, DELETE, PATCH]");
		return new HttpEntity<Ticket>(ticket,headers);

	}


	private static HttpEntity<?> getHeaders()
	{HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + Key.key);

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin","*");
		headers.set("Access-Control-Allow-Methods","[GET, POST, OPTIONS, PUT, DELETE, PATCH]");
		return new HttpEntity<>(headers);
	}

	private static HttpEntity<?> getHeaders(UserDTO userDTO)
	{HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + Key.key);

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin","*");
		headers.set("Access-Control-Allow-Methods","[GET, POST, OPTIONS, PUT, DELETE, PATCH]");
		return new HttpEntity<UserDTO>(userDTO,headers);
	}

}

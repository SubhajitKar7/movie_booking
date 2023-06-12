package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.MovieIdAlreadyExistsExceptions;
import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;

public interface MovieService {


	public List<Movie> getAllMovies();
	
	public Movie addMovie(Movie movie) throws MovieIdAlreadyExistsExceptions;
	
	public boolean updateMovie(Movie movie);
	
	public boolean deleteMovie(int mid);
	
	public Movie getMovieById(int mid);

	public Movie getMovieByName(String movieName);


	public boolean updateTicketStatus(int mid,Ticket ticket);
	

	
}

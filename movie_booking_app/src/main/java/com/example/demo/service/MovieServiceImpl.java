package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MovieIdAlreadyExistsExceptions;
import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.TicketRepository;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private  MovieRepository movieRepository;
	@Autowired
	private TicketRepository ticketRepository;



	@Override
	public List<Movie> getAllMovies() {
		List<Movie> movies=movieRepository.findAll();

		if(movies!=null && movies.size()>0)
		{
			return movies;
		}
		else {
			return null;
					
		}
	}


	@Override
	public Movie addMovie(Movie movie) throws MovieIdAlreadyExistsExceptions {
			
	Optional<Movie>	moviOptional=movieRepository.findById(movie.getMovieId());
	if(moviOptional.isPresent())
	{
			throw new MovieIdAlreadyExistsExceptions();
	}else {
		return movieRepository.saveAndFlush(movie);

	}
	
		
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean updateMovie(Movie movie) {
		
		Movie movie2=movieRepository.getById(movie.getMovieId());
		if(movie2!=null)
		{
			movie2.setMovieName(movie.getMovieName());
			movie2.setSeatAvailable(movie.getSeatAvailable());
			movie2.setSeatBooked(movie.getSeatBooked());
			movie2.setTheatreName(movie.getTheatreName());
			movie2.setStatus("BOOK ASAP");
			
			movieRepository.saveAndFlush(movie2);
			return true;
		}
		else {
			return false;
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean deleteMovie(int mid) {
		Movie movie2=movieRepository.getById(mid);
		if(movie2!=null)
		{
			movieRepository.deleteById(mid);
			return true;	
		}
		else {
		return false;
	}
	}


	@Override
	public Movie getMovieById(int mid) {
		Optional<Movie>	moviOptional=movieRepository.findById(mid);
		if(moviOptional.isPresent())
		{
				return moviOptional.get();
		}else {
			
				return null;
		}
		
	}


	@Override
	public Movie getMovieByName(String movieName) {
		Movie movie=movieRepository.getByMovieName(movieName);
		//System.out.println(movie.getMovieName());
		return movie;
	}


	@SuppressWarnings("deprecation")
	@Override	
	public boolean updateTicketStatus(int mid,Ticket ticket) {

		System.out.println("UPDATEREEE");

	Ticket ticket2=ticketRepository.getById(ticket.getTransactionId());
	Movie movie= movieRepository.getById(mid);

	

	movie.setSeatAvailable(ticket2.getAvailableSeat());
	movie.setSeatBooked(movie.getSeatBooked()+ticket2.getBookedSeat());


		System.out.println(ticket);
		System.out.println("-----");
		System.out.println(movie);

	if(movie.getSeatAvailable()==0)
		movie.setStatus("SOLD OUT");
	else 
		movie.setStatus("BOOK ASAP");
	
	movieRepository.saveAndFlush(movie);
	
	 return true;
	}

	
}

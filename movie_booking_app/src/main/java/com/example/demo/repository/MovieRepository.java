package com.example.demo.repository;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<Movie,Integer>{
	
	
	

	@Query(value="select m from Movie m where m.movieName= :movieName")
	public Movie getByMovieName(String movieName);

}

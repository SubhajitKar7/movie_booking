package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket,Integer>{
	
	
	//public String findMovieNameBy(String movieName);
	/*
	@Query(value="select t from Ticket t where t.movieId= :mid")
	public Set<Ticket> getTicketList(int mid);
	
	@Modifying
	@Query(value="delete from Ticket t where t.movieId= :mid")
	public void deleteTicketData(int mid);
	*/

    @Query(value="select t from Ticket t where t.userId= :loginId")
    public List<Ticket> getList(String loginId);


}

package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private  MovieRepository movieRepository;
	@Autowired
	private TicketRepository ticketRepository;




/*

	@Override
	public Set<Ticket> getAllTickets(int mid) {
		Set<Ticket> tickets=ticketRepository.getTicketList(mid);
		return tickets;
	
	}*/



	@Override
	public boolean addTicket(Ticket ticket) {

		ticketRepository.saveAndFlush(ticket);
		return true;
	}

	@Override
	public List<Ticket> getBookingList(String loginId) {

		List<Ticket> ticketList=ticketRepository.getList(loginId);

		return ticketList;
	}

/*

	@Override
	public boolean deleteTicket(int mid) {
		ticketRepository.deleteTicketData(mid);
		return false;
	}*/
}

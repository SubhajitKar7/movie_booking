package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.model.Ticket;

public interface TicketService {

	//public Set<Ticket> getAllTickets(int mid);
	public boolean addTicket(Ticket ticket);
	//public boolean deleteTicket(int mid);

	public List<Ticket> getBookingList(String loginId);
}

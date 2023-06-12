package com.example.demo.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

	@Id
	private int movieId;

	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTheatreName() {
		return TheatreName;
	}
	public void setTheatreName(String theatreName) {
		TheatreName = theatreName;
	}
	public int getSeatAvailable() {
		return seatAvailable;
	}
	public void setSeatAvailable(int seatAvailable) {
		this.seatAvailable = seatAvailable;
	}
	public int getSeatBooked() {
		return seatBooked;
	}
	public void setSeatBooked(int seatBooked) {
		this.seatBooked = seatBooked;
	}

	public void setStatus(String status){ this.status=status;}

	public String getStatus(){return this.status;}



	public Set<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(Set<Ticket> ticketList) {
		this.ticketList = ticketList;
	}



	private String movieName;
	private String TheatreName;
	private int seatAvailable;
	private int seatBooked;
	private String status;

	public int getTotalSeat() {
		return totalSeat;
	}

	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}

	private int totalSeat;
	
	@OneToMany(
			mappedBy = "movie",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
			/*targetEntity = Ticket.class*/
			)
	@JsonManagedReference
	private Set<Ticket> ticketList;

	@Override
	public String toString() {
		return "Movie{" +
				"movieId=" + movieId +
				", movieName='" + movieName + '\'' +
				", TheatreName='" + TheatreName + '\'' +
				", seatAvailable=" + seatAvailable +
				", seatBooked=" + seatBooked +
				", status='" + status + '\'' +
				", totalSeat=" + totalSeat +
				", ticketList=" + ticketList +
				'}';
	}
}

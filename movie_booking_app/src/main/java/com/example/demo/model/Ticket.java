package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue
	private int transactionId;
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getTotalSeat() {
		return totalSeat;
	}
	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}
	public int getAvailableSeat() {
		return availableSeat;
	}
	public void setAvailableSeat(int availableSeat) {
		this.availableSeat = availableSeat;
	}
	public int getBookedSeat() {
		return bookedSeat;
	}
	public void setBookedSeat(int bookedSeat) {
		this.bookedSeat = bookedSeat;
	}


	private int totalSeat;
	private int availableSeat;
	private int bookedSeat;

	/*public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	//private int movieId;
	*/


	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name="movie_id")
	private Movie movie;



	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	private String theatreName;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}



	private String movieName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String userId;

	@Override
	public String toString() {
		return "Ticket{" +
				"transactionId=" + transactionId +
				", totalSeat=" + totalSeat +
				", availableSeat=" + availableSeat +
				", bookedSeat=" + bookedSeat +

				", theatreName='" + theatreName + '\'' +
				", movieName='" + movieName + '\'' +
				", userId='" + userId + '\'' +

				'}';
	}

}

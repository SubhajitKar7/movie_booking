package com.example.demo.service;


import com.example.demo.exception.MovieIdAlreadyExistsExceptions;
import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieService).build();
    }


    @Test
    public void getAllMoviesTest1(){


       Ticket ticket=Ticket.builder()
                .transactionId(1)
                .movieName("movieA")
                .availableSeat(95)
                .totalSeat(100)
                .bookedSeat(5)
                .theatreName("theatre1")
                .build();

        Set<Ticket> tickets=new HashSet<>();
        tickets.add(ticket);

        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(tickets)
                .build();


        List<Movie> movies=new ArrayList<>();
        movies.add(movie);

        when(movieRepository.findAll()).thenReturn(movies);

        assertEquals(movieService.getAllMovies(),movies);
    }


    @Test
    public void getAllMoviesTest2(){

        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();

        List<Movie> movies=new ArrayList<>();
        movies.add(movie);

        when(movieRepository.findAll()).thenReturn(movies);

        assertEquals(movieService.getAllMovies(),movies);
    }

    @Test
    public void getAllMoviesTest3(){


        when(movieRepository.findAll()).thenReturn(null);

        assertEquals(movieService.getAllMovies(),null);
    }


    @Test
    public void addMovieTest1() throws MovieIdAlreadyExistsExceptions {
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();


        when(movieRepository.saveAndFlush(any())).thenReturn(movie);

        assertEquals(movieService.addMovie(movie),movie);

    }


    @Test
    public void addMovieTest2() throws MovieIdAlreadyExistsExceptions {
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();


        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));


        assertThrows(MovieIdAlreadyExistsExceptions.class,()->{movieService.addMovie(movie);});

    }

    @Test
    public void getMovieByIdTest1()
    {
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();

        when(movieRepository.findById(1)).thenReturn(Optional.ofNullable(movie));

        assertEquals(movieService.getMovieById(1),movie);

    }

    @Test
    public  void getMovieByNameTest1()
    {
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();

        when(movieRepository.getByMovieName("movieA")).thenReturn(movie);
        assertEquals(movieService.getMovieByName("movieA"),movie);
    }

    @Test
    public  void getMovieByNameTest2()
    {
        when(movieRepository.getByMovieName("movieA")).thenReturn(null);
        assertEquals(movieService.getMovieByName("movieA"),null);
    }

    @Test
    public void deleteMovieTest1(){

        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();
        when(movieRepository.getById(1)).thenReturn(movie);

        assertEquals(movieService.deleteMovie(1),true);
    }


    @Test
    public void deleteMovieTest2(){


        when(movieRepository.getById(1)).thenReturn(null);

        assertEquals(movieService.deleteMovie(1),false);
    }


    @Test
    public void updateMovieTest1(){
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();

        when(movieRepository.getById(1)).thenReturn(movie);
        when(movieRepository.saveAndFlush(any())).thenReturn(movie);

        assertEquals(movieService.updateMovie(movie),true);


    }




    @Test
    public void updateMovieTest2(){
        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(null)
                .build();

        when(movieRepository.getById(1)).thenReturn(null);

        assertEquals(movieService.updateMovie(movie),false);


    }

    @Test
    public void updateTicketStatusTest()
    {
        Ticket ticket=Ticket.builder()
                .transactionId(1)
                .movieName("movieA")
                .availableSeat(95)
                .totalSeat(100)
                .bookedSeat(5)
                .theatreName("theatre1")
                .build();
        Set<Ticket> tickets=new HashSet<>();
        tickets.add(ticket);

        Movie movie=Movie.builder()
                .movieId(1)
                .movieName("movieA")
                .seatAvailable(100)
                .seatBooked(0)
                .totalSeat(100)
                .status("BOOK ASAP")
                .TheatreName("theatre1")
                .ticketList(tickets)
                .build();


        when(ticketRepository.getById(1)).thenReturn(ticket);
        when(movieRepository.getById(1)).thenReturn(movie);
        when(movieRepository.saveAndFlush(any())).thenReturn(movie);

        assertEquals(movieService.updateTicketStatus(1,ticket),true);
    }

}

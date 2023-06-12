/*package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.DataPublisherServiceImpl;
import com.example.demo.service.MovieService;
import com.example.demo.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private DataPublisherServiceImpl dataPublisherService;



    @Test
    public void viewAllMovies() throws Exception {
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

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/moviebooking/all")).andExpect(status().isOk());

    }


}*/

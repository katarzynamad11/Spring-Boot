package com.example.stringboot;

import com.example.stringboot.controllers.RatingController;
import com.example.stringboot.entities.Rate;
import com.example.stringboot.entities.Department;
import com.example.stringboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    public void testAddRating() throws Exception {
        // Przygotowanie danych testowych
        Department department = new Department();
        department.setId(5L); // Podajemy tylko ID departamentu

        Rate rate = new Rate();
        rate.setId(1L); // Zakładając, że ID zostanie ustawione na 1
        rate.setRateValue(4); // Ocena 4
        rate.setDepartment(department);  // Tylko ID departamentu, obiekt `department` ustawiony na odpowiednie ID
        rate.setDateOfRate(LocalDate.of(2024, 12, 14));   // Data oceny
        rate.setComment("Good performance, but could improve in communication.");

        // Mocks dla RatingService
        when(ratingService.addRating(any(Rate.class))).thenReturn(rate);

        // Testowanie POST /api/rating
        mockMvc.perform(post("/api/rating")
                        .contentType("application/json")
                        .content("{\"rateValue\":4,\"department\":{\"id\":5},\"comment\":\"Good performance, but could improve in communication.\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Oczekujemy statusu 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))  // Oczekujemy, że ID będzie 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateValue").value(4))  // Oczekujemy, że ocena to 4
                .andExpect(MockMvcResultMatchers.jsonPath("$.department.id").value(5))  // Oczekujemy, że departament ma ID 5
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Good performance, but could improve in communication."))  // Oczekujemy, że komentarz będzie zgodny
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfRate").value("2024-12-14"));  // Oczekujemy, że data będzie 2024-12-14


        verify(ratingService, times(1)).addRating(any(Rate.class));  // Sprawdzamy, czy metoda addRating() została wywołana raz
    }
}

package com.example.stringboot.controllers;


import com.example.stringboot.entities.Rate;
import com.example.stringboot.entities.repositiories.RatingRepository;
import com.example.stringboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Endpoint do dodawania oceny dla grupy
    @PostMapping("/rating")
    public ResponseEntity<Rate> addRating(@RequestBody Rate rate) {
        try {
            Rate createdRate = ratingService.addRating(rate);
            return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
package com.example.stringboot.services;

import com.example.stringboot.entities.Rate;
import com.example.stringboot.entities.repositiories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // Metoda do dodawania oceny
    public Rate addRating(Rate rate) {
        // Ustawiamy datę oceny na bieżącą datę, jeśli nie jest ustawiona
        if (rate.getDateOfRate() == null) {
            rate.setDateOfRate(LocalDate.now());
        }

        return ratingRepository.save(rate);
    }
}

package com.example.stringboot.entities.repositiories;

import com.example.stringboot.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rate, Long> {

}

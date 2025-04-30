package edu.gmu.swe642.surveyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.gmu.swe642.surveyapp.model.Survey;

/**
 * Repository interface for Survey entity
 * Done by Shreyas Patil
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Custom queries can be added here if needed
}
package edu.gmu.swe642.surveyapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.gmu.swe642.surveyapp.model.Survey;
import edu.gmu.swe642.surveyapp.service.SurveyService;
import jakarta.validation.Valid;

/**
 * REST Controller for Survey entity
 * Done by Shreyas Patil
 */
@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "http://localhost:4200")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * Get all surveys
     */
    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    /**
     * Get survey by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        return surveyService.getSurveyById(id)
                .map(survey -> new ResponseEntity<>(survey, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new survey
     */
    @PostMapping
    public ResponseEntity<Survey> createSurvey(@Valid @RequestBody Survey survey) {
        Survey savedSurvey = surveyService.createSurvey(survey);
        return new ResponseEntity<>(savedSurvey, HttpStatus.CREATED);
    }

    /**
     * Update an existing survey
     */
    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @Valid @RequestBody Survey survey) {
        try {
            Survey updatedSurvey = surveyService.updateSurvey(id, survey);
            return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a survey
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        try {
            surveyService.deleteSurvey(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
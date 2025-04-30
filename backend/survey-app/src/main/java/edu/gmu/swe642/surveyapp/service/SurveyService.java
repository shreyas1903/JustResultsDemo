package edu.gmu.swe642.surveyapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gmu.swe642.surveyapp.model.Survey;
import edu.gmu.swe642.surveyapp.repository.SurveyRepository;

/**
 * Done by Sri Bhuvan, Praneeth Naidu, Ankit Raut, Shreyas Patil
 * Service class for Survey entity operations
 */
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Retrieve all surveys
     */
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    /**
     * Retrieve a specific survey by ID
     */
    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    /**
     * Create a new survey
     */
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    /**
     * Update an existing survey
     */
    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
        
        survey.setFirstName(surveyDetails.getFirstName());
        survey.setLastName(surveyDetails.getLastName());
        survey.setStreetAddress(surveyDetails.getStreetAddress());
        survey.setCity(surveyDetails.getCity());
        survey.setState(surveyDetails.getState());
        survey.setZip(surveyDetails.getZip());
        survey.setPhone(surveyDetails.getPhone());
        survey.setEmail(surveyDetails.getEmail());
        survey.setSurveyDate(surveyDetails.getSurveyDate());
        survey.setLikes(surveyDetails.getLikes());
        survey.setInterestSource(surveyDetails.getInterestSource());
        survey.setRecommendationLikelihood(surveyDetails.getRecommendationLikelihood());
        survey.setComments(surveyDetails.getComments());
        
        return surveyRepository.save(survey);
    }

    /**
     * Delete a survey
     */
    public void deleteSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
        surveyRepository.delete(survey);
    }
}
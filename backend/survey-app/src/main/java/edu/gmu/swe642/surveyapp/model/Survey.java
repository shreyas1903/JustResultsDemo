package edu.gmu.swe642.surveyapp.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a student survey
 * Done by Shreyas Patil
 */
@Entity
@Table(name = "surveys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Street address is required")
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "ZIP code is required")
    @Pattern(regexp = "^\\d{5}$", message = "ZIP code must be 5 digits")
    @Column(name = "zip", nullable = false)
    private String zip;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone number must be in format XXX-XXX-XXXX")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    @Column(name = "survey_date", nullable = false)
    private LocalDate surveyDate;

    @ElementCollection
    @CollectionTable(name = "survey_likes", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "liked_item")
    private List<String> likes;

    @Column(name = "interest_source", nullable = false)
    private String interestSource;

    @Column(name = "recommendation_likelihood", nullable = false)
    private String recommendationLikelihood;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
}
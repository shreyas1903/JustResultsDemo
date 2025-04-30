// Component for displaying all submitted surveys with edit and delete functionality
// Done by Sri Bhuvan, Praneeth Naidu, Ankit Raut, Shreyas Patil
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SurveyService } from '../../services/survey.service';
import { Survey } from '../../models/survey.model';

@Component({
  selector: 'app-survey-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './survey-list.component.html',
  styleUrl: './survey-list.component.css'
})
export class SurveyListComponent implements OnInit {
  surveys: Survey[] = [];
  loading = true;
  error = false;

  constructor(private surveyService: SurveyService) { }

  ngOnInit(): void {
    this.loadSurveys();
  }

  loadSurveys(): void {
    this.loading = true;
    this.error = false;
    
    this.surveyService.getAllSurveys().subscribe({
      next: (data: Survey[]) => {
        this.surveys = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error fetching surveys:', error);
        this.error = true;
        this.loading = false;
      }
    });
  }

  deleteSurvey(id: number): void {
    if (confirm('Are you sure you want to delete this survey?')) {
      this.surveyService.deleteSurvey(id).subscribe({
        next: () => {
          this.surveys = this.surveys.filter(survey => survey.id !== id);
        },
        error: (error) => {
          console.error('Error deleting survey:', error);
        }
      });
    }
  }
}
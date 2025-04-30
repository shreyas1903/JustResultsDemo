// Component implementing the survey form with validation and CRUD operations
// Done by Sri Bhuvan, Praneeth Naidu, Ankit Raut, Shreyas Patil 
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { SurveyService } from '../../services/survey.service';
import { Survey } from '../../models/survey.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-survey-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './survey-form.component.html',
  styleUrl: './survey-form.component.css'
})
export class SurveyFormComponent implements OnInit {
  surveyForm!: FormGroup;
  isEditMode = false;
  surveyId: number | null = null;
  submitting = false;
  
  likeOptions = [
    { value: 'students', label: 'Students' },
    { value: 'location', label: 'Location' },
    { value: 'campus', label: 'Campus' },
    { value: 'atmosphere', label: 'Atmosphere' },
    { value: 'dorms', label: 'Dorm Rooms' },
    { value: 'sports', label: 'Sports' }
  ];
  
  interestOptions = [
    { value: 'friends', label: 'Friends' },
    { value: 'television', label: 'Television' },
    { value: 'internet', label: 'Internet' },
    { value: 'other', label: 'Other' }
  ];
  
  recommendOptions = [
    { value: 'very-likely', label: 'Very Likely' },
    { value: 'likely', label: 'Likely' },
    { value: 'unlikely', label: 'Unlikely' }
  ];

  constructor(
    private fb: FormBuilder,
    private surveyService: SurveyService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.surveyId = +params['id'];
        this.loadSurveyData();
      }
    });
  }

  initForm(): void {
    this.surveyForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      streetAddress: ['', [Validators.required]],
      city: ['', [Validators.required]],
      state: ['', [Validators.required]],
      zip: ['', [Validators.required, Validators.pattern('^[0-9]{5}$')]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{3}-[0-9]{3}-[0-9]{4}$')]],
      email: ['', [Validators.required, Validators.email]],
      surveyDate: [new Date().toISOString().substring(0, 10), [Validators.required]],
      likes: this.fb.array([], [Validators.required, Validators.minLength(1)]),
      interestSource: ['', [Validators.required]],
      recommendationLikelihood: ['', [Validators.required]],
      comments: ['']
    });
  }
  
  get likesArray(): FormArray {
    return this.surveyForm.get('likes') as FormArray;
  }

  onCheckboxChange(event: any): void {
    const likes = this.surveyForm.get('likes') as FormArray;
    
    if (event.target.checked) {
      likes.push(this.fb.control(event.target.value));
    } else {
      const index = likes.controls.findIndex(x => x.value === event.target.value);
      if (index >= 0) {
        likes.removeAt(index);
      }
    }
  }

  isLikeSelected(value: string): boolean {
    const likes = this.surveyForm.get('likes') as FormArray;
    return likes.controls.some(control => control.value === value);
  }

  loadSurveyData(): void {
    if (this.surveyId) {
      this.surveyService.getSurveyById(this.surveyId).subscribe({
        next: (survey: Survey) => {
          // Reset likes array
          while (this.likesArray.length) {
            this.likesArray.removeAt(0);
          }
          
          // Add each like to the form array
          survey.likes.forEach(like => {
            this.likesArray.push(this.fb.control(like));
          });
          
          // Update the rest of the form
          this.surveyForm.patchValue({
            firstName: survey.firstName,
            lastName: survey.lastName,
            streetAddress: survey.streetAddress,
            city: survey.city,
            state: survey.state,
            zip: survey.zip,
            phone: survey.phone,
            email: survey.email,
            surveyDate: survey.surveyDate,
            interestSource: survey.interestSource,
            recommendationLikelihood: survey.recommendationLikelihood,
            comments: survey.comments
          });
        },
        error: (error) => {
          console.error('Error loading survey:', error);
          this.router.navigate(['/surveys']);
        }
      });
    }
  }

  onSubmit(): void {
    if (this.surveyForm.invalid) {
      // Mark all fields as touched to trigger validation messages
      this.markFormGroupTouched(this.surveyForm);
      return;
    }

    this.submitting = true;
    const surveyData: Survey = this.surveyForm.value;

    if (this.isEditMode && this.surveyId) {
      this.surveyService.updateSurvey(this.surveyId, surveyData).subscribe({
        next: () => {
          this.router.navigate(['/surveys']);
        },
        error: (error) => {
          console.error('Error updating survey:', error);
          this.submitting = false;
        }
      });
    } else {
      this.surveyService.createSurvey(surveyData).subscribe({
        next: () => {
          this.router.navigate(['/surveys']);
        },
        error: (error) => {
          console.error('Error creating survey:', error);
          this.submitting = false;
        }
      });
    }
  }

  // Helper method to mark all controls in a form group as touched
  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  resetForm(): void {
    this.surveyForm.reset();
    
    // Reset the likes array
    while (this.likesArray.length) {
      this.likesArray.removeAt(0);
    }
    
    // Set default date
    this.surveyForm.patchValue({
      surveyDate: new Date().toISOString().substring(0, 10)
    });
  }
}
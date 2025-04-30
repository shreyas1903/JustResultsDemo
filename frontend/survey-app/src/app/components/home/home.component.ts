// Component for the welcome page with navigation cards to survey form and list
//Done by Sri Bhuvan, Praneeth Naidu, Ankit Raut, Shreyas Patil
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
}
// Interface defining the Survey data structure for frontend-backend communication
// Done by Shreyas Patil
export interface Survey {
  id?: number;
  firstName: string;
  lastName: string;
  streetAddress: string;
  city: string;
  state: string;
  zip: string;
  phone: string;
  email: string;
  surveyDate: string;
  likes: string[];
  interestSource: string;
  recommendationLikelihood: string;
  comments?: string;
}

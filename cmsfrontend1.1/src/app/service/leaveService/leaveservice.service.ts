import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})


export class LeaveserviceService {

  private apiUrl = 'http://localhost:8765/LEAVEMANAGEMENTSYSTEM/leave'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getLeaveRequestsByEmployee(employeeId: number): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(`${this.apiUrl}/${employeeId}`);
  }
}

export interface LeaveRequest {
  id: number;
  employeeId: number;
  startDate: string;
  endDate: string;
  reason: string;
  status: string;
}
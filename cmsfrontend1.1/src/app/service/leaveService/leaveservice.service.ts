import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeaveRequest } from '../../dto/leaverequest';

@Injectable({
  providedIn: 'root'
})
export class LeaveserviceService {

  private apiUrl = 'http://localhost:8765/LEAVEMANAGEMENTSYSTEM/leave'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getLeaveRequestsByEmployee(employeeId: number):any {
    return this.http.get(`${this.apiUrl}/employee/${employeeId}`);
  }


  // getLeaveRequestsByEmployee(employeeId: number): Observable<LeaveRequest[]> {
  //   return this.http.get<LeaveRequest[]>(`${this.apiUrl}/employee/${employeeId}`);
  // }

  getAllleaves(): any {
    return this.http.get(`${this.apiUrl}/all`);
  }


}


import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../service/leaveService/leaveservice.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LeaveRequest } from '../dto/leaverequest';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './test.component.html',
  styleUrl: './test.component.css'
})
export class TestComponent  implements OnInit{
//  leaveRequests: LeaveRequest[] = [];
 leaveRequests: LeaveRequest[] = [];
 

  employeeId: number | undefined; 

  constructor(private leaveService: LeaveserviceService , private route : ActivatedRoute) {}

  // ngOnInit(): void {
  //   this.loadLeaveRequests(this.employeeId);
  //   // this.getAllleavescomponent();
  // }



  ngOnInit(): void {
    // Get the employeeId from the route parameters
    this.route.params.subscribe((params) => {
      this.employeeId = +params['employeeId'];  // Convert to number
      this.loadLeaveRequests(this.employeeId);
    });
  }




  loadLeaveRequests(employeeId: number): void {//getleaves by id
    this.leaveService.getLeaveRequestsByEmployee(employeeId).subscribe({
      next: (data: any) => {
        this.leaveRequests = Array.isArray(data) ? data : []; // Ensure data is an array
      },
      error: (error: any) => {
        console.error('Error fetching leave requests:', error);
      },
    });
  }
  


  getAllleavescomponent(): void { // getall leaves
    this.leaveService.getAllleaves().subscribe(
      (data: any) => {this.leaveRequests = data}
    );
  }


}

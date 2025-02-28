import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import {  LeaveserviceService } from '../../service/leaveService/leaveservice.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { LeaveRequest } from '../../dto/leaverequest';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-leaveform',
  standalone: true,
  imports: [NavbarComponent, SidebarComponent, CommonModule],
  templateUrl: './leaveform.component.html',
  styleUrl: './leaveform.component.css'
})
export class LeaveformComponent implements OnInit {
 
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
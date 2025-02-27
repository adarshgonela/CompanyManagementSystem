import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import {  LeaveserviceService } from '../../service/leaveService/leaveservice.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { LeaveRequest } from '../../dto/leaverequest';

@Component({
  selector: 'app-leaveform',
  standalone: true,
  imports: [NavbarComponent, SidebarComponent, CommonModule],
  templateUrl: './leaveform.component.html',
  styleUrl: './leaveform.component.css'
})
export class LeaveformComponent implements OnInit {
  leaveRequests: LeaveRequest[] = [];
  employeeId: number = 1; // Replace with the actual employee ID or get it dynamically

  constructor(private leaveService: LeaveserviceService) {}

  ngOnInit(): void {
    this.loadLeaveRequests();
  }

  loadLeaveRequests(): void {
    this.leaveService.getLeaveRequestsByEmployee(this.employeeId).subscribe(
      (data: LeaveRequest[]) => {
        this.leaveRequests = data;
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching leave requests', error);
      }
    );
  }
}
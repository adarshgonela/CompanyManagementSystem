import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../service/leaveService/leaveservice.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LeaveRequest } from '../dto/leaverequest';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './test.component.html',
  styleUrl: './test.component.css'
})
export class TestComponent  implements OnInit{
//  leaveRequests: LeaveRequest[] = [];
 leaveRequests: any = [];
 

  employeeId: number = 1; // Replace with the actual employee ID or get it dynamically

  constructor(private leaveService: LeaveserviceService) {}

  ngOnInit(): void {
    // this.loadLeaveRequests(this.employeeId);
    this.getAllleavescomponent();
  }

  // loadLeaveRequests(): void {
  //   this.leaveService.getLeaveRequestsByEmployee(this.employeeId).subscribe(
  //     (data: any[]) => {
  //       this.leaveRequests = data;
  //       // console.log(this.leaveRequests);
  //       console.log(Array.isArray(this.leaveRequests)); // Should return true
  //     },
  //     (error: HttpErrorResponse) => {
  //       console.error('Error fetching leave requests', error);
  //     }
  //   );
  // }




  

  loadLeaveRequests(employeeId: number): void {
    this.leaveService.getLeaveRequestsByEmployee(employeeId).subscribe(
       (data: any[]) => {this.leaveRequests = data}
      );
  }
  


  getAllleavescomponent(): void {
    this.leaveService.getAllleaves().subscribe(
      (data: any) => {this.leaveRequests = data}
    );
  }


}

import { Component, OnInit } from '@angular/core';
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { EmpdetailsService } from '../../service/employee/empdetails.service';
import { Employee } from '../../dto/Employee';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ActivatedRoute, Router, RouterModule } from '@angular/router'
@Component({
  selector: 'app-empdashboard',
  standalone: true,
  imports: [SidebarComponent, NavbarComponent,CommonModule, FormsModule, RouterModule],
  templateUrl: './empdashboard.component.html',
  styleUrls: ['./empdashboard.component.css']
})
export class EmpdashboardComponent implements OnInit {
employee: Employee | null = null;
  isLoading = false;
  error: string | null = null;
  employeeId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmpdetailsService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.employeeId = Number(id);
        if (!isNaN(this.employeeId)) {
          this.loadEmployee(this.employeeId);
        } else {
          this.error = 'Invalid employee ID in URL';
        }
      }
    });
  }

  loadEmployee(id: number): void {
    this.isLoading = true;
    this.error = null;

    this.employeeService.getEmployeeById(id).subscribe({
      next: (employee) => {
        this.employee = employee;
        this.isLoading = false;
      },
      error: (error) => {
        this.error = 'Employee not found';
        this.isLoading = false;
        console.error('Error fetching employee:', error);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/employees']);
  }
}

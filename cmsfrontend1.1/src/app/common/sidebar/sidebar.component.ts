import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../common/navbar/navbar.component';
import { EmpdetailsService } from '../../service/employee/empdetails.service';
import { Employee } from '../../dto/Employee';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FooterComponent } from '../../common/footer/footer.component';
import { HeaderComponent } from '../../common/header/header.component';
import { response } from 'express';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './sidebar.component.html',
  // styleUrl: './sidebar.component.css'
  styleUrl: '../../../assets/css/style.css',
})
export class SidebarComponent {
  employees: Employee[] = [];
  //  employees: Employee | null = null;
  isLoading = false;
  error: string | null = null;
  employeeId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmpdetailsService
  ) {}

  ngOnInit(): void {
    this.loadEmployeeFromUrl();
    const userId = Number(sessionStorage.getItem('user-id'));//testing
  }

  ////////////////////////////////////////////////////////////////

  loadEmployeeFromUrl(): void {
    this.isLoading = true;
    this.error = null;

    // Get ID from route parameters
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');

      if (id) {
        const employeeId = Number(id);

        if (isNaN(employeeId)) {
          this.error = 'Invalid employee ID';
          this.isLoading = false;
          return;
        }

        this.getEmployeeById(employeeId);
      } else {
        this.error = 'No employee ID provided';
        this.isLoading = false;
      }
    });
  }

  // Method to fetch employee by ID
  getEmployeeById(id: number): void {
    this.isLoading = true;
    this.error = null;

    this.employeeService.getEmployeeById(id).subscribe({
      next: (data: Employee) => {
        this.employees = [data]; // Wrap the single employee in an array
        this.isLoading = false;
      },
      error: (error: any) => {
        this.error = 'Employee not found';
        this.isLoading = false;
        console.error('Error fetching employee:', error);
      },
    });
  }

  // Method to reload the same employee
  loadEmployee(id: number): void {
    this.getEmployeeById(id);
  }

  // Navigate back to employee list
  goBack(): void {
    this.router.navigate(['/employees']);
  }
}

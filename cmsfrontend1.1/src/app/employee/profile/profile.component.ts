import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs';

import { NavbarComponent } from "../../common/navbar/navbar.component";
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";
import { Employee } from '../../dto/Employee';
import { EmpdetailsService } from '../../service/employee/empdetails.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    HeaderComponent,
    ReactiveFormsModule // <-- Import ReactiveFormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  employees: Employee[] = [];
  isLoading = false;
  error: string | null = null;
  employeeId: number | null = null;
  selectedEmployee: Employee | null = null;
  employeeForm!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmpdetailsService,
    private fb: FormBuilder // <-- Inject FormBuilder
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadEmployeeFromUrl();
  }

  // Initializing the form with form controls
  initializeForm(): void {
    this.employeeForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      gender: [''],
      phone: ['', Validators.pattern('^[0-9]*$')],
      address: ['']
    });
  }

  loadEmployeeFromUrl(): void {
    this.isLoading = true;
    this.error = null;

    this.route.paramMap.subscribe(params => {
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

  getEmployeeById(id: number): void {
    this.isLoading = true;
    this.error = null;

    this.employeeService.getEmployeeById(id).subscribe({
      next: (data: Employee) => {
        this.selectedEmployee = data;
        this.employees = [data];
        // Populate the form with fetched data
        this.employeeForm.patchValue(this.selectedEmployee);
        this.isLoading = false;
      },
      error: (error: any) => {
        this.error = 'Employee not found';
        this.isLoading = false;
        console.error('Error fetching employee:', error);
      }
    });
  }

  loadEmployee(id: number): void {
    this.getEmployeeById(id);
  }

  goBack(): void {
    this.router.navigate(['/employees']);
  }

  updateEmployeeProfile(): void {
    if (this.employeeForm.invalid || !this.selectedEmployee) {
      console.error('Form is invalid or no employee selected');
      return;
    }

    this.isLoading = true;
    this.error = null;

    const updatedData = {
      ...this.selectedEmployee,
      ...this.employeeForm.value
    };

    this.employeeService.changeEmployeeProfile(this.selectedEmployee.id, updatedData)
      .pipe(finalize(() => {
        this.isLoading = false;
      }))
      .subscribe({
        next: (updatedEmployee: Employee) => {
          this.selectedEmployee = updatedEmployee;
          this.employees = [updatedEmployee]; // Update the local array
          console.log('Employee profile updated successfully');
        },
        error: (error: any) => {
          this.error = 'Failed to update employee profile';
          console.error('Error updating employee:', error);
        }
      });
  }

  resetForm(): void {
    this.employeeForm.reset();
    this.selectedEmployee = null;
    this.error = null;
  }
}
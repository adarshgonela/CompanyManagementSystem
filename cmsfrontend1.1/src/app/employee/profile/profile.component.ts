import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs/operators';

// Components
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";

// Services & Models
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
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  // Dependencies (Angular 18 style)
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private employeeService = inject(EmpdetailsService);
  private destroyRef = inject(DestroyRef);

  // State
  employees: Employee[] = []; // Kept for compatibility if needed
  selectedEmployee: Employee | null = null;
  employeeForm!: FormGroup;
  
  // UI State
  isLoading = false;
  isImageLoading = false;
  error: string | null = null;
  imageError: string | null = null;
  showDebug = false;

  // Image Handling
  currentImageUrl: string | null = null;
  uploadedFile: File | null = null;

  private readonly dummyImageServices = [
    'https://i.pravatar.cc/300?img=',
    'https://api.dicebear.com/7.x/avataaars/svg?seed=',
    'https://robohash.org/'
  ];

  ngOnInit(): void {
    this.initializeForm();
    this.loadEmployeeFromUrl();
  }

  private initializeForm(): void {
    this.employeeForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      // Note: If email/empid are disabled in UI, use getRawValue() on submit
      email: [''], 
      empid: [''],
      gender: [''],
      phone: ['', [Validators.pattern('^[0-9]{10,15}$')]],
      address: ['']
    });
  }

  private loadEmployeeFromUrl(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        const id = params.get('id');
        if (id) {
          this.getEmployeeById(Number(id));
        } else {
          this.error = 'No employee ID provided';
        }
      });
  }

  getEmployeeById(id: number): void {
    this.isLoading = true;
    this.error = null;

    this.employeeService.getEmployeeById(id)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe({
        next: (data: Employee) => {
          this.selectedEmployee = data;
          this.employees = [data]; // optional, depends on your sidebar reqs
          this.currentImageUrl = data.image || null;
          this.populateForm(data);
        },
        error: (err) => {
          this.error = 'Failed to load employee details.';
          console.error(err);
        }
      });
  }

  populateForm(employee: Employee): void {
    this.employeeForm.patchValue({
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      empid: employee.empid,
      gender: employee.gender,
      phone: employee.phone,
      address: employee.address
    });
    
    // Mark as pristine so the "Update" button is disabled initially
    this.employeeForm.markAsPristine(); 
  }

  // --- Image Handling ---

  getProfileImage(): string {
    return this.currentImageUrl || this.selectedEmployee?.image || 'assets/images/default-avatar.png';
  }

  onImageUpload(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];

      // Validation
      if (!['image/jpeg', 'image/png', 'image/jpg'].includes(file.type)) {
        this.imageError = 'Only PNG and JPG allowed.';
        return;
      }
      if (file.size > 5 * 1024 * 1024) {
        this.imageError = 'File size must be less than 5MB.';
        return;
      }

      this.uploadedFile = file;
      this.imageError = null;

      // Preview
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.currentImageUrl = e.target.result;
        this.employeeForm.markAsDirty(); // Enable update button
      };
      reader.readAsDataURL(file);
    }
  }

  generateDummyImage(): void {
    this.isImageLoading = true;
    const seed = Math.random().toString(36).substring(7);
    const serviceIndex = Math.floor(Math.random() * this.dummyImageServices.length);
    const url = `${this.dummyImageServices[serviceIndex]}${seed}`;

    // Preload to ensure validity
    const img = new Image();
    img.src = url;
    img.onload = () => {
      this.currentImageUrl = url;
      this.isImageLoading = false;
      this.employeeForm.markAsDirty();
    };
    img.onerror = () => {
      this.isImageLoading = false;
      this.imageError = "Could not generate dummy image.";
    };
  }

  // --- Update Logic ---

  updateEmployeeProfile(): void {
    if (this.employeeForm.invalid || !this.selectedEmployee) return;

    this.isLoading = true;
    this.error = null;

    // 1. Prepare the payload
    // use getRawValue() to include disabled fields (like email/id)
    const formValues = this.employeeForm.getRawValue();

    // 2. Merge existing data with form data to ensure a COMPLETE object is sent
    const updatedEmployee: Employee = {
      ...this.selectedEmployee, // Original data
      ...formValues,            // Overwrite with form changes
    };

    // 3. Handle Image Logic
    // If a Base64 string is generated (via dummy or preview), attach it.
    // NOTE: If your backend expects a specific 'file' object, logic changes here.
    if (this.currentImageUrl) {
        updatedEmployee.image = this.currentImageUrl;
    }

    // 4. Send Request
    this.employeeService.changeEmployeeProfile(this.selectedEmployee.id, updatedEmployee)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe({
        next: (response: Employee) => {
          this.selectedEmployee = response;
          this.populateForm(response); // Reset form state to pristine
          this.uploadedFile = null;
          
          // Simple success feedback
          alert('Profile Updated Successfully!');
        },
        error: (err) => {
          console.error('Update failed', err);
          this.error = 'Failed to update profile. Please try again.';
        }
      });
  }

  // Helper helper for debugging in template
  getChangedFields(): any {
    const controls = this.employeeForm.controls;
    const changes: any = {};
    for (const name in controls) {
      if (controls[name].dirty) {
        changes[name] = controls[name].value;
      }
    }
    return changes;
  }

  resetForm(): void {
    if (this.selectedEmployee) {
      this.populateForm(this.selectedEmployee);
      this.currentImageUrl = this.selectedEmployee.image || null;
      this.imageError = null;
    }
  }

  goBack(): void {
    this.router.navigate(['/employees']);
  }
}
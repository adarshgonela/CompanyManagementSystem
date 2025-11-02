import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

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
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  employees: Employee[] = [];
  isLoading = false;
  isImageLoading = false;
  error: string | null = null;
  imageError: string | null = null;
  employeeId: number | null = null;
  selectedEmployee: Employee | null = null;
  employeeForm!: FormGroup;
  originalFormData: any = {};
  uploadedImage: File | null = null;
  currentImageUrl: string | null = null;
  showDebug = false;

  // Dummy image service URLs
  private readonly dummyImageServices = [
    'https://i.pravatar.cc/300?img=',
    'https://i.pravatar.cc/300?u=',
    'https://robohash.org/',
    'https://api.dicebear.com/6.x/avataaars/svg?seed=',
    'https://api.dicebear.com/6.x/personas/svg?seed='
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmpdetailsService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadEmployeeFromUrl();
  }

  initializeForm(): void {
    this.employeeForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      gender: [''],
      phone: ['', [Validators.pattern('^[0-9]{10,15}$')]],
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
        
        // Populate form with employee data
        this.populateForm(data);
        
        // Store original form data for comparison
        this.originalFormData = { ...this.employeeForm.value };
        
        // Set initial image
        if (this.selectedEmployee.image) {
          this.currentImageUrl = this.selectedEmployee.image;
        }
        
        this.isLoading = false;
        console.log('Employee loaded:', data);
        console.log('Form initialized with:', this.employeeForm.value);
      },
      error: (error: any) => {
        this.error = 'Employee not found';
        this.isLoading = false;
        console.error('Error fetching employee:', error);
      }
    });
  }

  populateForm(employee: Employee): void {
    this.employeeForm.patchValue({
      firstName: employee.firstName || '',
      lastName: employee.lastName || '',
      gender: employee.gender || '',
      phone: employee.phone || '',
      address: employee.address || ''
    });
  }

  getProfileImage(): string {
    if (this.currentImageUrl) {
      return this.currentImageUrl;
    }
    return this.selectedEmployee?.image || 'assets/images/default-avatar.png';
  }

  onImageUpload(event: any): void {
    const file = event.target.files[0];
    if (file) {
      // Validate file type
      const validTypes = ['image/jpeg', 'image/jpg', 'image/png'];
      if (!validTypes.includes(file.type)) {
        this.imageError = 'Please select a valid image file (JPEG, JPG, PNG)';
        return;
      }

      // Validate file size (max 5MB)
      if (file.size > 5 * 1024 * 1024) {
        this.imageError = 'Image size should be less than 5MB';
        return;
      }

      this.isImageLoading = true;
      this.imageError = null;
      this.uploadedImage = file;

      // Create preview URL
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.currentImageUrl = e.target.result;
        this.isImageLoading = false;
        
        // Mark form as dirty since image changed
        this.employeeForm.markAsDirty();
      };
      reader.onerror = () => {
        this.imageError = 'Failed to load image';
        this.isImageLoading = false;
      };
      reader.readAsDataURL(file);
    }
  }

  generateDummyImage(): void {
    this.isImageLoading = true;
    this.imageError = null;
    
    // Select random service
    const serviceIndex = Math.floor(Math.random() * this.dummyImageServices.length);
    const baseUrl = this.dummyImageServices[serviceIndex];
    
    // Generate unique seed based on employee data or random
    const seed = this.selectedEmployee?.id 
      ? `${this.selectedEmployee.id}-${Date.now()}`
      : `employee-${Math.random().toString(36).substr(2, 9)}`;
    
    const dummyImageUrl = `${baseUrl}${seed}`;
    
    // Create image element to test if URL is valid
    const img = new Image();
    img.onload = () => {
      this.currentImageUrl = dummyImageUrl;
      this.isImageLoading = false;
      this.employeeForm.markAsDirty();
      console.log('Dummy image generated:', dummyImageUrl);
    };
    img.onerror = () => {
      // If one service fails, try another
      this.fallbackDummyImage();
    };
    img.src = dummyImageUrl;
  }

  private fallbackDummyImage(): void {
    // Use a reliable fallback service
    const fallbackUrl = `https://i.pravatar.cc/300?img=${Math.floor(Math.random() * 70) + 1}`;
    
    const img = new Image();
    img.onload = () => {
      this.currentImageUrl = fallbackUrl;
      this.isImageLoading = false;
      this.employeeForm.markAsDirty();
    };
    img.onerror = () => {
      this.imageError = 'Failed to generate dummy image';
      this.isImageLoading = false;
    };
    img.src = fallbackUrl;
  }

  updateEmployeeProfile(): void {
    if (this.employeeForm.invalid || !this.selectedEmployee) {
      console.error('Form is invalid or no employee selected');
      return;
    }

    // Get only the changed fields
    const changedData = this.getChangedFields();
    
    // Include image URL if it was changed
    if (this.currentImageUrl && this.currentImageUrl !== this.selectedEmployee.image) {
      changedData.image = this.currentImageUrl;
    }
    
    // If no fields have changed, don't make the API call
    if (Object.keys(changedData).length === 0) {
      console.log('No changes detected');
      this.error = 'No changes to update';
      setTimeout(() => {
        this.error = null;
      }, 3000);
      return;
    }

    this.isLoading = true;
    this.error = null;

    console.log('Sending update with changed data:', changedData);

    this.employeeService.changeEmployeeProfile(this.selectedEmployee.id, changedData)
      .pipe(finalize(() => {
        this.isLoading = false;
      }))
      .subscribe({
        next: (updatedEmployee: Employee) => {
          this.selectedEmployee = updatedEmployee;
          this.employees = [updatedEmployee];
          
          // Update the original form data with the new values
          this.originalFormData = { ...this.employeeForm.value };
          
          // Update image reference
          if (updatedEmployee.image) {
            this.currentImageUrl = updatedEmployee.image;
          }
          
          console.log('Employee profile updated successfully:', updatedEmployee);
          
          // Show success message
          this.error = 'Profile updated successfully!';
          setTimeout(() => {
            this.error = null;
          }, 3000);
        },
        error: (error: any) => {
          this.error = 'Failed to update employee profile';
          console.error('Error updating employee:', error);
        }
      });
  }

  public getChangedFields(): any {
    const currentFormData = this.employeeForm.value;
    const changedFields: any = {};

    Object.keys(currentFormData).forEach(key => {
      const currentValue = currentFormData[key];
      const originalValue = this.originalFormData[key];
      
      // Handle null/undefined comparison and string trimming for consistency
      const currentVal = typeof currentValue === 'string' ? currentValue.trim() : currentValue;
      const originalVal = typeof originalValue === 'string' ? originalValue.trim() : originalValue;
      
      if (currentVal !== originalVal) {
        changedFields[key] = currentValue; // Use original value (not trimmed) for API
      }
    });

    console.log('Changed fields detected:', changedFields);
    return changedFields;
  }

  resetForm(): void {
    if (this.selectedEmployee) {
      // Reset to original employee data
      this.populateForm(this.selectedEmployee);
      this.currentImageUrl = this.selectedEmployee.image || null;
      this.uploadedImage = null;
      this.imageError = null;
      this.error = null;
      this.employeeForm.markAsPristine();
      
      // Update original form data
      this.originalFormData = { ...this.employeeForm.value };
    } else {
      this.employeeForm.reset();
      this.selectedEmployee = null;
      this.currentImageUrl = null;
      this.error = null;
    }
  }

  loadEmployee(id: number): void {
    this.getEmployeeById(id);
  }

  goBack(): void {
    this.router.navigate(['/employees']);
  }
}
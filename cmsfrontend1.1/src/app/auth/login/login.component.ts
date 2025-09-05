import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;
  showPassword = false;
  isBrowser: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: any
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    this.initializeForm();
    
    // Only check login status in browser environment and if we're in browser
    if (this.isBrowser) {
      // Use setTimeout to avoid ExpressionChangedAfterItHasBeenCheckedError
      setTimeout(() => {
        if (this.authService.isLoggedIn()) {
          this.router.navigate(['/dashboard']);
        }
      });
    }
  }

  private initializeForm(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      rememberMe: [false]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      const { username, password, rememberMe } = this.loginForm.value;

      this.authService.login(username, password)
        .pipe(
          catchError((error: Error) => {
            this.errorMessage = error.message || 'Login failed. Please check your credentials and try again.';
            return of(null);
          }),
          finalize(() => {
            this.isLoading = false;
          })
        )
        .subscribe((response: any) => {
          if (response) {
            this.handleSuccessfulLogin(response, rememberMe);
          }
        });
    } else {
      this.markFormGroupTouched();
    }
  }

  private handleSuccessfulLogin(response: any, rememberMe: boolean): void {
    if (response.token) {
      // Only store if we're in browser environment
      if (this.isBrowser) {
        if (rememberMe) {
          localStorage.setItem('authToken', response.token);
          if (response.username || response.email) {
            const userData = {
              username: response.username,
              email: response.email,
              roles: response.roles || []
            };
            localStorage.setItem('user', JSON.stringify(userData));
          }
        } else {
          sessionStorage.setItem('authToken', response.token);
          if (response.username || response.email) {
            const userData = {
              username: response.username,
              email: response.email,
              roles: response.roles || []
            };
            sessionStorage.setItem('user', JSON.stringify(userData));
          }
        }
      }

      console.log('Login successful, navigating to dashboard...');
      this.router.navigate(['/dashboard']);
    } else {
      this.errorMessage = 'Invalid response from server: No token received';
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      if (control) {
        control.markAsTouched();
      }
    });
  }

  togglePasswordVisibility(): void {
    if (this.isBrowser) {
      this.showPassword = !this.showPassword;
    }
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  get rememberMe() {
    return this.loginForm.get('rememberMe');
  }

  getUsernameError(): string {
    if (this.username?.errors?.['required']) {
      return 'Username is required';
    } else if (this.username?.errors?.['minlength']) {
      return 'Username must be at least 3 characters';
    }
    return '';
  }

  getPasswordError(): string {
    if (this.password?.errors?.['required']) {
      return 'Password is required';
    } else if (this.password?.errors?.['minlength']) {
      return 'Password must be at least 6 characters';
    }
    return '';
  }

  onInputChange(): void {
    if (this.errorMessage) {
      this.errorMessage = null;
    }
  }
}
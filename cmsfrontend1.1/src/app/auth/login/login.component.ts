import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { JwtResponse } from '../../dto/JwtResponse';
import { Token, TokenType } from '@angular/compiler';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
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
    @Inject(PLATFORM_ID) private platformId: any,
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
      rememberMe: [false],
    });
  }

  // onSubmit(): void {
  //   if (this.loginForm.valid) {
  //     this.isLoading = true;
  //     this.errorMessage = null;

  //     const { username, password, rememberMe } = this.loginForm.value;

  //     this.authService.login(username, password)
  //       .pipe(
  //         catchError((error: Error) => {
  //           this.errorMessage = error.message || 'Login failed. Please check your credentials and try again.';
  //           return of(null);
  //         }),
  //         finalize(() => {
  //           this.isLoading = false;
  //         })
  //       )
  //       .subscribe((response: any) => {
  //         if (response) {
  //           this.handleSuccessfulLogin(response, rememberMe);
  //         }
  //       });
  //   } else {
  //     this.markFormGroupTouched();
  //   }
  // }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      const { username, password, rememberMe } = this.loginForm.value;

      this.authService
        .login(username, password)
        .pipe(
          catchError((error: Error) => {
            this.errorMessage =
              error.message ||
              'Login failed. Please check your credentials and try again.';
            return of(null);
          }),
          finalize(() => {
            this.isLoading = false;
          }),
        )
        .subscribe((response: JwtResponse | null) => {
          if (response) {
            // 🔹 Print full JWT response
            // console.log('JWT Response:', response);

            // 🔹 Print individual fields
            // console.log('Token:', response.token);
            // console.log('User ID:', response.id);
            // console.log('Username:', response.username);
            // console.log('Email:', response.email);
            // console.log('Roles:', response.roles);

            this.handleSuccessfulLogin(response, rememberMe);
          }
        });
    } else {
      this.markFormGroupTouched();
    }
  }

  // private handleSuccessfulLogin(response: any, rememberMe: boolean): void {
  //   // if (response.token) {
  //   //   // Only store if we're in browser environment
  //   //   if (this.isBrowser) {
  //   //     if (rememberMe) {
  //   //       localStorage.setItem('authToken', response.token);
  //   //       if (response.username || response.email) {
  //   //         const userData = {
  //   //           username: response.username,
  //   //           email: response.email,
  //   //           roles: response.roles || []
  //   //         };
  //   //         localStorage.setItem('user', JSON.stringify(userData));
  //   //       }
  //   //     } else {
  //   //       sessionStorage.setItem('authToken', response.token);
  //   //       if (response.username || response.email) {
  //   //         const userData = {
  //   //           username: response.username,
  //   //           email: response.email,
  //   //           roles: response.roles || []
  //   //         };
  //   //         sessionStorage.setItem('user', JSON.stringify(userData));
  //   //       }
  //   //     }
  //   //   }

  //   //   console.log('Login successful, navigating to dashboard...');
  //   //
  //   // } else {
  //   //   this.errorMessage = 'Invalid response from server: No token received';
  //   // }
  //   // Save token (you likely already do this)
  //   sessionStorage.setItem('auth-token', response.token);
  //    sessionStorage.setItem('auth-token', response.token);

  //   // ✅ Save user id
  //   sessionStorage.setItem('user-id', response.id.toString());

  //   // Optional: save other user data
  //   sessionStorage.setItem('username', response.username);
  //   sessionStorage.setItem('roles', JSON.stringify(response.roles));
  //   const userId = Number(sessionStorage.getItem('user-id'));
  //   console.log(userId+" hey i am userId");
  //   this.router.navigate(['/empdashboard', userId]);
  // }

  private handleSuccessfulLogin(response: any, rememberMe: boolean): void {
    // Validate response
    if (!response || !response.accessToken) {
      this.errorMessage = 'Invalid response from server: No token received';
      console.error(this.errorMessage, response);
      return;
    }

    // Ensure sessionStorage is available
    if (typeof window !== 'undefined' && sessionStorage) {
      // Save auth token
      sessionStorage.setItem('token', response.accessToken);

      // Save user ID
      if (response.id !== undefined && response.id !== null) {
        sessionStorage.setItem('user-id', response.id.toString());
      }

      // Save optional user data as a single object
      const userData = {
        username: response.username || '',
        roles: response.roles || [],
        TokenType: response.type,
        Token: response.accessToken,
      };
      sessionStorage.setItem('user', JSON.stringify(userData));

      // Debugging
      const userId = Number(sessionStorage.getItem('user-id'));
      console.log(`Login successful. User ID: ${userId}`);

      // Navigate to employee dashboard
      this.router.navigate(['/empdashboard', userId]);
    } else {
      console.warn('Session storage is not available in this environment.');
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.loginForm.controls).forEach((key) => {
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

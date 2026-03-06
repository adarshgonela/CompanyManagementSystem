import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { JwtResponse } from '../dto/JwtResponse';
import { catchError, tap } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:9000/api/auth';

  constructor(
    private http: HttpClient, 
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: any
  ) {}

 login(username: string, password: string): Observable<JwtResponse> {
  const loginRequest: LoginRequest = { username, password };
  
  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  
  console.log('Auth service: Sending login request', loginRequest);
  
  return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, loginRequest, httpOptions).pipe(
    tap(response => {
      console.log('Auth service: Login successful', response);
    }),
    catchError(this.handleError)
  );
}

  logout(): void {
sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    if (this.isBrowser()) {
      return !!localStorage.getItem('authToken');
    }
    return false;
  }

  getToken(): string | null {
    if (this.isBrowser()) {
      return localStorage.getItem('authToken');
    }
    return null;
  }

  getUser(): any {
    if (this.isBrowser()) {
      const userStr = localStorage.getItem('user');
      return userStr ? JSON.parse(userStr) : null;
    }
    return null;
  }

  checkSession(): Observable<any> {
    return this.http.get(`${this.apiUrl}/checkSession`);
  }

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = error.error?.message || error.statusText || 'Server error';
    }
    
    return throwError(() => new Error(errorMessage));
  }
}

interface LoginRequest {
  username: string;
  password: string;
}
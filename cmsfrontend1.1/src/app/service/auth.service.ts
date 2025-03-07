import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { JwtResponse } from '../dto/JwtResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8765/SPRINGSECURITY/api/auth/signin'; // Replace with your backend API URL
  private userSignal = signal<JwtResponse | null>(null); // Signal to store user details
  public user = this.userSignal.asReadonly(); // Expose a read-only signal

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, { username, password }).pipe(
      tap((response) => {
        if (response && response.accessToken) {
          localStorage.setItem('token', response.accessToken); // Store the JWT token in local storage
          this.userSignal.set(response); // Update user signal
          this.router.navigate(['/dashboard']); // Redirect to DashboardComponent on successful login
        }
      })
    );
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    this.userSignal.set(null); // Clear user signal
    this.router.navigate(['/login']);
  }

}

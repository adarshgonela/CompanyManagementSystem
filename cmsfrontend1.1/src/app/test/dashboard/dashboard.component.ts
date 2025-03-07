import { Component, OnInit, Signal } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { JwtResponse } from '../../dto/JwtResponse';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  constructor(private authService: AuthService) {}
 // Use a getter to access the user signal
 get user(): Signal<JwtResponse | null> {
  return this.authService.user;
}

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.authService.logout();
    }
  }

  logout(): void {
    this.authService.logout();
  }
}

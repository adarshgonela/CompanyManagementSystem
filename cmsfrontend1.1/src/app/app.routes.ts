import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { LeaveformComponent } from './employee/leaveform/leaveform.component';
import { EmpdashboardComponent } from './employee/empdashboard/empdashboard.component';
import { DetailsfetchComponent } from './testing/detailsfetch/detailsfetch.component';
import { ProfileComponent } from './employee/profile/profile.component';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
 
  {
    path: 'sidebar',
    component: SidebarComponent,
  },
  {
    path: 'leave/:id',
    component: LeaveformComponent,
  },
  {
    path: 'empdashboard/:id',
    component: EmpdashboardComponent,
  },
  {
    path: 'empdashboard',
    component: EmpdashboardComponent,
  },
  {
    path: 'e/:id',
    component: DetailsfetchComponent,
  },
  {
    path: 'e',
    component: DetailsfetchComponent,
  },
  {
    path: 'profile/:id',
    component: ProfileComponent,
  }

  // { path: '', redirectTo: '/login', pathMatch: 'full' },
  // { path: '**', redirectTo: '/login' },
  // { path: 'leave/:employeeId', component: LeaveformComponent }
];

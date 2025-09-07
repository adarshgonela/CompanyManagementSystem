import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/ems/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { LeaveformComponent } from './employee/leaveform/leaveform.component';
import { TestComponent } from './test/test.component';
import { EmpdashboardComponent } from './employee/empdashboard/empdashboard.component';
import { DetailsfetchComponent } from './testing/detailsfetch/detailsfetch.component';

export const routes: Routes = [
    {
        path:'',
        component:LoginComponent
    },
    {
        path:'login',
        component:LoginComponent
    },
    {
        path:'register',
        component:RegisterComponent
    },
    {
        path:'dashboard',
        component:DashboardComponent
    },
    {
        path:'sidebar',
        component:SidebarComponent
    }
    ,
    {
        path:'leaveform',
        component:LeaveformComponent
    },
   {
    path:'empdashboard/:id',
    component:DetailsfetchComponent
   },
   {
    path:'empdashboard',
    component:DetailsfetchComponent
   }
    // { path: '', redirectTo: '/login', pathMatch: 'full' },
    // { path: '**', redirectTo: '/login' },
    // { path: 'leave/:employeeId', component: LeaveformComponent }
];

import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/ems/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { LeaveformComponent } from './employee/leaveform/leaveform.component';

export const routes: Routes = [
    {
        path:'empdashboard',
        component:DashboardComponent
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
    }
    
    // { path: 'leave/:employeeId', component: LeaveformComponent }
];

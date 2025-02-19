import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/ems/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';

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
    }
];

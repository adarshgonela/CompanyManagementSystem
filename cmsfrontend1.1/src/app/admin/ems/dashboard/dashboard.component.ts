import { Component } from '@angular/core';
import { SidebarComponent } from "../../../common/sidebar/sidebar.component";
import { HeaderComponent } from "../../../common/header/header.component";
import { NavbarComponent } from "../../../common/navbar/navbar.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [SidebarComponent, NavbarComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}

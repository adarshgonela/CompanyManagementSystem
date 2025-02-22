import { Component } from '@angular/core';
import { SidebarComponent } from "../../../common/sidebar/sidebar.component";
import { HeaderComponent } from "../../../common/header/header.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}

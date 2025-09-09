import { Component } from '@angular/core';
import { NavbarComponent } from "../../common/navbar/navbar.component";
import { SidebarComponent } from "../../common/sidebar/sidebar.component";
import { FooterComponent } from "../../common/footer/footer.component";
import { HeaderComponent } from "../../common/header/header.component";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent, SidebarComponent, FooterComponent, HeaderComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

}

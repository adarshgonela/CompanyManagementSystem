import { Component } from '@angular/core';
import { HeaderComponent } from "../../common/header/header.component";
import { FooterComponent } from "../../common/footer/footer.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

}

import { Component, Renderer2, Inject, OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  private linkElements: HTMLLinkElement[] = [];

  constructor(
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    // Check if the <link> elements already exist before adding them
    if (!this.document.querySelector('link[href="assets/img/favicon.png"]')) {
      this.linkElements.push(this.addLinkElement('assets/img/favicon.png', 'icon', 'image/x-icon'));
    }
    if (!this.document.querySelector('link[href="assets/css/bootstrap.min.css"]')) {
      this.linkElements.push(this.addLinkElement('assets/css/bootstrap.min.css', 'stylesheet'));
    }
    if (!this.document.querySelector('link[href="assets/css/lnr-icon.css"]')) {
      this.linkElements.push(this.addLinkElement('assets/css/lnr-icon.css', 'stylesheet'));
    }
    if (!this.document.querySelector('link[href="assets/css/font-awesome.min.css"]')) {
      this.linkElements.push(this.addLinkElement('assets/css/font-awesome.min.css', 'stylesheet'));
    }
    if (!this.document.querySelector('link[href="assets/css/style.css"]')) {
      this.linkElements.push(this.addLinkElement('assets/css/style.css', 'stylesheet'));
    }
  }

  addLinkElement(href: string, rel: string, type?: string): HTMLLinkElement {
    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'rel', rel);
    this.renderer.setAttribute(link, 'href', href);
    if (type) {
      this.renderer.setAttribute(link, 'type', type);
    }
    this.renderer.appendChild(this.document.head, link);
    return link;
  }
}

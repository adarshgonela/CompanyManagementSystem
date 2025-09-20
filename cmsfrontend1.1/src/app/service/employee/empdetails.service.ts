import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../dto/Employee';
@Injectable({
  providedIn: 'root'
})
export class EmpdetailsService {


   private apiUrl = 'http://localhost:8000/api/employees'; // Adjust based on your backend URL

  constructor(private http: HttpClient) { }

  // Get all employees
  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/all`);
  }

  // Get employee by ID
  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }

  // Get employees by department
  getEmployeesByDepartment(department: string): Observable<Employee[]> {
    const params = new HttpParams().set('department', department);
    return this.http.get<Employee[]>(this.apiUrl, { params });
  }

  // Get employees by role type
  getEmployeesByRole(roletype: string): Observable<Employee[]> {
    const params = new HttpParams().set('roletype', roletype);
    return this.http.get<Employee[]>(this.apiUrl, { params });
  }

  // Search employees with optional filters
  searchEmployees(filters?: {
    department?: string;
    roletype?: string;
    position?: string;
  }): Observable<Employee[]> {
    let params = new HttpParams();
    
    if (filters) {
      Object.keys(filters).forEach(key => {
        const value = filters[key as keyof typeof filters];
        if (value) {
          params = params.set(key, value);
        }
      });
    }
    
    return this.http.get<Employee[]>(this.apiUrl, { params });
  }
 
   changeEmployeeProfile(id: number, employee: Employee): Observable<Employee> {
    const url = `${this.apiUrl}/change-profile/${id}`;
    return this.http.put<Employee>(url, employee);
  }
}

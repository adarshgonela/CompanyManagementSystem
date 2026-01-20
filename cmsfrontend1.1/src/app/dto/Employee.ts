export interface Employee {
  // image: null;
  id: number;
  empid:number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  department: string;
  position: string;
  hireDate: Date;
  roletype: string; // 'employee' | 'hr' | 'manager'
  gender: string;
  address: string;
  image: string | null;
}
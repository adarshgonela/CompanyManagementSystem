export interface JwtResponse {
    token: string;
  type: "Bearer";
  id: number;
  username: string;
  email: string;
  roles: string[];
  }
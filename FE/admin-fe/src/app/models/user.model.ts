export interface UserData {
  email: string;
  first_name: string;
  last_name: string;
}

export interface AddUserData {
  email: string;
  firstName: string;
  lastName: string;
  hospital: string;
  password: string;
  role: number;
}

export interface UserDetails {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  hospital: string;
  enabled: boolean;
}

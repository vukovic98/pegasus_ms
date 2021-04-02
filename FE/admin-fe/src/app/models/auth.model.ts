export interface LoginModel {
  email: string;
  password: string;
}

export interface loginResponse {
  authenticationToken: string;
  expiresAt: number;
  email: string;
  verified: boolean;
}

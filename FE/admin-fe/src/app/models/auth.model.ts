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

export interface Role {
  "id": number,
  "name": string,
  "authority": string
}

export interface TokenModel {
  "iss": string,
  "sub": string,
  "aud": string,
  "iat": number,
  "exp": number,
  "user_firstName": string,
  "user_lastName": string,
  "user_id": string,
  "authority": Array<Role>
}

export interface CertificateModel{
  serialNum: number,
  subjectID: number,
  subjectCN: string,
  subjectEmail: string,
  subjectOU: string,
  issuerID: number,
  issuerCN: string,
  issuerEmail: string,
  issuerOU: string,
  issuedDate: string,
  validToDate: string,
  revokedReason: string,
  revoked: boolean
}

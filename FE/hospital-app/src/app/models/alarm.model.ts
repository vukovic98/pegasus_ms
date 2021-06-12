export  interface AlarmModel{
  "id":number,
  "patientID":number,
  "dataType":string,
  "numValue":number,
  "patientsName":string,
  "value":string,
  "date":string
}

export interface SecurityAlarm {
  id: number,
  ipAddress: string,
  alarmType: string,
  date: Date
}

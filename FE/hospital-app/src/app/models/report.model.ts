export interface ReportEntryModel {
  startDate: Date,
  endDate: Date
}

export interface LogModel {
  id: string,
  date: Date,
  user: string,
  message: string,
  status: string
}

export interface DeviceLogModel {
  id: string,
  date: string,
  user: string,
  message: string
}

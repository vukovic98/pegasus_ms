import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ReportService} from '../../services/report.service';
import {DeviceLogModel, LogModel, ReportEntryModel} from '../../models/report.model';
import {AlarmModel, SecurityAlarm} from '../../models/alarm.model';
import {
  Chart,
  ArcElement,
  LineElement,
  BarElement,
  PointElement,
  BarController,
  BubbleController,
  DoughnutController,
  LineController,
  PieController,
  PolarAreaController,
  RadarController,
  ScatterController,
  CategoryScale,
  LinearScale,
  LogarithmicScale,
  RadialLinearScale,
  TimeScale,
  TimeSeriesScale,
  Decimation,
  Filler,
  Legend,
  Title,
  Tooltip
} from 'chart.js';
import Swal from 'sweetalert2';

Chart.register(
  ArcElement,
  LineElement,
  BarElement,
  PointElement,
  BarController,
  BubbleController,
  DoughnutController,
  LineController,
  PieController,
  PolarAreaController,
  RadarController,
  ScatterController,
  CategoryScale,
  LinearScale,
  LogarithmicScale,
  RadialLinearScale,
  TimeScale,
  TimeSeriesScale,
  Decimation,
  Filler,
  Legend,
  Title,
  Tooltip
);

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reportForm = new FormGroup({
    report: new FormControl('', Validators.required),
    startDate: new FormControl(''),
    endDate: new FormControl(''),
  });

  public logs: Array<LogModel> = [];
  public deviceLogs: Array<DeviceLogModel> = [];
  public deviceAlarms: Array<AlarmModel> = [];
  public securityAlarms: Array<SecurityAlarm> = [];
  public tableMaterial: Array<[string, string]> = []
  public chart: Chart;

  constructor(
    private reportService: ReportService
  ) { }

  ngOnInit(): void {
  }

  getAReport() {
    Swal.fire({
      title: 'It will take just a second to gather all the data!',
      allowOutsideClick: false,
      showCancelButton: false,
      showConfirmButton: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      },
    });
    const dto: ReportEntryModel = {
      startDate: this.reportForm.value.startDate ? this.reportForm.value.startDate : null,
      endDate: this.reportForm.value.endDate ? this.reportForm.value.endDate : null
    };

    if(this.reportForm.value.report == 'LOG') {
      this.reportService.getAllLogs(dto).subscribe((response) => {
        this.logs = response ? response : [];
        console.log(this.logs)
        this.drawLogChart();
        Swal.close();
      })
    }

    if(this.reportForm.value.report == 'DEVICE') {
      this.reportService.getAllDeviceData(dto).subscribe((response) => {
        this.deviceLogs = response ? response : [];
        this.drawDeviceLogChart();
        Swal.close();
      })
    }

    if(this.reportForm.value.report == 'LOG_ALARM') {
      this.reportService.getAllLogAlarms(dto).subscribe((response) => {
        this.securityAlarms = response ? response : [];
        this.drawSecurityAlarmChart();
        console.log(this.securityAlarms)
        Swal.close();

      })
    }

    if(this.reportForm.value.report == 'DEVICE_ALARM') {
      this.reportService.getAllDeviceAlarms(dto).subscribe((response) => {
        this.deviceAlarms = response ? response : [];
        this.drawDeviceAlarmChart();
        console.log(this.deviceAlarms)
        Swal.close();

      })
    }
  }

  public drawLogChart() {
    this.chart ? this.chart.destroy() : '';

    let info = 0;
    let debug = 0;
    let warn = 0;
    let error = 0;
    let fatal = 0;

    this.logs.forEach((log) => {
      if(log.status === 'DEBUG') debug += 1;
      if(log.status === 'INFO') info += 1;
      if(log.status === 'WARN') warn += 1;
      if(log.status === 'ERROR') error += 1;
      if(log.status === 'FATAL') fatal += 1;
    })

    this.tableMaterial = new Array<[string, string]>();

    this.tableMaterial.push(['DEBUG', debug.toString()]);
    this.tableMaterial.push(['INFO', info.toString()]);
    this.tableMaterial.push(['WARN', warn.toString()]);
    this.tableMaterial.push(['ERROR', error.toString()]);
    this.tableMaterial.push(['FATAL', fatal.toString()]);

    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: ['DEBUG', 'INFO', 'WARN', 'ERROR', 'FATAL'],
        datasets: [{
          label: 'Logs',
          data: [debug, info, warn, error, fatal],
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }

  public drawDeviceLogChart() {
    this.chart ? this.chart.destroy() : '';

    let blood = 0;
    let heart = 0;
    let neuro = 0;

    this.deviceLogs.forEach((log) => {
      if(log.user === 'BLOOD_DEVICE') blood += 1;
      if(log.user === 'HEART_MONITOR_DEVICE') heart += 1;
      if(log.user === 'NEUROLOGICAL_DEVICE') neuro += 1;
    })

    this.tableMaterial = new Array<[string, string]>();

    this.tableMaterial.push(['Blood device messages', blood.toString()]);
    this.tableMaterial.push(['Heart monitor device messages', heart.toString()]);
    this.tableMaterial.push(['Neurological device messages', neuro.toString()]);

    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: ['BLOOD_DEVICE', 'HEART_DEVICE', 'NEURO_DEVICE'],
        datasets: [{
          label: 'Device data',
          data: [blood, heart, neuro],
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }

  public drawDeviceAlarmChart() {
    this.chart ? this.chart.destroy() : '';

    let patients: Array<number> = new Array(50).fill(0);

    this.deviceAlarms.forEach((log) => {
      patients[log.patientID - 150] += 1;
    })

    this.tableMaterial = new Array<[string, string]>();

    patients.forEach((pat) => {
      let patientID: number = patients.indexOf(pat) + 150;
      this.tableMaterial.push(['Patient [ ' + patientID + " ]", pat.toString()]);
    })

    let labels = new Array(50);

    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: Array.from(patients.keys()),
        datasets: [{
          label: 'Device alarms',
          data: patients,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }

  public drawSecurityAlarmChart() {
    this.chart ? this.chart.destroy() : '';

    let types: Set<string> = new Set<string>();

    this.securityAlarms.forEach((log) => {
      types.add(log.alarmType);
    })

    let values = {};

    types.forEach((type) => {
      values[type] = 0;
    })

    this.securityAlarms.forEach((log) => {
      values[log.alarmType] += 1;
    })

    this.tableMaterial = new Array<[string, string]>();

    let labels = [];
    let data = [];

    for(let key in values) {
      this.tableMaterial.push([key, values[key]])
      labels.push(key);
      data.push(values[key]);
    }

    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: 'Log alarms',
          data: data,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }
}

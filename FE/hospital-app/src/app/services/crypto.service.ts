import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  encryptData(data) {

    try {
      return CryptoJS.AES.encrypt(JSON.stringify(data), environment.CYPHER_KEY).toString();
    } catch (e) {
      console.log(e);
    }
  }

  decryptData(data) {

    try {
      const bytes = CryptoJS.AES.decrypt(data, environment.CYPHER_KEY);
      if (bytes.toString()) {
        return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
      }
      return data;
    } catch (e) {
      console.log(e);
    }
  }
}

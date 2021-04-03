package com.ftn.uns.ac.rs.adminapp.service;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateRequestDTO;
import com.ftn.uns.ac.rs.adminapp.repository.CertificateRequestRepository;



@Service
public class CertificateRequestService {

	@Autowired
	private CertificateRequestRepository certRepository;
	
	public ArrayList<CertificateRequestDTO> findAll(){
		ArrayList<CertificateRequestDTO> requests = new ArrayList();
		ArrayList<CertificateRequest> byteRequests = (ArrayList) this.certRepository.findAll();
		
		for(CertificateRequest cr: byteRequests) {
			PKCS10CertificationRequest request = null;
			CertificateRequestDTO dto = null;
			try {
				request = new PKCS10CertificationRequest(cr.getCertificateRequest());
				dto = new CertificateRequestDTO();
				RDN[] listaAtributa = request.getSubject().getRDNs();
				//mejl ime bolnica organiyhaciaj srbija srbija rs
				dto.setSubjectEmail(listaAtributa[0].getFirst().getValue().toString());
				dto.setSubjectCN(listaAtributa[1].getFirst().getValue().toString());
				dto.setSubjectOU(listaAtributa[2].getFirst().getValue().toString());
				dto.setSubjectON(listaAtributa[3].getFirst().getValue().toString());
				dto.setSubjectL(listaAtributa[4].getFirst().getValue().toString());
				dto.setSubjectST(listaAtributa[5].getFirst().getValue().toString());
				dto.setSubjectC(listaAtributa[6].getFirst().getValue().toString());
				requests.add(dto);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return requests;
	}
	
	public boolean save(byte[] request) {
		CertificateRequest r = new CertificateRequest(request);
		return this.certRepository.save(r)!=null ? true : false;
	}
}

package com.ftn.uns.ac.rs.adminapp.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateRequestDTO;
import com.ftn.uns.ac.rs.adminapp.repository.CertificateRequestRepository;
import com.ftn.uns.ac.rs.adminapp.util.CipherEncrypt;

@Service
public class CertificateRequestService {

	@Autowired
	private CertificateRequestRepository certRepository;

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean remove(CertificateRequest c) {
		try {
			this.certRepository.deleteById(c.getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<CertificateRequestDTO> findAll() {
		ArrayList<CertificateRequestDTO> requests = new ArrayList<>();
		ArrayList<CertificateRequest> byteRequests = this.certRepository.findAllVerified();

		for (CertificateRequest cr : byteRequests) {
			PKCS10CertificationRequest request = null;
			CertificateRequestDTO dto = null;
			try {
				request = new PKCS10CertificationRequest(cr.getCertificateRequest());
				dto = new CertificateRequestDTO();
				RDN[] listaAtributa = request.getSubject().getRDNs();
				// mejl ime bolnica organiyhaciaj srbija srbija rs
				dto.setSubjectEmail(listaAtributa[0].getFirst().getValue().toString());
				dto.setSubjectCN(listaAtributa[1].getFirst().getValue().toString());
				dto.setSubjectOU(listaAtributa[2].getFirst().getValue().toString());
				dto.setSubjectON(listaAtributa[3].getFirst().getValue().toString());
				dto.setSubjectL(listaAtributa[4].getFirst().getValue().toString());
				dto.setSubjectST(listaAtributa[5].getFirst().getValue().toString());
				dto.setSubjectC(listaAtributa[6].getFirst().getValue().toString());
				dto.setId(cr.getId());
				requests.add(dto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return requests;
	}

	public CertificateRequest findOneById(long id) {
		return this.certRepository.findOneById(id);
	}

	public boolean save(byte[] req, String email) {
		CertificateRequest r = new CertificateRequest(req, false);

		CertificateRequest c = this.certRepository.save(r);

		if (c != null) {
			this.sendMailToUser(email, c.getId());

			return true;
		} else {
			return false;
		}
	}

	public String createMailBody(String mail, long id) {

		StringBuffer sb = new StringBuffer();

		String reqId = CipherEncrypt.encrypt(id + "", env.getProperty("cipherKey"));

		sb.append("<code>Hello, <br><br>");
		sb.append("We sent you this mail in order to keep our safety and integrity on the highest level possible.");
		sb.append("Certificate request has arrived in our database, and we would like to ensure it is valid.<br>");
		sb.append("With that in mind, if you sent that request for your hospital, please click link bellow.<br<br>");
		sb.append("<h2>https://localhost:8080/certificate-request/verify-request?id=" + reqId + "</h2><br><br>");
		sb.append("Sincerely,<br> Pegasus MS Team</code>");

		return sb.toString();
	}

	@Async
	public void sendMailToUser(String email, long id) {
		try {
			MimeMessage msg = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);

			helper.setTo(email);
			helper.setSubject("Pegasus Medical System : Request Verification Mail");

			helper.setText(createMailBody(email, id), true);
			this.javaMailSender.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verify(long reqId) {
		CertificateRequest c = this.certRepository.findById(reqId).orElse(null);

		if (c != null) {
			c.setVerified(true);
			this.certRepository.save(c);
			
			return true;
		} else
			return false;
	}
}

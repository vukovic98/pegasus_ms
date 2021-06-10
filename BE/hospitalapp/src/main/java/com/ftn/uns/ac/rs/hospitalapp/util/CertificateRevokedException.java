package com.ftn.uns.ac.rs.hospitalapp.util;

public class CertificateRevokedException extends Exception{

	public CertificateRevokedException() {
		super();
	}

	public CertificateRevokedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CertificateRevokedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CertificateRevokedException(String message) {
		super(message);
	}

	public CertificateRevokedException(Throwable cause) {
		super(cause);
	}

	
	
}

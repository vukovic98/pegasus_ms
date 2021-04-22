package com.ftn.uns.ac.rs.adminapp.util;

public class FinalMessage {

	private byte[] hash_and_compress;
	private byte[] encrypted_sym_key;

	public FinalMessage() {
		super();
	}

	public FinalMessage(byte[] hash_and_compress, byte[] encrypted_sym_key) {
		super();
		this.hash_and_compress = hash_and_compress;
		this.encrypted_sym_key = encrypted_sym_key;
	}

	public byte[] getHash_and_compress() {
		return hash_and_compress;
	}

	public void setHash_and_compress(byte[] hash_and_compress) {
		this.hash_and_compress = hash_and_compress;
	}

	public byte[] getEncrypted_sym_key() {
		return encrypted_sym_key;
	}

	public void setEncrypted_sym_key(byte[] encrypted_sym_key) {
		this.encrypted_sym_key = encrypted_sym_key;
	}

}

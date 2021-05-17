package com.ftn.uns.ac.rs.heartmonitor.util;

public class FinalMessage {

	private byte[] hash_and_compress;
	private byte[] encrypted_sym_key;
	private byte[] ivSpec;

	public FinalMessage() {
		super();
	}

	public FinalMessage(byte[] hash_and_compress, byte[] encrypted_sym_key, byte[] spec) {
		super();
		this.hash_and_compress = hash_and_compress;
		this.encrypted_sym_key = encrypted_sym_key;
		this.ivSpec = spec;
	}

	public byte[] getIvSpec() {
		return ivSpec;
	}

	public void setIvSpec(byte[] ivSpec) {
		this.ivSpec = ivSpec;
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

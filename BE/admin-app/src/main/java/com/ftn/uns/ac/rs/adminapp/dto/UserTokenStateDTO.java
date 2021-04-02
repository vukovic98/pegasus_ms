package com.ftn.uns.ac.rs.adminapp.dto;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
public class UserTokenStateDTO {

	private String authenticationToken;
	//private String refreshToken;
	private int expiresAt;
	private String email;
	private boolean verified;
	    
    //private String accessToken;
    //private Long expiresIn;

    public UserTokenStateDTO() {
    }

    public UserTokenStateDTO(String authenticationToken, int expiresAt) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
	}
    
    public UserTokenStateDTO(String authenticationToken, int expiresAt, boolean verified) {
  		super();
  		this.authenticationToken = authenticationToken;
  		this.expiresAt = expiresAt;
  		this.verified = verified;
  	}
    
    public UserTokenStateDTO(String authenticationToken, int expiresAt, String email, boolean verified) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
		this.email = email;
		this.verified = verified;
	}

	public UserTokenStateDTO(String authenticationToken, int expiresAt, String email) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
		this.email = email;
	}
    
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public int getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(int expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getEmail() { 
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
    
}

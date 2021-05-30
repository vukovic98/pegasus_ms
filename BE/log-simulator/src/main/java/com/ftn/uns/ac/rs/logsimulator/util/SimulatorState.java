package com.ftn.uns.ac.rs.logsimulator.util;

public enum SimulatorState {

	//STANJA NORMALNIH AKTIVNOSTI
	
	//Jedan uspesan pokusaj prijave
	NORMAL_SIGN_IN_ATTEMPT,
	
	//Prijava na nalog koji je bio aktivan u poslednjih 90 dana
	NORMAL_ACCOUNT_ACTIVITY,
	
	//Prijava sa IP adrese koja nije na spisku malicioznih adresa
	NORMAL_IP_ADDRESS_SIGN_IN,
	
	//Log koji sadrzi IP adresu koja nije na spisku malicioznih adresa
	NORMAL_IP_ADDRESS_LOG,
	
	//Manje od 50 zahteva koji nisu sign-in u roku od 60 sekundi
	NORMAL_REQUEST_AMOUNT,
	
	//Manje od 50 zahteva koji jesu sign-in u roku od 60 sekundi
	NORMAL_SIGN_IN_AMOUNT,
	
	
	//STANJA AKTIVNOSTI NAPADA
	
	//Vise neuspesnih pokusaja prijave
	ATTACK_SIGN_IN_ATTEMPT,
	
	//Prijava na nalog koji nije bio aktivan u poslednjih 90 dana
	ATTACK_ACCOUNT_ACTIVITY,
	
	//Prijava sa IP adrese koja jeste na spisku malicioznih adresa
	ATTACK_IP_ADDRESS_SIGN_IN,
	
	//Log koji sadrzi IP adresu koja jeste na spisku malicioznih adresa
	ATTACK_IP_ADDRESS_LOG,
	
	//Vise od 50 zahteva koji nisu sign-in u roku od 60 sekundi (DoS attack)
	ATTACK_REQUEST_AMOUNT,
	
	//Vise od 50 zahteva koji jesu sign-in u roku od 60 sekundi (Brute-force attack)
	ATTACK_SIGN_IN_AMOUNT
	
	
}

package com.ftn.uns.ac.rs.adminapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.Authority;
import com.ftn.uns.ac.rs.adminapp.beans.Privilege;
import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.repository.AuthorityRepository;
import com.ftn.uns.ac.rs.adminapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthorityRepository authRepository;

//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail(email);
//		
//		if (user == null) {
//			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email)); 
//		} else {
//			return user;
//		}
//	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
		}

		user.setAuthorities((List<Authority>) getAuthorities(user.getAuthorities()));

		return user;
	}

	private Collection<Authority> getAuthorities(Collection<? extends GrantedAuthority> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<? extends GrantedAuthority> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (GrantedAuthority role : roles) {
			Authority a = this.authRepository.findByName(role.getAuthority());
			collection.addAll(a.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<Authority> getGrantedAuthorities(List<String> privileges) {
		List<Authority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			Authority a = new Authority();
			a.setName(privilege);
			authorities.add(a);
		}
		return authorities;
	}

	// Funkcija pomocu koje korisnik menja svoju lozinku
	public void changePassword(String oldPassword, String newPassword) {

		// Ocitavamo trenutno ulogovanog korisnika
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = ((User) currentUser.getPrincipal()).getEmail();

		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			return;
		}
		User user = (User) loadUserByUsername(username);
		System.out.println(user.getEmail());
		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

}
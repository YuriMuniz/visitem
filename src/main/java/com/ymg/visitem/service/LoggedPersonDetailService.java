package com.ymg.visitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.LoggedPerson;
import com.ymg.visitem.entity.Usuario;


@Service
public class LoggedPersonDetailService implements UserDetailsService{

	@Autowired
	private IUsuarioService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario;
		
		try {
			
			usuario = service.findByEmail(username);
		}
		catch (Exception ex){
			
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}
		LoggedPerson logged = new LoggedPerson (usuario);
		logged.setAuthenticated(true);
		return logged;
	}

}

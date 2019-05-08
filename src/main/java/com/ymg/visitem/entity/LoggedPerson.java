package com.ymg.visitem.entity;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public class LoggedPerson extends User implements Authentication{

	private static final long serialVersionUID = 1L;
	private boolean isAuthenticated;
	private Usuario usuario;
	
	

	public LoggedPerson(Usuario usuario) {
		
		super(usuario.getEmail(), 
				usuario.getSenhaHash(), 
				usuario.getSituacao().equals(SituacaoUsuario.ATIVO), 
				true, true, true, 
				usuario.getUsuarioProfiles());
		
		this.usuario = usuario;
	}
	
	public List<Profile> getProfiles() {
		return usuario.getUsuarioProfiles();
	}
	
	public Long getId() {
		return usuario.getId();
	}

	@Override
	public String getName() {
		return usuario.getEmail();
	}

	@Override
	public Object getCredentials() {
		return usuario.getSenhaHash();
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return usuario;
	}

	@Override
	public boolean isAuthenticated() {
		
		if (usuario.getSituacao().equals(SituacaoUsuario.ATIVO)) {
			
			setIsAuth(true);
			return true;
		}
		
		setIsAuth(false);
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		setIsAuth(isAuthenticated);
	}

	public boolean getIsAuth() {
		return isAuthenticated;
	}

	public void setIsAuth(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
}

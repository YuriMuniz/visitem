package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.SituacaoUsuario;
import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.repository.IRepositoryUsuario;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	IRepositoryUsuario repositoryUsuario;
	
	@Override
	public Usuario saveUsuario(Usuario usuario) {
		usuario.setSituacao(SituacaoUsuario.ATIVO);
		usuario.setSenhaHash(new BCryptPasswordEncoder().encode(usuario.getSenhaHash()));
		return repositoryUsuario.saveAndFlush(usuario);
		
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		usuario.setSenhaHash(new BCryptPasswordEncoder().encode(usuario.getSenhaHash()));
		return repositoryUsuario.saveAndFlush(usuario);
	}

	@Override
	public List<Usuario> findAll() {
		return repositoryUsuario.findAll();
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		repositoryUsuario.delete(usuario);
		
	}

	@Override
	public Usuario getById(Long id) {
		return repositoryUsuario.getOne(id);
	}

	@Override
	public Usuario findByEmail(String email) {
		return repositoryUsuario.findByEmail(email);
	}
	
	public boolean existUsuario(String email){
		if(repositoryUsuario.findByEmail(email)==null){
			return false;
		}
		else{
			return true;
		}
	}

}

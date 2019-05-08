package com.ymg.visitem.service;

import java.util.List;

import com.ymg.visitem.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario saveUsuario(Usuario usuario);
	public Usuario updateUsuario(Usuario usuario);
	public List<Usuario> findAll();
	public void deleteUsuario(Usuario usuario);
	public Usuario getById(Long id);
	public Usuario findByEmail(String email);
	public boolean existUsuario(String email);

}

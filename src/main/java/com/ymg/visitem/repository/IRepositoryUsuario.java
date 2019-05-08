package com.ymg.visitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Usuario;

public interface IRepositoryUsuario extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
	
}

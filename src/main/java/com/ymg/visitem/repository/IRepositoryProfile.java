package com.ymg.visitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Profile;

public interface IRepositoryProfile extends JpaRepository<Profile, Long>  {
	Profile findByUsuarioEmail(String email);
}

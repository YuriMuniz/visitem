package com.ymg.visitem.service;

import java.util.List;

import com.ymg.visitem.entity.Profile;

public interface IProfileService {
	public Profile saveProfile(Profile profile);
	public List<Profile> findAll();
	public Profile alterProfile(Profile profile);
	public void deleteProfile(Profile profile);
	public Profile findById(Long id);
	public Profile findByUsuarioEmail(String email);
}

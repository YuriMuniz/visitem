package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Profile;
import com.ymg.visitem.repository.IRepositoryProfile;

@Service
public class ProfileService implements IProfileService {
	@Autowired
	public IRepositoryProfile repositoryProfile;

	@Override
	public Profile saveProfile(Profile profile) {
		return repositoryProfile.saveAndFlush(profile);
	}

	@Override
	public List<Profile> findAll() {
		return repositoryProfile.findAll();
	}

	@Override
	public Profile alterProfile(Profile profile) {
		return repositoryProfile.saveAndFlush(profile);
	}

	@Override
	public void deleteProfile(Profile profile) {
		repositoryProfile.delete(profile);
		
	}

	@Override
	public Profile findById(Long id) {
		return repositoryProfile.getOne(id);
		
	}

	@Override
	public Profile findByUsuarioEmail(String email) {
		return repositoryProfile.findByUsuarioEmail(email);
	}

}

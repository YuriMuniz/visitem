package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Telefone;
import com.ymg.visitem.repository.IRepositoryTelefone;

@Service
public class TelefoneService implements ITelefoneService {

	@Autowired
	public IRepositoryTelefone repositoryTelefone;
	
	@Override
	public Telefone saveTelefone(Telefone telefone) {
		return repositoryTelefone.save(telefone);
	}

	@Override
	public Telefone updateTelefone(Telefone telefone) {
		return repositoryTelefone.saveAndFlush(telefone);
	}

	@Override
	public List<Telefone> findAll() {
		return repositoryTelefone.findAll();
	}

	@Override
	public void deleteTelefone(Telefone telefone) {
		repositoryTelefone.delete(telefone);
		
	}

	@Override
	public Telefone findById(Long id) {
		return repositoryTelefone.getOne(id);
	}

}

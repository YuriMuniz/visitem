package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Endereco;
import com.ymg.visitem.repository.IRepositoryEndereco;

@Service
public class EnderecoService implements IEnderecoService {
	
	@Autowired
	public IRepositoryEndereco repositoryEndereco;
	
	@Override
	public Endereco saveEndereco(Endereco endereco) {
		return repositoryEndereco.save(endereco);
	}

	@Override
	public Endereco updateEndereco(Endereco endereco) {
		return repositoryEndereco.saveAndFlush(endereco);
	}

	@Override
	public List<Endereco> findAll() {
		return repositoryEndereco.findAll(); 
	}

	@Override
	public void deleteEndereco(Endereco endereco) {
		repositoryEndereco.delete(endereco);
		
	}

	@Override
	public Endereco findById(Long id) {
		return repositoryEndereco.getOne(id);
	}

}

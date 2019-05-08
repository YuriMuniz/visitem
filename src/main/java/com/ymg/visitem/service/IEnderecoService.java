package com.ymg.visitem.service;

import java.util.List;

import com.ymg.visitem.entity.Endereco;

public interface IEnderecoService {
	
	public Endereco saveEndereco(Endereco endereco);
	public Endereco updateEndereco(Endereco endereco);
	public List<Endereco> findAll();
	public void deleteEndereco(Endereco endereco);
	public Endereco findById(Long id);

}

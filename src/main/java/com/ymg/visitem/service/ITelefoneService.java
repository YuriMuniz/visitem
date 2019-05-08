package com.ymg.visitem.service;

import java.util.List;

import com.ymg.visitem.entity.Telefone;

public interface ITelefoneService {
	public Telefone saveTelefone(Telefone telefone);
	public Telefone updateTelefone(Telefone telefone);
	public List<Telefone> findAll();
	public void deleteTelefone(Telefone telefone);
	public Telefone findById(Long id);
}

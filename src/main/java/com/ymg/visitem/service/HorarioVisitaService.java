package com.ymg.visitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.HorarioVisita;
import com.ymg.visitem.repository.IRepositoryHorarioVisita;

@Service
public class HorarioVisitaService implements IHorarioVisitaService {

	@Autowired
	IRepositoryHorarioVisita repositoryHorarioVisita;
	
	@Override
	public HorarioVisita save(HorarioVisita horarioVisita) {
		return repositoryHorarioVisita.save(horarioVisita);
	}

	@Override
	public HorarioVisita update(HorarioVisita horarioVisita) {
		return repositoryHorarioVisita.saveAndFlush(horarioVisita);
	}

	@Override
	public void delete(HorarioVisita horarioVisita) {
		 repositoryHorarioVisita.delete(horarioVisita);
	}

	@Override
	public HorarioVisita findOne(Long id) {
		
		return repositoryHorarioVisita.getOne(id);
	}

}

package com.ymg.visitem.service;

import com.ymg.visitem.entity.HorarioVisita;

public interface IHorarioVisitaService {

	public HorarioVisita save(HorarioVisita horarioVisita);
	public HorarioVisita update(HorarioVisita horarioVisita);
	public void delete(HorarioVisita horarioVisita);
	public HorarioVisita findOne(Long id);
}

package com.ymg.visitem.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="TBL_HORARIO_VISITA")
public class HorarioVisita extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TBL_HORARIO_FUNCIONAMENTO_TURNO = "TBL_HORARIO_VISITA_TURNO";
	private static final String COLUMN_TURNO = "TURNO";
	private static final String COLUMN_ID_HORARIO_FUNCIONAMENTO= "ID_HORARIO_VISITA";
	private static final String COLUMN_FUNCIONA_FIM_DE_SEMANA = "FUNCIONA_FDS";
	
	@Column(name=COLUMN_FUNCIONA_FIM_DE_SEMANA, nullable=false)
	private boolean funcionaFds;
	
	@ElementCollection(targetClass = TurnoHorarioFuncionamento.class)
	@JoinTable(name = TBL_HORARIO_FUNCIONAMENTO_TURNO, joinColumns = @JoinColumn(name = COLUMN_ID_HORARIO_FUNCIONAMENTO))
	@Column(name = COLUMN_TURNO, nullable = true)
	@Enumerated(EnumType.STRING)
	Collection<TurnoHorarioFuncionamento> turnoFuncionamento;

	public boolean isFuncionaFds() {
		return funcionaFds;
	}

	public void setFuncionaFds(boolean funcionaFds) {
		this.funcionaFds = funcionaFds;
	}

	public Collection<TurnoHorarioFuncionamento> getTurnoFuncionamento() {
		return turnoFuncionamento;
	}

	public void setTurnoFuncionamento(Collection<TurnoHorarioFuncionamento> turnoFuncionamento) {
		this.turnoFuncionamento = turnoFuncionamento;
	}
	
}

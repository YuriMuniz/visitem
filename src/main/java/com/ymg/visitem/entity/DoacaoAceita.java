package com.ymg.visitem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "TBL_DOACAO_ACEITA")
public class DoacaoAceita extends AbstractPersistable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_USUARIO = "ID_USUARIO";
	public static final String COLUMN_DOACAO = "ID_DOACAO";
	public static final String COLUMN_PRAZO = "PRAZO";
	public static final String COLUMN_DATA_ACEITACAO = "DATA_ACEITACAO";
	public static final String COLUMN_ENTREGA_CONFIRMADA = "DATA_ENTREGA_CONFIRMADA";
	
	@ManyToOne
	@JoinColumn(name = COLUMN_USUARIO, nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_DOACAO, nullable = false)
	private Doacao doacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_PRAZO, nullable = false)
	private PrazoEscolhido prazo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_DATA_ACEITACAO, nullable = false)
	private Date dataAceitacao;
	
	@Temporal (TemporalType.TIMESTAMP)
	@Column(name = COLUMN_ENTREGA_CONFIRMADA, nullable = true)
	private Date dataEntregaConfirmada;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	

	public Doacao getDoacao() {
		return doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}

	public PrazoEscolhido getPrazo() {
		return prazo;
	}

	public void setPrazo(PrazoEscolhido prazo) {
		this.prazo = prazo;
	}

	public Date getDataAceitacao() {
		return dataAceitacao;
	}

	public void setDataAceitacao(Date dataAceitacao) {
		this.dataAceitacao = dataAceitacao;
	}

	public Date getDataEntregaConfirmada() {
		return dataEntregaConfirmada;
	}

	public void setDataEntregaConfirmada(Date dataEntregaConfirmada) {
		this.dataEntregaConfirmada = dataEntregaConfirmada;
	} 
	
	
	
}

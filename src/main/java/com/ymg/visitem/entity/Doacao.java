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
@Table(name = "TBL_DOACAO")
public class Doacao extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_NOME = "NOME";
	public static final String COLUMN_FOTO = "FOTO";
	public static final String COLUMN_DESCRICAO = "DESCRICAO";
	public static final String COLUMN_SITUACAO = "SITUACAO";
	public static final String COLUMN_INSTITUICAO = "ID_INSTITUICAO";
	public static final String COLUMN_DATA_CRIACAO = "DATA_CRIACAO";
	public static final String COLUMN_HOSPEDE = "ID_HOSPEDE";
	
	@Column(name = COLUMN_NOME, nullable = false, length = 100)
	private String nome;
	
	@Column(name = COLUMN_FOTO, nullable = true, length = 300)
	private String photo;
	
	@Column(name=COLUMN_DESCRICAO, nullable = true, length =  300)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_SITUACAO, nullable = false)
	private SituacaoDoacao situacao;
	
	@ManyToOne
	@JoinColumn(name=COLUMN_INSTITUICAO, nullable = false)
	private Instituicao instituicao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_DATA_CRIACAO, nullable = false)
	private Date dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_HOSPEDE, nullable = false)
	private Hospede hospede;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SituacaoDoacao getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDoacao situacao) {
		this.situacao = situacao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}

	
	
	
}

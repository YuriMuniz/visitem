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
@Table (name ="TBL_HOSPEDE")
public class Hospede extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_NOME = "NOME";
	public static final String COLUMN_SOBRENOME = "SOBRENOME";
	public static final String COLUMN_DESCRICAO = "DESCRICAO";
	public static final String COLUMN_FOTO = "FOTO";
	public static final String COLUMN_DATA_NASCIMENTO = "DATA_NASCIMENTO";
	public static final String COLUMN_SITUACAO = "SITUACAO";
	public static final String COLUMN_SEXO = "SEXO";
	public static final String COLUMN_INSTITUICAO = "ID_INSTITUICAO";




	@Column(name = COLUMN_NOME, nullable = false, length = 50)
	private String nome;
	
	@Column(name = COLUMN_SOBRENOME, nullable = false, length = 100)
	private String sobrenome;
	
	@Column(name = COLUMN_DESCRICAO, nullable = true, length = 200)
	private String descricao;
	
	@Column(name = COLUMN_FOTO, nullable = true)
	private String foto;
	
	@Temporal(TemporalType.DATE)
	@Column(name = COLUMN_DATA_NASCIMENTO, nullable = false)
	private Date dataNascimento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_SITUACAO, nullable = false)
	private SituacaoHospede situacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_SEXO, nullable = false)
	private Sexo sexo;
	
	@ManyToOne
	@JoinColumn(name=COLUMN_INSTITUICAO, nullable = true)
	private Instituicao instituicao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public SituacaoHospede getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoHospede situacao) {
		this.situacao = situacao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	
	
	
	
}

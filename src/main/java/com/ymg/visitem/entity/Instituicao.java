package com.ymg.visitem.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TBL_INSTITUICAO")
public class Instituicao extends AbstractPersistable<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_NOME = "NOME";
	public static final String COLUMN_CNPJ = "CNPJ";
	public static final String COLUMN_FOTO = "FOTO";
	public static final String COLUMN_LOGO = "LOGO";
	public static final String COLUMN_LATITUDE = "LATITUDE";
	public static final String COLUMN_LONGITUDE = "LONGITUDE";
	public static final String COLUMN_EMAIL = "EMAIL";
	public static final String COLUMN_NOME_USUARIO = "NOME_USUARIO";
	public static final String COLUMN_ENDERECO = "ID_ENDERECO";
	public static final String COLUMN_SITUACAO = "SITUACAO";
	public static final String COLUMN_DATA_CRIACAO = "DATA_CRIACAO";
	public static final String TBL_INSTITUICAO_TELEFONE = "TBL_INSTITUICAO_TELEFONE";
	public static final String COLUMN_TELEFONE = "ID_TELEFONE";
	public static final String COLUMN_INSTITUICAO = "ID_INSTITUICAO";
	public static final String TBL_INSTITUICAO_BENEFICIADO = "TBL_INSTITUICAO_BENEFICIADO";
	public static final String COLUMN_BENEFICIADO = "ID_BENEFICIADO";
	public static final String COLUMN_DATA_FUNDACAO = "DATA_FUNDACAO";
	public static final String COLUMN_NUMERO_HOSPEDES = "NUMERO_HOSPEDES";
	public static final String COLUMN_SITE = "SITE";
	public static final String COLUMN_DESCRICAO = "DESCRICAO";
	public static final String COLUMN_NOME_RESPONSAVEL = "NOME_RESPONSAVEL";
	public static final String COLUMN_CPF_RESPONSAVEL = "CPF_RESPONSAVEL";
	public static final String COLUMN_CATEGORIA= "CATEGORIA";
	public static final String COLUMN_CARENCIA= "CARENCIA";
	public static final String COLUMN_HORARIO_VISITA= "ID_HORARIO_VISITA";
	
	
	@Column(name = COLUMN_NOME, nullable = false, length=50)
	private String nome;

	@Column(name = COLUMN_CNPJ, nullable = false, length = 18)
	private String cnpj;
		
	@Column(name = COLUMN_FOTO, nullable = true, length = 150)
	private String foto;
	
	@Column(name = COLUMN_LOGO, nullable = true, length = 150)
	private String logo;

	
	@Column(name = COLUMN_EMAIL, nullable = false, length = 100)
	private String email;
	
	@Column(name = COLUMN_NOME_USUARIO, nullable = true, length = 100)
	private String nomeUsuario;
	
	@Column(name = COLUMN_NUMERO_HOSPEDES, nullable = true)
	private int numeroHospede;
	
	@Column (name = COLUMN_SITE, nullable = true)
	private String site;
	
	@Column (name = COLUMN_DESCRICAO, nullable = true , length = 10000)
	private String descricao;
	
	@Column (name = COLUMN_CARENCIA, nullable = true , length = 1000)
	private String carencia;
	
	@Column (name = COLUMN_NOME_RESPONSAVEL, nullable = true)
	private String nomeResponsavel;
	
	@Column (name = COLUMN_CPF_RESPONSAVEL, nullable = true)
	private String cpfResponsavel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_DATA_CRIACAO, nullable = false)
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_DATA_FUNDACAO, nullable = true)
	private Date dataFundacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_SITUACAO, nullable = false)
	private SituacaoInstituicao situacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_CATEGORIA, nullable = true)
	private Categoria categoria;
	
	@OneToOne
	@JoinColumn(name=COLUMN_HORARIO_VISITA, nullable = true)
	private HorarioVisita horarioVisita;
	
	@ManyToOne
	@JoinColumn(name=COLUMN_ENDERECO, nullable = true)
	private Endereco endereco;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name =TBL_INSTITUICAO_TELEFONE,
	joinColumns = {@JoinColumn(name = COLUMN_INSTITUICAO, referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = COLUMN_TELEFONE, referencedColumnName = "id")}
	)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<Telefone> telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public SituacaoInstituicao getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoInstituicao situacao) {
		this.situacao = situacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}


	public int getNumeroHospede() {
		return numeroHospede;
	}

	public void setNumeroHospede(int numeroHospede) {
		this.numeroHospede = numeroHospede;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getCarencia() {
		return carencia;
	}

	public void setCarencia(String carencia) {
		this.carencia = carencia;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public HorarioVisita getHorarioVisita() {
		return horarioVisita;
	}

	public void setHorarioVisita(HorarioVisita horarioVisita) {
		this.horarioVisita = horarioVisita;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	
	
}

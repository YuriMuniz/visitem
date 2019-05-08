package com.ymg.visitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "TBL_ENDERECO")
public class Endereco extends AbstractPersistable<Long>	 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_CEP ="CEP";
	public static final String COLUMN_LOGRADOURO ="LOGRADOURO";
	public static final String COLUMN_BAIRRO ="BAIRRO";
	public static final String COLUMN_CIDADE ="CIDADE";
	public static final String COLUMN_UF ="UF";
	public static final String COLUMN_PAIS ="PAIS";
	public static final String COLUMN_NUMERO ="NUMERO";
	public static final String COLUMN_COMPLEMENTO ="COMPLEMENTO";
	public static final String COLUMN_PONTO_REFERENCIA ="PONTO_REFERENCIA";
	
	@Column(name = COLUMN_CEP, nullable = false , length = 8)
	private String cep;
	
	@Column(name  = COLUMN_LOGRADOURO, nullable = false, length = 100)
	private String logradouro;
	
	@Column(name  = COLUMN_BAIRRO, nullable = false, length = 100)
	private String bairro;
	
	@Column(name  = COLUMN_CIDADE, nullable = false, length = 100)
	private String cidade;
	
	
	@Column(name  = COLUMN_UF, nullable = false)
	private String uf;
	
	@Column(name  = COLUMN_PAIS, nullable = false, length = 100)
	private String pais;
	
	@Column(name  = COLUMN_NUMERO, nullable = false, length= 999999999)
	private String numero;
	
	@Column(name  = COLUMN_COMPLEMENTO, nullable = true, length = 100)
	private String complemento;
	
	
	@Column(name  = COLUMN_PONTO_REFERENCIA, nullable = true, length = 300)
	private String pontoReferencia;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}



	

	
	
	
	

}

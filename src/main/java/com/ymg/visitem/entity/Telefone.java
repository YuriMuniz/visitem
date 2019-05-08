package com.ymg.visitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "TBL_TELEFONE")
public class Telefone extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_CODIGO_PAIS = "CODIGO_PAIS";
	public static final String COLUMN_DDD = "DDD";
	public static final String COLUMN_NUMERO = "NUMERO";
	
	@Column(name = COLUMN_CODIGO_PAIS, nullable = true)
	private String codigoPais;
	@Column(name = COLUMN_DDD, nullable = false)
	private String ddd;
	@Column(name = COLUMN_NUMERO, nullable = false, length = 10)
	private String numero;
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
}

package com.ymg.visitem.form;

import org.springframework.web.multipart.MultipartFile;

public class DoacaoCreateForm {
	
	private MultipartFile foto = null;
	
	private String nome = "";
	
	private String descricao = "";
	
	private String idHospede= "";
	
	private String idInstituicao= "";

	public MultipartFile getFoto() {
		return foto;
	}

	public void setFoto(MultipartFile foto) {
		this.foto = foto;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getIdHospede() {
		return idHospede;
	}

	public void setIdHospede(String idHospede) {
		this.idHospede = idHospede;
	}

	public String getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(String idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
	
	
    

}

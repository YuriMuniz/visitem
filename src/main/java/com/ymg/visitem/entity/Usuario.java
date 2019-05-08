package com.ymg.visitem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "TBL_USUARIO")
public class Usuario extends AbstractPersistable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_NOME = "NOME";
	public static final String COLUMN_EMAIL = "EMAIL";
	public static final String COLUMN_SENHA = "SENHA_HASH";
	public static final String COLUMN_SENHA_VAZIA = "SENHA_VAZIA";
	public static final String COLUMN_FOTO = "FOTO";
	public static final String COLUMN_ID_INSTITUICAO = "ID_INSTITUICAO";
	public static final String COLUMN_SITUACAO = "SITUACAO";
	public static final String COLUMN_SOCIAL_PROFILE = "SOCIAL_PROFILE";
	
	@Column(name = COLUMN_NOME, length = 100, nullable = false )
	private String nome;
	
	@Column(name = COLUMN_EMAIL, length = 100, nullable = false, unique = true )
	private String email;
	
	@Column(name = COLUMN_FOTO, nullable = true, length = 150)
	private String foto;
	
	@Column(name = COLUMN_SENHA, nullable = false )
	private String senhaHash;
	
	@Column(name = COLUMN_SOCIAL_PROFILE, nullable = true )
	private String socialProfile;
	
	@OneToMany(mappedBy = "usuario", fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Profile> usuarioProfiles;
	
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_SITUACAO, nullable=false)
	private SituacaoUsuario situacao;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_ID_INSTITUICAO, nullable = true)
	private Instituicao instituicao;
	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public List<Profile> getUsuarioProfiles() {
		return usuarioProfiles;
	}

	public void setUsuarioProfiles(List<Profile> usuarioProfiles) {
		this.usuarioProfiles = usuarioProfiles;
	}

	public SituacaoUsuario getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoUsuario situacao) {
		this.situacao = situacao;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public String getSocialProfile() {
		return socialProfile;
	}

	public void setSocialProfile(String socialProfile) {
		this.socialProfile = socialProfile;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

}

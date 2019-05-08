package com.ymg.visitem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoInstituicao;

public interface IInstituicaoService {
	
	public Instituicao saveInstituicao(Instituicao instituicao);
	public Instituicao updateInstituicao(Instituicao instituicao);
	public List<Instituicao> findAll();
	public void deleteInstituicao(Instituicao instituicao);
	public Instituicao findById(Long id);
	public Instituicao findByEmail(String email);
	public boolean isEmailExist(String email);
	public List<Instituicao> findByEnderecoUf(Pageable pageable, String uf);
	public List<Instituicao> findByEnderecoUfAndSituacao(SituacaoInstituicao situacao, String uf);
	public List<Instituicao> findByEnderecoUfAndSituacaoPageable(Pageable pageable, SituacaoInstituicao situacao, String uf);
	public Instituicao reativarInstituicao(Instituicao instituicao);
	public List<Instituicao> findBySituacaoAndEnderecoUfAndCategoria(Pageable pageable,SituacaoInstituicao situacao, String uf, Categoria categoria );
	public Instituicao findBySituacaoAndNomeUsuario(SituacaoInstituicao situacao, String nomeUsuario);

}

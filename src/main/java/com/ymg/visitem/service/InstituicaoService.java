package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoInstituicao;
import com.ymg.visitem.repository.IRepositoryInstituicao;

@Service
public class InstituicaoService implements IInstituicaoService {
	@Autowired
	public IRepositoryInstituicao repositoryInstituicao;
	
	@Override
	public Instituicao saveInstituicao(Instituicao instituicao) {
		instituicao.setSituacao(SituacaoInstituicao.PENDENTE);
		return repositoryInstituicao.save(instituicao);
	}

	@Override
	public Instituicao updateInstituicao(Instituicao instituicao) {
		return repositoryInstituicao.saveAndFlush(instituicao);
	}

	@Override
	public List<Instituicao> findAll() {
		return repositoryInstituicao.findAll();
	}

	@Override
	public void deleteInstituicao(Instituicao instituicao) {
			instituicao.setSituacao(SituacaoInstituicao.INATIVO);
			repositoryInstituicao.saveAndFlush(instituicao);
		
	}

	@Override
	public Instituicao findById(Long id) {
		return repositoryInstituicao.getOne(id);
	}

	@Override
	public Instituicao findByEmail(String email) {
		
		return repositoryInstituicao.findByEmail(email);
	}
	
	public boolean isEmailExist(String email){
		List<Instituicao> instituicoes = repositoryInstituicao.findAll();
		for (Instituicao instituicao: instituicoes){
			System.out.println(instituicao.getEmail());
			if(instituicao.getEmail().equals(email)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Instituicao> findByEnderecoUf(Pageable pageable, String uf) {
		return repositoryInstituicao.findByEnderecoUf(pageable, uf);
	}

	@Override
	public List<Instituicao> findByEnderecoUfAndSituacao(SituacaoInstituicao situacao,String uf) {
		return repositoryInstituicao.findBySituacaoAndEnderecoUf(situacao, uf);
	}

	@Override
	public Instituicao reativarInstituicao(Instituicao instituicao) {
		instituicao.setSituacao(SituacaoInstituicao.PENDENTE);
		return repositoryInstituicao.saveAndFlush(instituicao);
	}

	@Override
	public List<Instituicao> findBySituacaoAndEnderecoUfAndCategoria(Pageable pageable, SituacaoInstituicao situacao,
			String uf, Categoria categoria) {
		return repositoryInstituicao.findBySituacaoAndEnderecoUfAndCategoria(pageable, situacao, uf, categoria);
	}

	@Override
	public List<Instituicao> findByEnderecoUfAndSituacaoPageable(Pageable pageable, SituacaoInstituicao situacao,
			String uf) {
		return repositoryInstituicao.findBySituacaoAndEnderecoUf(pageable,situacao, uf);
	}

	@Override
	public Instituicao findBySituacaoAndNomeUsuario(SituacaoInstituicao situacao, String nomeUsuario) {
		return repositoryInstituicao.findBySituacaoAndNomeUsuario(situacao, nomeUsuario);
	}
	

}

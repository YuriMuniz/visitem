package com.ymg.visitem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoInstituicao;

public interface IRepositoryInstituicao extends JpaRepository<Instituicao, Long> {
	
	Instituicao findByEmail(String email);
	List<Instituicao> findByEnderecoUf(Pageable pageable, String uf);
	List<Instituicao> findBySituacaoAndEnderecoUf(Pageable pageable,SituacaoInstituicao situacao, String uf );
	List<Instituicao> findBySituacaoAndEnderecoUf(SituacaoInstituicao situacao, String uf );
	List<Instituicao> findBySituacaoAndEnderecoUfAndCategoria(Pageable pageable,SituacaoInstituicao situacao, String uf, Categoria categoria );
	Instituicao findBySituacaoAndNomeUsuario(SituacaoInstituicao situacao, String nomeUsuario);

}

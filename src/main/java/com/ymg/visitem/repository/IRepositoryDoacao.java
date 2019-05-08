package com.ymg.visitem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.SituacaoDoacao;

public interface IRepositoryDoacao extends JpaRepository<Doacao, Long> {
	List<Doacao> findByInstituicao(Pageable page, Instituicao instiuicao);
	List<Doacao> findByInstituicaoAndSituacao(Instituicao instituicao, SituacaoDoacao situacao);
	List<Doacao> findByInstituicaoAndSituacaoOrderByDataCriacaoDesc(Pageable pageable,Instituicao instituicao, SituacaoDoacao situacao);
	List<Doacao> findByInstituicaoEnderecoUf(Pageable pageable, String uf);
	List<Doacao> findByInstituicaoEnderecoUfAndSituacao(Pageable pageable, String uf,SituacaoDoacao situacaoPresente);
	List<Doacao> findByHospede(Hospede hospede);
	List<Doacao> findByInstituicaoEnderecoUfAndInstituicaoCategoriaAndSituacao(Pageable pageable, String uf,Categoria categoria,SituacaoDoacao situacaoPresente);
	
	
}

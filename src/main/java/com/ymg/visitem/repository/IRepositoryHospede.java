package com.ymg.visitem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoHospede;

public interface IRepositoryHospede extends JpaRepository<Hospede, Long> {
	
	List<Hospede> findByInstituicao(Instituicao instituicao);
	List<Hospede> findByInstituicao(Pageable page, Instituicao instituicao);
	List<Hospede> findByInstituicaoAndSituacaoOrderByNome(Instituicao instituicao, SituacaoHospede situacao);
	List<Hospede> findByNomeAndSobrenomeAndInstituicaoAndDataNascimentoAndSituacao(String nome, String sobrenome, Instituicao instituicao, Date data, SituacaoHospede situacao);
	List<Hospede> findByInstituicaoAndSituacaoOrderByNome(Pageable pageable,Instituicao instituicao, SituacaoHospede situacao);
	List<Hospede> findByNomeContaining(String value);
	List<Hospede> findByNomeAndSobrenomeContaining(String nome, String sobrenome);
	List<Hospede> findByNomeContainingAndSobrenomeContainingAndInstituicaoAndSituacao(String nome, String sobrenome, Instituicao instituicao, SituacaoHospede situacao);
}

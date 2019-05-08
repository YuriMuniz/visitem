package com.ymg.visitem.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoHospede;

public interface IHospedeService {
	
	public Hospede findById(Long id);
	public Hospede saveHospede(Hospede hospede);
	public Hospede updateHospede(Hospede hospede);
	public List<Hospede> findAll();
	public void deleteHospede(Hospede hospede);
	public List<Hospede> findHospedeByNomeContaining(String value);
	public List<Hospede> findHospedeByNomeAndSobrenomeContaining(String nome, String sobrenome);
	public List<Hospede> findHospedeByInstituicao(Instituicao instituicao);
	public List<Hospede> findHospedeByInstituicao(Pageable page,Instituicao instituicao);
	public List<Hospede> findHospedeByInstituicaoAndSituacao(Instituicao instituicao, SituacaoHospede situacao);
	public List<Hospede> findHospedeByInstituicaoAndSituacaoPageable(Pageable pageable,Instituicao instituicao, SituacaoHospede situacao);
	List<Hospede> findByNomeAndSobrenomeContainingAndInstituicaoAndSituacao(String nome, String sobrenome, Instituicao instituicao, SituacaoHospede situacao);
	public List<Hospede> findHospedeByNomeAndSobrenomeAndInstituicaoAndDataNascimentoAndSituacao(String nome, String sobrenome, Instituicao instituicao, Date data, SituacaoHospede situacao);
 }

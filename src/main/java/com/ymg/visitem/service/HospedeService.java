package com.ymg.visitem.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.SituacaoHospede;
import com.ymg.visitem.repository.IRepositoryHospede;

@Service
public class HospedeService implements IHospedeService {
	
	@Autowired
	public IRepositoryHospede hospedeRepository;
	
	@Override
	public Hospede saveHospede(Hospede hospede) {
		hospede.setSituacao(SituacaoHospede.ATIVO);
		return hospedeRepository.save(hospede);
	}

	@Override
	public Hospede updateHospede(Hospede hospede) {
		return hospedeRepository.saveAndFlush(hospede);
	}

	@Override
	public List<Hospede> findAll() {
		return hospedeRepository.findAll();
	}

	@Override
	public void deleteHospede(Hospede hospede) {
		hospede.setSituacao(SituacaoHospede.INATIVO);
		hospedeRepository.saveAndFlush(hospede);
		
	}
	
	@Override
	public List<Hospede> findHospedeByInstituicao(Instituicao instituicao) {
		return hospedeRepository.findByInstituicao(instituicao);
	}

	@Override
	public List<Hospede> findHospedeByInstituicao(Pageable page, Instituicao instituicao) {
		return hospedeRepository.findByInstituicao(page,instituicao);
	}

	@Override
	public List<Hospede> findHospedeByInstituicaoAndSituacao(Instituicao instituicao,
			SituacaoHospede situacao) {
		return hospedeRepository.findByInstituicaoAndSituacaoOrderByNome(instituicao, situacao);
	}

	@Override
	public List<Hospede> findHospedeByNomeAndSobrenomeAndInstituicaoAndDataNascimentoAndSituacao(String nome,
			String sobrenome, Instituicao instituicao, Date data, SituacaoHospede situacao) {
		return hospedeRepository.findByNomeAndSobrenomeAndInstituicaoAndDataNascimentoAndSituacao(nome, sobrenome, instituicao, data, situacao);
	}

	@Override
	public Hospede findById(Long id) {
		return hospedeRepository.getOne(id);
	}

	@Override
	public List<Hospede> findHospedeByInstituicaoAndSituacaoPageable(Pageable pageable, Instituicao instituicao,
			SituacaoHospede situacao) {
		return hospedeRepository.findByInstituicaoAndSituacaoOrderByNome(pageable,instituicao, situacao);
	}

	@Override
	public List<Hospede> findHospedeByNomeContaining(String value) {
		return hospedeRepository.findByNomeContaining(value);
	}

	@Override
	public List<Hospede> findHospedeByNomeAndSobrenomeContaining(String nome, String sobrenome) {
		return hospedeRepository.findByNomeAndSobrenomeContaining(nome, sobrenome);
	}

	@Override
	public List<Hospede> findByNomeAndSobrenomeContainingAndInstituicaoAndSituacao(String nome, String sobrenome,
			Instituicao instituicao, SituacaoHospede situacao) {
		return hospedeRepository.findByNomeContainingAndSobrenomeContainingAndInstituicaoAndSituacao(nome, sobrenome, instituicao, situacao);
	}

	
	

}

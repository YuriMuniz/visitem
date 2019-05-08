package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.DoacaoAceita;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.repository.IRepositoryDoacaoAceita;

@Service
public class DoacaoAceitaService implements IDoacaoAceitaService {
	
	@Autowired
	public IRepositoryDoacaoAceita doacaoAceitaRepository;
	
	@Override
	public DoacaoAceita saveDoacaoAceito(DoacaoAceita presenteAceito) {
		
		
		return doacaoAceitaRepository.save(presenteAceito);
	}

	@Override
	public DoacaoAceita updateDoacaoAceito(DoacaoAceita presenteAceito) {
		return doacaoAceitaRepository.saveAndFlush(presenteAceito);
	}

	@Override
	public List<DoacaoAceita> findAll() {
		return doacaoAceitaRepository.findAll();
	}

	@Override
	public void deleteDoacaoAceito(DoacaoAceita presenteAceito) {
		doacaoAceitaRepository.delete(presenteAceito);
		
	}

	@Override
	public DoacaoAceita findByDoacao(Doacao presente) {
		return doacaoAceitaRepository.findByDoacao(presente);
	}


	@Override
	public List<DoacaoAceita> findByInstituicao(Pageable pageable, Instituicao instituicao,
			SituacaoDoacao situacao) {
		return doacaoAceitaRepository.findByDoacaoInstituicaoAndDoacaoSituacao(pageable, instituicao , situacao);
	}

	@Override
	public List<DoacaoAceita> findByUsuarioAndDoacaoSituacao(Usuario usuario,
			SituacaoDoacao situacao) {
		return doacaoAceitaRepository.findByUsuarioAndDoacaoSituacao(usuario, situacao);
	}

	@Override
	public List<DoacaoAceita> findByUsuario(Usuario usuario) {
		return doacaoAceitaRepository.findByUsuarioOrderByDataAceitacaoDesc(usuario);
	}

	@Override
	public DoacaoAceita findById(Long id) {
		return doacaoAceitaRepository.getOne(id);
	}

}

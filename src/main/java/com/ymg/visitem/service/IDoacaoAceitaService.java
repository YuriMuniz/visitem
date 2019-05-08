package com.ymg.visitem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.DoacaoAceita;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.entity.Usuario;

public interface IDoacaoAceitaService {
	public DoacaoAceita saveDoacaoAceito(DoacaoAceita doacaoAceita);
	public DoacaoAceita updateDoacaoAceito(DoacaoAceita doacaoAceita);
	public List<DoacaoAceita> findAll();
	public void deleteDoacaoAceito(DoacaoAceita doacaoAceita);
	public DoacaoAceita findByDoacao(Doacao doacao);
	public List<DoacaoAceita> findByInstituicao (Pageable pageable, Instituicao instituicao, SituacaoDoacao situacao);
	public List<DoacaoAceita> findByUsuarioAndDoacaoSituacao(Usuario usuario, SituacaoDoacao situacao);
	public List<DoacaoAceita> findByUsuario(Usuario usuario);
	public DoacaoAceita findById(Long id);
	
}

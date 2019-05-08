package com.ymg.visitem.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.DoacaoAceita;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.entity.Usuario;

public interface IRepositoryDoacaoAceita extends JpaRepository<DoacaoAceita, Long> {
	public DoacaoAceita findByDoacao(Doacao doacao);
	public List<DoacaoAceita> findByDoacaoInstituicao(Pageable pageable, Instituicao instituicao);
	public List<DoacaoAceita> findByDoacaoInstituicaoAndDoacaoSituacao(Pageable pageable, Instituicao instituicao, SituacaoDoacao situacao);
	public List<DoacaoAceita> findByUsuarioAndDoacaoSituacao(Usuario usuario, SituacaoDoacao situacao);
	public List<DoacaoAceita> findByUsuarioOrderByDataAceitacaoDesc(Usuario usuario);
}

package com.ymg.visitem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.SituacaoDoacao;

import com.ymg.visitem.form.DoacaoCreateForm;

import org.springframework.web.multipart.MultipartFile;

public interface IDoacaoService {
	public Doacao saveDoacao(Doacao doacao);
	public Doacao addDoacao(MultipartFile photo,DoacaoCreateForm presente);
	public Doacao getById(Long id);
	
	public Doacao updateDoacao(Doacao presente);
	public List<Doacao> findAll();
	public void deleteDoacao(Doacao presente);
	List<Doacao> findByInstituicao(Pageable page, Instituicao instiuicao);
	public List<Doacao> findByInstituicaoAndSituacao(Instituicao instituicao, SituacaoDoacao situacao);
	public List<Doacao> findByInstituicaoAndSituacao(Pageable pageable,Instituicao instituicao, SituacaoDoacao situacao);
	public List<Doacao> findByInstituicaoUf(Pageable pageable, String uf);
	public List<Doacao> findByInstituicaoUfAndSituacao(Pageable pageable, String uf,SituacaoDoacao situacaoDoacao);
	public List<Doacao> findByInstituicaoUfAndInstituicaoCategoriaAndSituacao(Pageable pageable, String uf, Categoria categoria,SituacaoDoacao situacaoDoacao);
	public List<Doacao> findByHospede(Hospede hospede);
}

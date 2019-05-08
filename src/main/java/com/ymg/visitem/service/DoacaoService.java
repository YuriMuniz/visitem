package com.ymg.visitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.repository.IRepositoryHospede;
import com.ymg.visitem.repository.IRepositoryInstituicao;
import com.ymg.visitem.repository.IRepositoryDoacao;
import com.ymg.visitem.repository.IRepositoryUsuario;
import com.ymg.visitem.storage.PictureSave;
import com.ymg.visitem.util.Dates;

import com.ymg.visitem.form.DoacaoCreateForm;


@Service
public class DoacaoService implements IDoacaoService {
	
	@Autowired
	public IRepositoryDoacao doacaoRepository;
	
	@Autowired
	public IRepositoryUsuario usuarioRepository;
	
	@Autowired
	public IRepositoryInstituicao instituicaoRepository;
	
	@Autowired 
	public IRepositoryHospede hospedeRepository;
	
	
	@Override
	public Doacao saveDoacao(Doacao doacao) {
		return doacaoRepository.save(doacao);
	}

	@Override
	public Doacao updateDoacao(Doacao doacao) {
		return doacaoRepository.saveAndFlush(doacao);
	}

	@Override
	public List<Doacao> findAll() {
		return doacaoRepository.findAll();
	}

	@Override
	public void deleteDoacao(Doacao doacao) {
		doacaoRepository.delete(doacao);
		
	}

	@Override
	public List<Doacao> findByInstituicaoAndSituacao(Instituicao instituicao, SituacaoDoacao situacao) {
		return doacaoRepository.findByInstituicaoAndSituacao(instituicao, situacao);
	}

	@Override
	public List<Doacao> findByInstituicaoAndSituacao(Pageable pageable, Instituicao instituicao,
			SituacaoDoacao situacao) {
		return doacaoRepository.findByInstituicaoAndSituacaoOrderByDataCriacaoDesc(pageable, instituicao, situacao);
	}

	@Override
	public Doacao getById(Long id) {
		return doacaoRepository.getOne(id);
	}

	@Override
	public List<Doacao> findByInstituicaoUf(Pageable pageable, String uf) {
		return doacaoRepository.findByInstituicaoEnderecoUf(pageable, uf);
	}
	
	@Override
	public List<Doacao> findByHospede(Hospede beneficiado) {
		return doacaoRepository.findByHospede(beneficiado);
	}

	@Override
	public Doacao addDoacao(MultipartFile photo, DoacaoCreateForm doacao) {
		String photoPath = PictureSave.sendPicture(photo, getLoggedPersonEmail());
		Instituicao instituicao = instituicaoRepository.getOne(Long.parseLong(doacao.getIdInstituicao()));
		Hospede beneficiado = hospedeRepository.getOne(Long.parseLong(doacao.getIdHospede()));
		
		Doacao doacaoNovo = new Doacao();
		doacaoNovo.setNome(doacao.getNome());
		doacaoNovo.setDescricao(doacao.getDescricao());
		doacaoNovo.setPhoto(photoPath);
		doacaoNovo.setInstituicao(instituicao);
		doacaoNovo.setHospede(beneficiado);
		doacaoNovo.setSituacao(SituacaoDoacao.ABERTO);
		doacaoNovo.setDataCriacao(Dates.obterTimestampAtual());
		
		return doacaoRepository.saveAndFlush(doacaoNovo);
		
	}
	
	private String getLoggedPersonEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public List<Doacao> findByInstituicaoUfAndSituacao(Pageable pageable, String uf,
			SituacaoDoacao situacaoDoacao) {
		return doacaoRepository.findByInstituicaoEnderecoUfAndSituacao(pageable, uf, situacaoDoacao);
	}

	@Override
	public List<Doacao> findByInstituicaoUfAndInstituicaoCategoriaAndSituacao(Pageable pageable, String uf,
			Categoria categoria, SituacaoDoacao situacaoDoacao) {
		return doacaoRepository.findByInstituicaoEnderecoUfAndInstituicaoCategoriaAndSituacao(pageable, uf,categoria, situacaoDoacao);
	}

	@Override
	public List<Doacao> findByInstituicao(Pageable page, Instituicao instiuicao) {
		return doacaoRepository.findByInstituicao(page, instiuicao);
	}

	

	


}

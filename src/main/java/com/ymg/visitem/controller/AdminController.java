package com.ymg.visitem.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Profile;
import com.ymg.visitem.entity.RandomString;
import com.ymg.visitem.entity.Role;
import com.ymg.visitem.entity.SituacaoInstituicao;
import com.ymg.visitem.entity.SituacaoUsuario;
import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.service.IEnderecoService;
import com.ymg.visitem.service.IInstituicaoService;
import com.ymg.visitem.service.IProfileService;
import com.ymg.visitem.service.ISendEmailService;
import com.ymg.visitem.service.ITelefoneService;
import com.ymg.visitem.service.IUsuarioService;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;



@Controller
public class AdminController {
	@Autowired
	public IEnderecoService enderecoService;

	@Autowired
	public IInstituicaoService instituicaoService;

	@Autowired
	public ITelefoneService telefoneService;

	@Autowired
	public IUsuarioService usuarioService;

	@Autowired
	public IProfileService profileService;

	@Autowired
	ISendEmailService sendEmailService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/admin/index", method=RequestMethod.GET)
	public ModelAndView adminIndex(){
		ModelAndView mv = new ModelAndView("/admin/admin-index");
		Profile profile = getUserProfile(getLoggedUser());
		
		mv.addObject("profile", profile);
		mv.addObject("usuario", getLoggedUser());
		
		return mv;
		
	}
	
	@RequestMapping(value = "/admin/instituicao/add", method = RequestMethod.GET)
	public ModelAndView addInstituicao() {
		ModelAndView mv = new ModelAndView("/instituicao/admin");
		return mv;
	}
	
//	@PreAuthorize("hasAuthority('ADMIN')")
//	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
//	public ModelAndView index() {
//		ModelAndView mv = new ModelAndView("/admin/admin-index");
//		return mv;
//	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/admin/find-inst-id", method = RequestMethod.GET)
	public @ResponseBody Instituicao findInstById(HttpServletRequest request){
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		System.out.println(instituicao.getDataCriacao());
		return instituicao;
	}
	
	//@PreAuthorize("hasAuthority('ANONIMOUS')")
	@RequestMapping(value="/salvar-admin", method=RequestMethod.GET)
	public ModelAndView salvarInstituicao(){
		ModelAndView mv = new ModelAndView("/inserir-admin");
		return mv;
	}
	

	//@PreAuthorize("hasAuthority('ANONIMOUS')")
	@RequestMapping(value = "/add-admin", method = RequestMethod.POST)
	public @ResponseBody String inserirAdmin() {

		Usuario usuario = new Usuario();
		usuario.setEmail("admin@gmail.com");
		usuario.setSenhaHash("1234");
		usuario.setNome("Yuri");
		usuario.setSituacao(SituacaoUsuario.ATIVO);
		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);

		Profile profile = new Profile();
		profile.setRole(Role.ADMIN);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);
		return "Usuario adicionao.";
	}
	
	//@PreAuthorize("hasAuthority('ANONIMOUS')")
	@RequestMapping(value = "/add-admin", method = RequestMethod.GET)
	public @ResponseBody String inserirAdmin2() {

		Usuario usuario = new Usuario();
		usuario.setEmail("admin@gmail.com");
		usuario.setSenhaHash("1234");
		usuario.setNome("Yuri");
		usuario.setSituacao(SituacaoUsuario.ATIVO);
		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);

		Profile profile = new Profile();
		profile.setRole(Role.ADMIN);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);
		return "Usuario adicionao.";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/instituicao/list-instituicoes-page", method = RequestMethod.GET)
	public @ResponseBody List<Instituicao> listInstituicaoPage(@RequestParam(defaultValue = "0") int value, HttpServletRequest request) {
		Pageable pagina = null;
		
		String uf = request.getParameter("uf");

		switch (value) {
		case 1:
			pagina = new PageRequest(0, 25);
			break;
		case 2:
			pagina = new PageRequest(1, 25);
			break;
		case 3:
			pagina = new PageRequest(2, 25);
			break;
		case 4:
			pagina = new PageRequest(3, 25);
			break;
		case 5:
			pagina = new PageRequest(4, 25);
			break;
		case 6:
			pagina = new PageRequest(5, 25);
			break;
		case 7:
			pagina  = new PageRequest(6, 25);
			break;
		case 8:
			pagina = new PageRequest(7, 25);
			break;
		}

		List<Instituicao> instituicoes = instituicaoService.findByEnderecoUf(pagina, uf);
		return instituicoes;

	}
	
	
	
	@RequestMapping(value = "/instituicao/list-instituicoes-situacao", method = RequestMethod.GET)
	public @ResponseBody List<Instituicao> listInstituicaoPageSituacao(HttpServletRequest request) {
		
		String uf = request.getParameter("uf");
		String situacao = request.getParameter("situacao");
		SituacaoInstituicao situacaoInstituicao = null;
		if(situacao.equals("ATIVO")){
			situacaoInstituicao = SituacaoInstituicao.ATIVO;
		}
		if(situacao.equals("PENDENTE")){
			situacaoInstituicao = SituacaoInstituicao.PENDENTE;
		}
		if(situacao.equals("INATIVO")){
			situacaoInstituicao = SituacaoInstituicao.INATIVO;
		}
		
		if(situacao.equals("AGUARDANDO")){
			situacaoInstituicao = SituacaoInstituicao.AGUARDANDO;
		}
		List<Instituicao> instituicoes = instituicaoService.findByEnderecoUfAndSituacao(situacaoInstituicao, uf);
		return instituicoes;

	}
	
	@RequestMapping(value="/admin/inativar-instituicao", method = RequestMethod.POST)
	public @ResponseBody String inativarInstituicao(HttpServletRequest request){
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		instituicao.setSituacao(SituacaoInstituicao.INATIVO);
		instituicaoService.updateInstituicao(instituicao);
		return "A instituição " + instituicao.getNome() + " foi inativa!";
		
	}
	
	@RequestMapping(value="/admin/reativar-instituicao", method = RequestMethod.POST)
	public @ResponseBody String reativarInstituicao(HttpServletRequest request){
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		instituicao.setSituacao(SituacaoInstituicao.PENDENTE);
		instituicaoService.updateInstituicao(instituicao);
		return "A instituição " + instituicao.getNome() + " foi redefinida para Pendente!";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/validar-cadastro-instituicao", method = RequestMethod.POST)
	public @ResponseBody String validarCadastro(HttpServletRequest request) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		instituicao.setSituacao(SituacaoInstituicao.AGUARDANDO);
		instituicaoService.updateInstituicao(instituicao);
		
		String email = instituicao.getEmail();
		//String dataCriacao = instituicao.getDataCriacao().toString();
		String nome = instituicao.getNome();
		
		RandomString random = new RandomString();
		String randomString = random.randomString(57);
		String randomString2  = random.randomString(13);
		
		String link = "http://localhost:8080/finalizar-cadastro-instituicao?value="  + randomString + id + randomString2;
		String subject = "Confirmar Cadastro Visitem";
		
		sendEmailService.sendEmailValidarCadastro(email, nome, subject, link);
		return "Email enviado com sucesso";
		
	}
	
	private String getLoggedPersonEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	private Usuario getLoggedUser() {
		return usuarioService.findByEmail(getLoggedPersonEmail());
	}
	
	private Profile getUserProfile(Usuario usuario) {
		return profileService.findByUsuarioEmail(usuario.getEmail());
	}


}

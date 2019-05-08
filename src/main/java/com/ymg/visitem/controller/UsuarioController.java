package com.ymg.visitem.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.PrazoEscolhido;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.DoacaoAceita;
import com.ymg.visitem.entity.Profile;
import com.ymg.visitem.entity.Role;
import com.ymg.visitem.entity.SituacaoInstituicao;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.entity.Uf;
import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.service.IHospedeService;
import com.ymg.visitem.service.IEnderecoService;
import com.ymg.visitem.service.IInstituicaoService;
import com.ymg.visitem.service.IDoacaoAceitaService;
import com.ymg.visitem.service.IDoacaoService;
import com.ymg.visitem.service.IProfileService;
import com.ymg.visitem.service.ISendEmailService;
import com.ymg.visitem.service.ITelefoneService;
import com.ymg.visitem.service.IUsuarioService;
import com.ymg.visitem.util.Dates;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Controller
public class UsuarioController {

	@Autowired
	public IEnderecoService enderecoService;

	@Autowired
	public IInstituicaoService instituicaoService;

	@Autowired
	public ITelefoneService telefoneService;
	
	@Autowired
	public IHospedeService hospedeService;

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IProfileService profileService;

	@Autowired
	IDoacaoService doacaoService;

	@Autowired
	IDoacaoAceitaService doacaoAceitoService;

	@Autowired
	ISendEmailService sendEmailService;

	@RequestMapping(value = "/usuario/cadastrar-usuario", method = RequestMethod.GET)
	public ModelAndView novoUsuario() {
		ModelAndView mv = new ModelAndView("/usuario/cadastrar-usuario");
		return mv;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView page() {
		ModelAndView mv = new ModelAndView("/index-anonimo");
		return mv;
	}

	@RequestMapping(value = "/usuario/add", method = RequestMethod.POST)
	public @ResponseBody String salvarAjax(HttpServletRequest request) {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		if (nome.equals("")) {
			return "Campo nome precisa ser preenchido!";
		}

		if (email.equals("")) {
			return "Campo email precisa ser preenchido!";
		}
		if (senha.equals("")) {
			return "Campo senha precisa ser preenchido!";
		}

		if (usuarioService.findByEmail(email) != null) {
			return "Esse email já esta cadastrado.";
		}

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenhaHash(senha);

		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
		Profile profile = new Profile();
		profile.setRole(Role.USER);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);

		return "Usuario cadastrado.";

	}

	@RequestMapping(value = "/usuario/cadastrar-usuario-facebook", method = RequestMethod.POST)
	public String cadastrarUsuarioFacebook(@RequestParam(value = "nome") String nome,
			@RequestParam("senha") String senha, @RequestParam("email") String email,
			@RequestParam("socialProfile") String socialProfile, @RequestParam String id) {

		if (nome.equals("") || email.equals("") || senha.equals("") || socialProfile.equals("")) {
			return "redirect:/connect/erro-cadastro";
		}

		if (usuarioService.findByEmail(email) != null) {
			return "redirect:/connect/erro-cadastro-email-existente";
		}

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenhaHash(senha);
		usuario.setSocialProfile(socialProfile);
		usuario.setFoto("http://graph.facebook.com/" + id + "/picture?type=large");

		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
		Profile profile = new Profile();
		profile.setRole(Role.USER);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);

		return "redirect:/verificar-usuario";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		Profile profile = getUserProfile(getLoggedUser());

		if (profile.getRole().equals(Role.INST)) {
			return "redirect:/instituicao/index";
		}
		if (profile.getRole().equals(Role.USER)) {
			return "redirect:/buscar-doacoes";
		}
		if (profile.getRole().equals(Role.ADMIN)) {
			return "redirect:/admin/index";
		}
		return "";
	}
	
	
	@RequestMapping(value="/buscar-doacoes", method=RequestMethod.GET)
	public ModelAndView usuarioIndex(){
		ModelAndView mv = new ModelAndView("/usuario/usuario-index");
		if(getLoggedUser()!=null){
			mv.addObject("usuario", getLoggedUser());
		}else{
			Usuario usuario = new Usuario();
			mv.addObject("usuario", usuario );
		}
		
		
		return mv;
		
	}


	
	@RequestMapping(value = "/pedidos", method = RequestMethod.GET)
	public @ResponseBody List<Doacao> pedidos(HttpServletRequest request) {
		String page = request.getParameter("pagina");
		Pageable pagina = new PageRequest(Integer.parseInt(page), 10);
		String uf = request.getParameter("uf");
		String categoria = request.getParameter("categoria");
		Categoria cat = null;
		System.out.println(categoria);
		if (categoria.equals("TODOS")) {
			return doacaoService.findByInstituicaoUfAndSituacao(pagina, uf, SituacaoDoacao.ABERTO);
		}

		if (categoria.equals("CRECHE")) {
			cat = Categoria.CRECHE;
		}
		if (categoria.equals("ASILO")) {
			cat = Categoria.ASILO;
		}
		if (categoria.equals("ORFANATO")) {
			cat = Categoria.ORFANATO;
		}

		if (cat != null) {
			return doacaoService.findByInstituicaoUfAndInstituicaoCategoriaAndSituacao(pagina, uf, cat,
					SituacaoDoacao.ABERTO);
		}

		return null;
	}

	// @RequestMapping(value="/usuario/endereco/save", method=
	// RequestMethod.POST)
	// public @ResponseBody String salvarEndereco(HttpServletRequest request){
	// String cep = request.getParameter("cep");
	// String logradouro = request.getParameter("logradouro");
	// String bairro = request.getParameter("bairro");
	// String cidade = request.getParameter("cidade");
	// String uf = request.getParameter("uf");
	// String pais = "Brasil";
	// String numero = request.getParameter("numero");
	// String complemento = request.getParameter("complemento");
	// String pontoReferencia = request.getParameter("pontoReferencia");
	//
	// Endereco endereco = new Endereco();
	// endereco.setCep(cep);
	// endereco.setLogradouro(logradouro);
	// endereco.setBairro(bairro);
	// endereco.setCidade(cidade);
	// endereco.setUf(uf);
	// endereco.setPais(pais);
	// endereco.setNumero(numero);
	// endereco.setComplemento(complemento);
	// endereco.setPontoReferencia(pontoReferencia);
	//
	// Endereco enderecoSalvo = enderecoService.saveEndereco(endereco);
	// String idEndereco = enderecoSalvo.getId().toString();
	// return idEndereco;
	//
	// }


	@RequestMapping(value = "/list-uf", method = RequestMethod.GET)
	public @ResponseBody List<String> uf() {
		List<String> lista = new ArrayList<>();
		for (Uf uf : Uf.values()) {
			lista.add(uf.toString());
		}
		return lista;
	}

	@RequestMapping(value = "/get-id-instituicao-logged", method = RequestMethod.GET)
	public @ResponseBody String getIdInstituicaoLogged() {
		return getLoggedInstituicao().getId().toString();
	}



	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/pedido/{id}", method = RequestMethod.GET)
	public ModelAndView detalhe(@PathVariable("id") String id) {

		ModelAndView mv = new ModelAndView("/usuario/pedido");
		Doacao doacao = doacaoService.getById(Long.parseLong(id));
		if (doacao.getSituacao().equals(SituacaoDoacao.ABERTO)) {
			mv.addObject("isAberto", true);
		} else {
			mv.addObject("isAberto", false);
		}
		mv.addObject("pedido", doacao);
		mv.addObject("usuario", getLoggedUser());

		return mv;

	}

	
	@RequestMapping(value = "/usuario/enviar-email-confirmacao-entrega", method = RequestMethod.POST)
	public @ResponseBody String enviarEmailConfirmacaoEntrega(HttpServletRequest request)
			throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException {
		Usuario usuario = getLoggedUser();
		String emailInstituicao = request.getParameter("emailInstituicao");
		String nomeInstituicao = request.getParameter("nomeInstituicao");
		String doacao = request.getParameter("nomeDoacao");
		String hospede = request.getParameter("nomeHospede");
		String dataAceitacao = request.getParameter("dataAceitacao");
		String subject = "Confirmar entrega de doação";

		sendEmailService.sendEmailConfirmacaoEntrega(emailInstituicao, nomeInstituicao, subject, usuario.getNome(),
				doacao, hospede, dataAceitacao);
		return "Email enviado com sucesso";

	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/usuario/pedido-aceito", method = RequestMethod.POST)
	public @ResponseBody String saveDoacaoAceito(HttpServletRequest request) {
		Usuario usuario = getLoggedUser();
		String prazo = request.getParameter("prazo");
		String idDoacao = request.getParameter("idDoacao");
		Doacao doacao = doacaoService.getById(Long.parseLong(idDoacao));

		if (prazo == null) {
			return "Necessário escolher prazo.";
		}
		if (idDoacao == null) {
			return "Erro ao pegar doacao!";
		}
		if (doacao == null) {
			return "Erro ao pegar doacao!";
		}

		PrazoEscolhido prazoEscolhido = null;

		if (prazo.equals("1")) {
			prazoEscolhido = PrazoEscolhido.UM;
		}
		if (prazo.equals("2")) {
			prazoEscolhido = PrazoEscolhido.DOIS;
		}
		if (prazo.equals("3")) {
			prazoEscolhido = PrazoEscolhido.TRES;
		}
		if (prazo.equals("4")) {
			prazoEscolhido = PrazoEscolhido.QUATRO;
		}
		if (prazo.equals("5")) {
			prazoEscolhido = PrazoEscolhido.CINCO;
		}
		if (prazo.equals("6")) {
			prazoEscolhido = PrazoEscolhido.SEIS;
		}
		if (prazo.equals("7")) {
			prazoEscolhido = PrazoEscolhido.SETE;
		}

		DoacaoAceita doacaoAceito = new DoacaoAceita();
		doacaoAceito.setDoacao(doacao);
		doacaoAceito.setPrazo(prazoEscolhido);
		doacaoAceito.setUsuario(usuario);
		doacaoAceito.setDataAceitacao(Dates.obterTimestampAtual());
		if (!existeDoacaoAceito(getLoggedUser())) {
			doacaoAceitoService.saveDoacaoAceito(doacaoAceito);

			doacao.setSituacao(SituacaoDoacao.ACEITO);
			doacaoService.updateDoacao(doacao);
		} else {
			return "1";
		}

		return "Você atendeu o pedido " + doacao.getNome() + "para " + doacao.getHospede().getNome() + " "
				+ doacao.getHospede().getSobrenome() + " no local: " + doacao.getInstituicao().getNome() + "!";

	}

	private boolean existeDoacaoAceito(Usuario usuario) {
		List<DoacaoAceita> doacaosAceitosUsuario = doacaoAceitoService.findByUsuarioAndDoacaoSituacao(usuario,
				SituacaoDoacao.ACEITO);
		if (doacaosAceitosUsuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "/usuario/meus-pedidos", method = RequestMethod.GET)
	public @ResponseBody List<DoacaoAceita> meusPedidos() {
		return doacaoAceitoService.findByUsuario(getLoggedUser());
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/usuario/suas-doacoes", method = RequestMethod.GET)
	public ModelAndView pedidosAceitos() {
		ModelAndView mv = new ModelAndView("/usuario/pedidos-aceitos");
		List<DoacaoAceita> doacaosAceitos = doacaoAceitoService.findByUsuario(getLoggedUser());

		mv.addObject("usuario", getLoggedUser());
		mv.addObject("minhasdoacoes", doacaosAceitos);
		return mv;
	}

	@RequestMapping(value = "/usuario/confirmar-entrega-doacao", method = RequestMethod.POST)
	public @ResponseBody String confirmarEntregaDoacao(HttpServletRequest request)
			throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException {
		Usuario usuario = getLoggedUser();
		String id = request.getParameter("id");
		DoacaoAceita doacaoAceito = doacaoAceitoService.findById(Long.parseLong(id));
		String emailInstituicao = doacaoAceito.getDoacao().getInstituicao().getEmail();
		String nomeInstituicao = doacaoAceito.getDoacao().getInstituicao().getNome();
		String doacao = doacaoAceito.getDoacao().getNome();
		String hospede = doacaoAceito.getDoacao().getHospede().getNome() + " "
				+ doacaoAceito.getDoacao().getHospede().getSobrenome();
		String dataAceitacao = doacaoAceito.getDataAceitacao().toString();
		String subject = "Confirmar entrega de doação";
		sendEmailService.sendEmailConfirmacaoEntrega(emailInstituicao, nomeInstituicao, subject, usuario.getNome(),
				doacao, hospede, dataAceitacao);
		doacaoAceito.getDoacao().setSituacao(SituacaoDoacao.ENTREGUE);
		doacaoAceitoService.updateDoacaoAceito(doacaoAceito);

		return "Confirmado";
	}

	@RequestMapping(value = "/get-logged-user", method = RequestMethod.GET)
	public @ResponseBody Usuario usuarioLogado() {
		return getLoggedUser();
	}

	@RequestMapping(value = "/usuario/teste", method = RequestMethod.GET)
	public ModelAndView teste() {
		ModelAndView mv = new ModelAndView("/usuario/teste");
		return mv;
	}
	
	@RequestMapping(value ="/instituicoes", method=RequestMethod.GET)
	public ModelAndView pageInstituicoes(){
		ModelAndView mv = new ModelAndView("/usuario/instituicoes");
		mv.addObject("usuario", getLoggedUser());
		return mv;
	}
	
	@RequestMapping(value="/instituicoes/{nomeUsuario}" , method=RequestMethod.GET)
	public ModelAndView instituicaoPagina(@PathVariable(value="nomeUsuario") String nomeUsuario){
		ModelAndView mv = new ModelAndView("/usuario/instituicao-pagina");
		Instituicao instituicao = instituicaoService.findBySituacaoAndNomeUsuario(SituacaoInstituicao.ATIVO, nomeUsuario);
		List<Hospede> hospedes = hospedeService.findHospedeByInstituicao(instituicao);
		mv.addObject("instituicao", instituicao);
		mv.addObject("usuario", getLoggedUser());
		mv.addObject("hospedes", hospedes);
		return mv;
	}
	
	@RequestMapping(value="/listar-hospedes-por-instituicao", method=RequestMethod.GET)
	public @ResponseBody List<Hospede> hospedes(HttpServletRequest request){
		String id = request.getParameter("id");
		String page = request.getParameter("pagina");
		Pageable pagina = new PageRequest(Integer.parseInt(page), 10);
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		List<Hospede> hospedes = hospedeService.findHospedeByInstituicao(pagina,instituicao);
		return hospedes;
		
		
	}
	
	
	@RequestMapping(value="/listar-doacoes-por-instituicao", method=RequestMethod.GET)
	public @ResponseBody List<Doacao> doacoes(HttpServletRequest request){
		String id = request.getParameter("id");
		String page = request.getParameter("pagina");
		Pageable pagina = new PageRequest(Integer.parseInt(page), 10);
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		List<Doacao> doacoes = doacaoService.findByInstituicao(pagina,instituicao);
		return doacoes;
		
		
	}
			
	
	
	@RequestMapping(value="/instituicoes-estado", method=RequestMethod.POST)
	public @ResponseBody List<Instituicao> instituicoes(HttpServletRequest request){
		String uf  = request.getParameter("uf");
		String page = request.getParameter("pagina");
		Pageable pagina =new PageRequest(Integer.parseInt(page), 10);
		Categoria cat = null;
		String categoria = request.getParameter("categoria");
		
		if (categoria.equals("TODOS")) {
			return instituicaoService.findByEnderecoUfAndSituacaoPageable(pagina, SituacaoInstituicao.ATIVO, uf);
		}

		if (categoria.equals("CRECHE")) {
			cat = Categoria.CRECHE;
		}
		if (categoria.equals("ASILO")) {
			cat = Categoria.ASILO;
		}
		if (categoria.equals("ORFANATO")) {
			cat = Categoria.ORFANATO;
		}

		if (cat != null) {
			return instituicaoService.findBySituacaoAndEnderecoUfAndCategoria(pagina,SituacaoInstituicao.ATIVO, uf, cat);
		}

		return null;
		
	}
	
	private Profile getUserProfile(Usuario usuario) {
		return profileService.findByUsuarioEmail(usuario.getEmail());
	}

	private String getLoggedPersonEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	private Usuario getLoggedUser() {
		return usuarioService.findByEmail(getLoggedPersonEmail());
	}

	private Instituicao getLoggedInstituicao() {
		Instituicao instituicao = instituicaoService.findByEmail(getLoggedPersonEmail());
		return instituicao;
	}

}

package com.ymg.visitem.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.AddressException;

//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.ymg.visitem.entity.Hospede;
import com.ymg.visitem.entity.Categoria;
import com.ymg.visitem.entity.Endereco;
import com.ymg.visitem.entity.HorarioVisita;
import com.ymg.visitem.entity.Instituicao;
import com.ymg.visitem.entity.Doacao;
import com.ymg.visitem.entity.DoacaoAceita;
import com.ymg.visitem.entity.Profile;
import com.ymg.visitem.entity.RandomString;
import com.ymg.visitem.entity.Role;
import com.ymg.visitem.entity.Sexo;
import com.ymg.visitem.entity.SituacaoInstituicao;
import com.ymg.visitem.entity.SituacaoDoacao;
import com.ymg.visitem.entity.SituacaoHospede;
import com.ymg.visitem.entity.Telefone;
import com.ymg.visitem.entity.TurnoHorarioFuncionamento;
import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.service.IHospedeService;
import com.ymg.visitem.service.IEnderecoService;
import com.ymg.visitem.service.IHorarioVisitaService;
import com.ymg.visitem.service.IInstituicaoService;
import com.ymg.visitem.service.IDoacaoAceitaService;
import com.ymg.visitem.service.IDoacaoService;
import com.ymg.visitem.service.IProfileService;
import com.ymg.visitem.service.ISendEmailService;
import com.ymg.visitem.service.ITelefoneService;
import com.ymg.visitem.service.IUsuarioService;
import com.ymg.visitem.storage.PictureSave;
import com.ymg.visitem.storage.StorageServiceLocal;
import com.ymg.visitem.util.Dates;

import com.ymg.visitem.form.DoacaoCreateForm;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;



@Controller
public class InstituicaoController {
	
	@Autowired
	public ITelefoneService telefoneService;
	
	@Autowired
	public IEnderecoService enderecoService;
	
	@Autowired
	public IUsuarioService usuarioService;	
	
	@Autowired
	public IDoacaoAceitaService doacaoAceitoService;
	
	@Autowired
	public IInstituicaoService instituicaoService;
	
	@Autowired
	public StorageServiceLocal storageService;
	
	@Autowired
	public IHospedeService hospedeService;
	
	@Autowired
	public IDoacaoService doacaoService;
	
	@Autowired
	public IHorarioVisitaService horarioVisitaService;
	
	@Autowired
	IProfileService profileService;
	
	@Autowired
	public ISendEmailService sendEmailService;
	
	@PreAuthorize("hasAuthority('INST')")
	@RequestMapping(value="/instituicao/index", method=RequestMethod.GET)
	public ModelAndView instituicaoIndex(){
		ModelAndView mv = new ModelAndView("/instituicao/instituicao-index");
		Profile profile = getUserProfile(getLoggedUser());
		Instituicao instituicao = instituicaoService.findByEmail(getLoggedUser().getEmail());
		
		mv.addObject("profile", profile);
		mv.addObject("usuario", getLoggedUser());
		mv.addObject("instituicao", instituicao);
		
		return mv;
		
	}
	
	@PreAuthorize("hasAuthority('INST')")
	@RequestMapping(value = "/instituicao/home", method = RequestMethod.GET)
	public ModelAndView inicio() {
		Usuario usuarioLogado = getLoggedUser();
		Instituicao instituicao = instituicaoService.findByEmail(usuarioLogado.getEmail());
		
		ModelAndView model = new ModelAndView("/instituicao");
		model.addObject("usuario", usuarioLogado);
		model.addObject("instituicao", instituicao);
		return model;
	}
	
	@RequestMapping(value = "/instituicao/atualizar-foto-instituicao", method = RequestMethod.POST)
	public @ResponseBody Instituicao atualizarFotoInstituicao(@RequestParam("file") MultipartFile file) {
		Instituicao instituicao = getLoggedInstituicao();
		String filePath = PictureSave.sendPicture(file, getLoggedPersonEmail());
		instituicao.setFoto(filePath);
		instituicaoService.updateInstituicao(instituicao);
		return instituicao;

	}
	
	@RequestMapping(value = "/instituicao/atualizar-instituicao", method = RequestMethod.POST)
	public @ResponseBody String atualizarInstituicao(HttpServletRequest request) {
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		List<Telefone> telefones = new ArrayList<Telefone>();

		String descricao = request.getParameter("descricao");
		String nomeResponsavel = request.getParameter("nomeResponsavel");
		String numeroHospedes = request.getParameter("numeroHospedes");
		String site = request.getParameter("site");
		String cep = request.getParameter("cep");
		String pais = request.getParameter("pais");
		String uf = request.getParameter("uf");
		String cidade = request.getParameter("cidade");
		String bairro = request.getParameter("bairro");
		String logradouro = request.getParameter("logradouro");
		String numero = request.getParameter("numero");
		String complemento = request.getParameter("complemento");
		String telefone = request.getParameter("telefone-1");
		String telefone2 = request.getParameter("telefone-2");

		System.out.println(descricao);
		System.out.println(site);

		if (telefone != null) {
			String ddd = telefone.substring(1, 3);
			String numero1 = telefone.substring(4, telefone.length());
			Telefone telefoneSalvar = new Telefone();
			telefoneSalvar.setCodigoPais("55");
			telefoneSalvar.setDdd(ddd);
			telefoneSalvar.setNumero(numero1);
			telefones.add(telefoneSalvar);

		}

		if (telefone2 != null) {
			String ddd2 = telefone2.substring(1, 3);
			String numero2 = telefone2.substring(4, telefone2.length());
			Telefone telefoneSalvar = new Telefone();
			telefoneSalvar.setCodigoPais("55");
			telefoneSalvar.setDdd(ddd2);
			telefoneSalvar.setNumero(numero2);
			telefones.add(telefoneSalvar);
		}

		if (descricao != null) {
			instituicao.setDescricao(descricao);
		}
		if (nomeResponsavel != null) {
			instituicao.setNomeResponsavel(nomeResponsavel);
		}
		if (numeroHospedes != null) {
			instituicao.setNumeroHospede(Integer.parseInt(numeroHospedes));
		}
		if (site != null) {
			instituicao.setSite(site);
		}
		if (cep != null) {
			Endereco endereco = new Endereco();
			endereco.setCep(cep);
			endereco.setPais(pais);
			endereco.setUf(uf);
			endereco.setCidade(cidade);
			endereco.setBairro(bairro);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			if (complemento != null) {
				endereco.setComplemento(complemento);
			}

			Endereco enderecoSalvo = enderecoService.saveEndereco(endereco);

			instituicao.setEndereco(enderecoSalvo);

		}

		if (!telefones.isEmpty()) {
			instituicao.setTelefone(telefones);
		}

		instituicaoService.updateInstituicao(instituicao);
		return "Instituição atualizada com sucesso!";
	}
	
	
	
	@RequestMapping(value = "/is-email-exist-instituicao", method = RequestMethod.GET)
	public @ResponseBody String isEmailExist(HttpServletRequest request) {
		String email = request.getParameter("email");
		boolean isEmailExist = instituicaoService.isEmailExist(email);

		return String.valueOf(isEmailExist);
		// if(valor==0){
		// return "true";
		// }else{
		// return "false";
		// }
	}
	
	
	@RequestMapping(value = "/instituicao/esqueci-senha", method=RequestMethod.GET)
	public ModelAndView pageEsqueciSenha(){
		ModelAndView mv = new ModelAndView("/usuario/esqueci-senha");
		return mv;
	}
	
	
	@RequestMapping(value = "/instituicao/enviar-email-esqueci-senha", method = RequestMethod.POST)
	public @ResponseBody String esqueciSenha(HttpServletRequest request) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		//String id = request.getParameter("id");
		String email = request.getParameter("email");
		Date date = Dates.obterTimestampAtual();
				
		//String email = instituicao.getEmail();
		//String dataCriacao = instituicao.getDataCriacao().toString();
		
		Instituicao instituicao = instituicaoService.findByEmail(email);
		if(instituicao==null){
			return "Email não cadastrado!";
		}
		String nome = instituicao.getNome();
		String id = instituicao.getId().toString();
		RandomString random = new RandomString();
		String randomString = random.randomString(57);
		
		String randomString2  = random.randomString(13);
		
		
		String link = "http://localhost:8080/instituicao/alterar-senha?value=" + date.toString() + "@" + randomString  + id + randomString2;
		String subject = "Recuperar Senha Visitem";
		
		sendEmailService.sendEmailEsqueciSenha(email, nome, subject, link);
		return "Email enviado com sucesso!";
		
	}
	
	
	@RequestMapping(value = "/instituicao/alterar-senha", method = RequestMethod.GET)
	public ModelAndView alterarSenha(@RequestParam("value") String value) throws ParseException{
		ModelAndView mv = new ModelAndView("/usuario/alterar-senha");
		ModelAndView mv2 = new ModelAndView("/usuario/link-expirado");
		//int lengthId = value.length() - 70;
		
		int pos = value.indexOf("@");
		String dateSolicitada = value.substring(0,pos);
		
		int lengthId = value.length() - 71 - dateSolicitada.length(); 
		int lengthTotal = dateSolicitada.length()+1+57;
		System.out.println(lengthId);
		System.out.println(dateSolicitada.length());
		System.out.println(dateSolicitada);
		String id = value.substring(lengthTotal, lengthTotal+lengthId);
		System.out.println(id);
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		
		mv.addObject("instituicao", instituicao);
		//Usuario usuario = serviceUsuario.findByEmail(instituicao.getEmail());
		//mv.addObject("usuario", usuario);
		dateSolicitada = dateSolicitada.substring(0, 4) + dateSolicitada.substring(5,7) + dateSolicitada.substring(8,10);
		System.out.println(" dateSolicidata " + dateSolicitada);
		String dataAtual = Dates.obterTimestampAtual().toString();
		dataAtual = dataAtual.substring(0, 4) + dataAtual.substring(5,7) + dataAtual.substring(8,10);
		System.out.println(" dateAtual " + dataAtual);
		if(isMore24Hours(dateSolicitada, dataAtual)){
			return mv2;
		}
		
		return mv;
		
		
	}
	
	
	@RequestMapping(value="/instituicao/salvar-alteracao-senha", method=RequestMethod.POST)
	public String altPass(HttpServletRequest request){
		String senha = request.getParameter("senha");
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		
		Usuario usuario = usuarioService.findByEmail(instituicao.getEmail());
		usuario.setSenhaHash(senha);
		usuarioService.updateUsuario(usuario);
		return "/usuario/senha-alterada";
		
	}
	
	@RequestMapping(value = "/cadastrar-instituicao", method = RequestMethod.GET)
	public ModelAndView formAddInstituicaoTeste() {
		ModelAndView mv = new ModelAndView("/usuario/cadastro-instituicao");
		return mv;

	}

	@RequestMapping(value = "/salvar-instituicao", method = RequestMethod.POST)
	public @ResponseBody String saveInstituicao(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String filePath = PictureSave.sendPicture(file, "Usuario");

		String nome = request.getParameter("nome");
		String cnpj = request.getParameter("cnpj");
		String email = request.getParameter("email");
		String nomeResponsavel = request.getParameter("nomeResponsavel");
		String idEndereco = request.getParameter("idEndereco");

		List<Telefone> telefones = new ArrayList<>();

		// String ddd = request.getParameter("ddd");
		String numeroTelefone = request.getParameter("telefone");
		String ddd = numeroTelefone.substring(1, 3);
		String numero = numeroTelefone.substring(4, numeroTelefone.length());

		Telefone telefone = new Telefone();
		telefone.setCodigoPais("55");
		telefone.setDdd(ddd);
		telefone.setNumero(numero);
		Telefone telefoneSalvo = telefoneService.saveTelefone(telefone);
		telefones.add(telefoneSalvo);

		String numeroTelefoneOpcional = request.getParameter("telefone-opcional");
		if (numeroTelefoneOpcional != null) {
			String dddOpcional = numeroTelefoneOpcional.substring(1, 3);
			String numeroOpcional = numeroTelefoneOpcional.substring(4, numeroTelefoneOpcional.length());
			System.out.println(numeroOpcional);
			Telefone telefoneOpcional = new Telefone();
			telefoneOpcional.setCodigoPais("55");
			telefoneOpcional.setDdd(dddOpcional);
			telefoneOpcional.setNumero(numeroOpcional);
			Telefone telefoneOpcionalSalvo = telefoneService.saveTelefone(telefoneOpcional);
			telefones.add(telefoneOpcionalSalvo);
		}

		Endereco endereco = enderecoService.findById(Long.parseLong(idEndereco));

		Instituicao instituicao = new Instituicao();
		instituicao.setNome(nome);
		instituicao.setCnpj(cnpj);
		instituicao.setEmail(email);
		instituicao.setEndereco(endereco);
		instituicao.setNomeResponsavel(nomeResponsavel);
		instituicao.setTelefone(telefones);
		instituicao.setFoto(filePath);
		instituicao.setDataCriacao(Dates.obterTimestampAtual());

		Instituicao instituicaoSalva = instituicaoService.saveInstituicao(instituicao);
		String retorno = saveUsuarioInstituicao(instituicaoSalva);
		if (retorno.equals("Esse email já esta cadastrado.")) {
			return "Esse email já esta cadastrado.";
		}
		if (retorno.equals("")) {
			return "Erro ao adicionar usuario.";
		}

		return "Instituicao salva com sucesso.";

	}
	

	public String saveUsuarioInstituicao(Instituicao instituicao) {

		if (usuarioService.findByEmail(instituicao.getEmail()) != null) {
			return "Esse email já esta cadastrado.";
		}

		Random randomGenerator = new Random();
		int random = randomGenerator.nextInt(200);

		Usuario usuario = new Usuario();
		usuario.setNome(instituicao.getNome());
		usuario.setEmail(instituicao.getEmail());
		usuario.setSenhaHash(String.valueOf(random));
		// usuario.setSenhaHash(senha);

		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
		Profile profile = new Profile();
		profile.setRole(Role.INST);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);

		return "Usuario cadastrado.";

	}
	
	public String saveUsuarioInstituicao(Instituicao instituicao, String senha) {

		if (usuarioService.findByEmail(instituicao.getEmail()) != null) {
			return "Esse email já esta cadastrado.";
		}

		Usuario usuario = new Usuario();
		usuario.setNome(instituicao.getNome());
		usuario.setEmail(instituicao.getEmail());
		usuario.setSenhaHash(senha);

		Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
		Profile profile = new Profile();
		profile.setRole(Role.INST);
		profile.setUsuario(usuarioSalvo);
		profileService.saveProfile(profile);

		return "Usuario cadastrado.";

	}

	@RequestMapping(value = "/salvar-endereco-instituicao", method = RequestMethod.POST)
	public @ResponseBody String salvarEndereco(HttpServletRequest request) {
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String uf = request.getParameter("uf");
		String pais = "Brasil";
		String numero = request.getParameter("numero");
		String complemento = request.getParameter("complemento");
		String pontoReferencia = request.getParameter("pontoReferencia");

		Endereco endereco = new Endereco();
		endereco.setCep(cep);
		endereco.setLogradouro(logradouro);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setUf(uf);
		endereco.setPais(pais);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setPontoReferencia(pontoReferencia);

		Endereco enderecoSalvo = enderecoService.saveEndereco(endereco);
		String idEndereco = enderecoSalvo.getId().toString();
		return idEndereco;

	}

	
	
	
	@RequestMapping(value = "/finalizar-cadastro-instituicao", method = RequestMethod.GET)
	public ModelAndView finalizarCadastro(@RequestParam("value") String value){
		ModelAndView mv = new ModelAndView("/usuario/finalizar-cadastro-instituicao");
		ModelAndView mv2 = new ModelAndView("/usuario/acesso-negado");
		int lengthId = value.length() - 70;
		System.out.println(lengthId);
		String id = value.substring(57, 57+lengthId);
		System.out.println(id);
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		HorarioVisita horarioVisita = new HorarioVisita();
		
		
		mv.addObject("horarioVisita", horarioVisita);
		mv.addObject("instituicao", instituicao);
		Usuario usuario = usuarioService.findByEmail(instituicao.getEmail());
		mv.addObject("usuario", usuario);
		
		if(instituicao.getSituacao().equals(SituacaoInstituicao.AGUARDANDO)){
			return mv;
		}
		
		return mv2;
		
		
	}
	
	@RequestMapping(value="/cadastro-sucesso", method = RequestMethod.GET)
	public ModelAndView paginaSucesso(@RequestParam ("value") String value){
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(value));
		
		
		ModelAndView mv = new ModelAndView("/usuario/pagina-sucesso-finalizacao-cadastro-instituicao");
		
		mv.addObject("instituicao", instituicao);
		
		return mv;
		
	}
	
	@RequestMapping (value = "/ativar-cadastro-instituicao", method = RequestMethod.POST)
	public @ResponseBody Instituicao finalizarCadastroInstituicao(HttpServletRequest request)  throws ParseException, AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException {
		String id = request.getParameter("id");
		String nomeResponsavel = request.getParameter("nomeResponsavel");
		String cpfResponsavel = request.getParameter("cpfResponsavel");
		String descricao = request.getParameter("descricao");
		String site = request.getParameter("site");
		String numeroHospede = request.getParameter("numeroHospede");
		String dataFundacao = request.getParameter("dataFundacao");
		String senha = request.getParameter("senha");
		String categoria = request.getParameter("categoria");
		String carencia = request.getParameter("carencia");
		String manha = request.getParameter("manha");
		String tarde = request.getParameter("tarde");
		String noite = request.getParameter("noite");
		String funcionaFimDeSemana = request.getParameter("funcionaFimDeSemana");
		
		Collection<TurnoHorarioFuncionamento> turnoHorarioFuncionamento = new ArrayList<>();
		if(manha!=null){
			turnoHorarioFuncionamento.add(TurnoHorarioFuncionamento.M);
			
		}
		
		if(tarde!=null){
			turnoHorarioFuncionamento.add(TurnoHorarioFuncionamento.T);
		}
		if(noite!=null){
			turnoHorarioFuncionamento.add(TurnoHorarioFuncionamento.N);
		}
		HorarioVisita horarioVisita = new HorarioVisita();
		
		
		
		horarioVisita.setTurnoFuncionamento(turnoHorarioFuncionamento);
		
		if(funcionaFimDeSemana.equals("true")){
			horarioVisita.setFuncionaFds(true);
		}
		if(funcionaFimDeSemana.equals("false")){
			horarioVisita.setFuncionaFds(false);
		}
		
		HorarioVisita horarioVisitaSalvo = horarioVisitaService.save(horarioVisita);
		
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date)formatter.parse(dataFundacao);
		
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		if(categoria.equals("ORFANATO")){
			instituicao.setCategoria(Categoria.ORFANATO);
		}
		if(categoria.equals("ASILO")){
			instituicao.setCategoria(Categoria.ASILO);
		}
		
		if(categoria.equals("CRECHE")){
			instituicao.setCategoria(Categoria.CRECHE);
		}
		
		int i = instituicao.getEmail().indexOf("@");
		String nomeUsuario = instituicao.getEmail().substring(0, i);
		String nUsuario = nomeUsuario.replace(".", "");
		
		System.out.println(nUsuario);
		instituicao.setNomeUsuario(nUsuario);
		instituicao.setHorarioVisita(horarioVisitaSalvo);
		instituicao.setCpfResponsavel(cpfResponsavel);
		instituicao.setNomeResponsavel(nomeResponsavel);
		instituicao.setDescricao(descricao);
		instituicao.setSite(site);
		instituicao.setCarencia(carencia);
		instituicao.setNumeroHospede(Integer.parseInt(numeroHospede));
		instituicao.setDataFundacao(date);
		instituicao.setSituacao(SituacaoInstituicao.ATIVO);
		String subject = "Cadastro realizado com sucesso";
		String nome = instituicao.getNome();
		String email = instituicao.getEmail();
		
		sendEmailService.sendEmailFinalizacaoCadastro(email, nome, subject);
				
		Instituicao instituicaoNova = instituicaoService.updateInstituicao(instituicao);
		
		Usuario usuario = usuarioService.findByEmail(instituicao.getEmail());
		usuario.setSenhaHash(senha);
		usuarioService.updateUsuario(usuario);
		
		return instituicaoNova;
		
		
	}
	
	@RequestMapping(value = "/instituicao/telefone/save", method = RequestMethod.POST)
	public @ResponseBody String saveTelefone(HttpServletRequest request) {
		String ddd = request.getParameter("ddd");
		String numero = request.getParameter("numero");

		Telefone telefone = new Telefone();
		telefone.setCodigoPais("55");
		telefone.setDdd(ddd);
		telefone.setNumero(numero);

		Telefone telefoneSalvo = telefoneService.saveTelefone(telefone);
		String idTelefone = telefoneSalvo.getId().toString();
		return idTelefone;

	}
	
	@RequestMapping(value = "/excluir-instituicao", method = RequestMethod.POST)
	public @ResponseBody String desativarInstituicao(HttpServletRequest request){
		
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		instituicaoService.deleteInstituicao(instituicao);
		return "Instituição inativada com sucesso!";
		
		
	}
	
	@RequestMapping(value="/reativar-instituicao", method=RequestMethod.POST)
	public @ResponseBody String reativarInstituicao(HttpServletRequest request){
		String id = request.getParameter("id");
		Instituicao instituicao = instituicaoService.findById(Long.parseLong(id));
		instituicaoService.reativarInstituicao(instituicao);
		return "Instituição reativada com sucesso!";
		
	}
	
	
	@RequestMapping(value="/get-instituicao", method=RequestMethod.GET)
	public @ResponseBody Instituicao getInstituicao(){
		return getLoggedInstituicao();
	}
	
	
	@RequestMapping(value = "/instituicao/save", method = RequestMethod.POST)
	public @ResponseBody String saveAjax(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws ParseException {
		Instituicao instituicao = getLoggedInstituicao();
		String filePath = PictureSave.sendPicture(file, getLoggedPersonEmail());
		
		Hospede hospede = new Hospede();
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String dataNascimento = request.getParameter("data");
		String descricao = request.getParameter("descricao");
		String sexo = request.getParameter("sexo");
		
		if(sexo.equals("empty")){
			return "Campo sexo precisa ser preenchido!";
		}
		
		if(dataNascimento==""){
			return "Campo data de nascimento precisa ser preenchido!";
		}
		
		int i = dataNascimento.indexOf("-");
		String ano = dataNascimento.substring(0, i);
		if(ano.length()>4){
			return "Digite uma data no formato dd/mm/AAAA";
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date)formatter.parse(dataNascimento);
		
		List<Hospede> hospedesIguais = hospedeService.findHospedeByNomeAndSobrenomeAndInstituicaoAndDataNascimentoAndSituacao(nome, sobrenome, instituicao, date, SituacaoHospede.ATIVO);
		
		if(hospedesIguais.size()>=1){
			return "Esse hospede já está cadastrado!";
		}
		
		hospede.setNome(nome);
		hospede.setSobrenome(sobrenome);
		hospede.setDescricao(descricao);
		hospede.setDataNascimento(date);
		hospede.setInstituicao(instituicao);
		hospede.setFoto(filePath);
		
		if(sexo.equals("M")){
			hospede.setSexo(Sexo.M);
		}
		if(sexo.equals("F")){
			hospede.setSexo(Sexo.F);
		}
		
		hospedeService.saveHospede(hospede);
		return "Hospede adicionado com sucesso!";
		
	}
	
	
	@RequestMapping(value = "/instituicao/list-hospedes", method = RequestMethod.GET)
	public @ResponseBody List<Hospede> listaHospedes() {
		Instituicao instituicao = getLoggedInstituicao();
		
		List<Hospede> hospedes = hospedeService.findHospedeByInstituicaoAndSituacao(instituicao, SituacaoHospede.ATIVO);
		return hospedes;
				
	}
	
	@RequestMapping(value = "/instituicao/list-hospedes-page", method = RequestMethod.GET)
	public @ResponseBody List<Hospede> listaHospedesPage(@RequestParam (defaultValue = "0") int value) {
		Instituicao instituicao = getLoggedInstituicao();
		Pageable pagina = null;
		
		switch(value){
		case 1:
			new PageRequest(0, 10);
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
			pagina = new PageRequest(6, 25);
			break;
		case 8:
			pagina = new PageRequest(7, 25);
			break;
		}
		
		List<Hospede> hospedes = hospedeService.findHospedeByInstituicaoAndSituacaoPageable(pagina, instituicao, SituacaoHospede.ATIVO);
		return hospedes;
				
	}
	
	@RequestMapping(value = "/instituicao/hospede/pesquisa-por-nome", method = RequestMethod.GET)
	public @ResponseBody List<Hospede> pesquisaHospedePorNome(@RequestParam String value){
		List<Hospede> hospedes = hospedeService.findHospedeByNomeContaining(value);
		return hospedes;
	}
	@RequestMapping( value = "/instituicao/hospede/pesquisa-por-nome-sobrenome", method = RequestMethod.GET)
	public @ResponseBody List<Hospede> pesquisaHospedePorNomeESobreNome(@RequestParam String value){
		Instituicao instituicao = getLoggedInstituicao();
//		if(value==""){
//			Pageable pagina = PageRequest.of(0, 25);
//			List<Hospede> hospedes = hospedeService.findHospedeByInstituicaoAndSituacaoPageable(pagina, instituicao, SituacaoInstituicao.ATIVO);
//			return hospedes;
//		}
		
		String nome = "";
		String sobrenome = "";
		String[] arrayValores = value.split(" ");
		for(int x=0; x < arrayValores.length; x++){
				if(x==0){
					nome = arrayValores[x];
				}
				if(x>=1){
					sobrenome = arrayValores[x];
				}
		}
		
		
		List<Hospede> hospedes = hospedeService.findByNomeAndSobrenomeContainingAndInstituicaoAndSituacao(nome, sobrenome, instituicao, SituacaoHospede.ATIVO);
		return hospedes;
	}
	
	@RequestMapping(value = "/instituicao/hospede/carrega-hospede-alterar" , method = RequestMethod.GET)
	public @ResponseBody Hospede carregarAlterarHospede(@RequestParam("id") String id){
		return hospedeService.findById(Long.parseLong(id));
		
	}
	
	@RequestMapping(value = "/instituicao/hospede/alterar-hospede", method = RequestMethod.POST)
	public @ResponseBody String alterarHospede(@RequestParam(value ="file", required=false) MultipartFile file, HttpServletRequest request) throws ParseException{
		
		
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String descricao = request.getParameter("descricao");
		String dataNascimento = request.getParameter("dataNascimento");
		String sexo = request.getParameter("sexo");
			
		
		if(sexo.equals("empty")){
			return "Campo sexo precisa ser preenchido!";
		}
		
		if(dataNascimento==""){
			return "Campo data de nascimento precisa ser preenchido!";
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date)formatter.parse(dataNascimento);
		
		Hospede hospedeAntigo = hospedeService.findById(Long.parseLong(id));
		hospedeAntigo.setNome(nome);
		hospedeAntigo.setSobrenome(sobrenome);
		hospedeAntigo.setDescricao(descricao);
		
		if(file != null){
			String filePath = PictureSave.sendPicture(file, getLoggedPersonEmail());
			hospedeAntigo.setFoto(filePath);
		}
		
		
		if(sexo=="F"){
			hospedeAntigo.setSexo(Sexo.F);
		}
		if(sexo == "M"){
			hospedeAntigo.setSexo(Sexo.M);
		}
		hospedeAntigo.setDataNascimento(date);
		
		
		
		hospedeService.updateHospede(hospedeAntigo);
		
		return "Hospede alterado com sucesso!";
		
	}
	
	@RequestMapping(value = "/instituicao/hospede/excluir", method= RequestMethod.POST )
	public @ResponseBody String excluirHospede(@RequestParam ("id") String id){
		Hospede hospede = hospedeService.findById(Long.parseLong(id));
		List<Doacao> doacaos = doacaoService.findByHospede(hospede);
		if(!doacaos.isEmpty()){
			for(Doacao doacao:doacaos){
				if(doacao.getSituacao().equals(SituacaoDoacao.ABERTO) ){
					return "EXISTE ABERTO";
				}
				if(doacao.getSituacao().equals(SituacaoDoacao.ACEITO)){
					return "EXISTE ACEITO";
				}
			}
		}
		
		
		hospedeService.deleteHospede(hospede);
		return "Hospede excluido com sucesso!";
		
	}
	
	@RequestMapping(value ="/instituicao/hospede/find-hospede", method = RequestMethod.GET)
	public  @ResponseBody Hospede findById(@RequestParam ("id") String id){
			return hospedeService.findById(Long.parseLong(id));
	}
	
	@RequestMapping (value = "/instituicao/doacao/find-by-situacao", method = RequestMethod.GET)
	public @ResponseBody List<Doacao> findDoacaoBySituacao(HttpServletRequest request){
		Instituicao instituicao = getLoggedInstituicao();
		Pageable pagina = null;
		
		String situacao = request.getParameter("situacao");
		int valuePage = Integer.parseInt(request.getParameter("valuePage"));
		
		SituacaoDoacao situacaoDoacao = null;
		
		if(situacao.equals("ABERTO")){
			situacaoDoacao = SituacaoDoacao.ABERTO; 	
		}
		if(situacao.equals("ACEITO")){
			situacaoDoacao = SituacaoDoacao.ACEITO;
		}
		if(situacao.equals("ENTREGUE")){
			situacaoDoacao = SituacaoDoacao.ENTREGUE;
		}
		
		pagina = new PageRequest(valuePage, 25);
		
		return doacaoService.findByInstituicaoAndSituacao(pagina, instituicao, situacaoDoacao);
		
	}
	
	@RequestMapping(value="/instituicao/doacao/save-teste", method=RequestMethod.GET)
	public ModelAndView salvarDoacaoTeste(){
		ModelAndView mv = new ModelAndView("/teste-save-present",  "doacao", new DoacaoCreateForm());
		Instituicao instituicao = getLoggedInstituicao();
		List<Hospede> hospedes = hospedeService.findHospedeByInstituicaoAndSituacao(instituicao, SituacaoHospede.ATIVO);
		mv.addObject("hospedes", hospedes);
		
		return mv;
	}
	
	
	@RequestMapping(value="/instituicao/doacao/save-teste", method = RequestMethod.POST, params={"action"})
	public String addDoacaoTeste(@ModelAttribute("doacao") DoacaoCreateForm doacao,MultipartFile foto,BindingResult bindingResult){
		 if (bindingResult.hasErrors()) {
	            return "/teste-save-present";
	        }
		 Instituicao instituicao = getLoggedInstituicao(); 
		 doacao.setIdInstituicao(instituicao.getId().toString());
		doacaoService.addDoacao(foto, doacao);
		return "redirect:/instituicao/doacao/save-teste";
	}
	
	@RequestMapping(value = "/instituicao/doacao/save", method=RequestMethod.POST)
	public @ResponseBody String saveDoacao(@RequestParam("file") MultipartFile file ,HttpServletRequest request){
		
		Instituicao instituicao = getLoggedInstituicao();
		String filePath = PictureSave.sendPicture(file, getLoggedPersonEmail());
		
		String nome = request.getParameter("nome");
		String descricao  = request.getParameter("descricao");
		String idHospede = request.getParameter("idHospede");
		
		
		
		
		if(idHospede.equals("") || idHospede.equals("selecionar")){
			return "É necessário escolher o hospede!"; 
		}
		if(nome.equals("")){
			return "Campo nome precisa ser preenchido!";
		}
		
		Hospede hospede = hospedeService.findById(Long.parseLong(idHospede));
		int x=0;
		for (Doacao doacao : doacaoService.findByHospede(hospede)){
			if(doacao.getSituacao().equals(SituacaoDoacao.ABERTO)){
				x++;
			}
		}
		if(x!=0){
			return "Já existe uma doação em aberto para esse hóspede.";
		}
		Doacao doacao = new Doacao();
		doacao.setNome(nome);
		doacao.setDescricao(descricao);
		doacao.setHospede(hospede);
		doacao.setSituacao(SituacaoDoacao.ABERTO);
		doacao.setDataCriacao(Dates.obterTimestampAtual());
		doacao.setInstituicao(instituicao);
		doacao.setPhoto(filePath);
		
		doacaoService.saveDoacao(doacao);
		
		return "Doação adicionada com sucesso!";
		
	}
	
	@RequestMapping(value = "instituicao/save/foto-doacao" , method = RequestMethod.POST)
	public @ResponseBody String saveFotoDoacao(@RequestParam("file") MultipartFile file){
	     
	     String photoPath = PictureSave.sendPicture(file, getLoggedPersonEmail());
	     return photoPath;
	}
	
	
	@RequestMapping(value = "/instituicao/doacao-aceito/find", method  = RequestMethod.GET)
	public @ResponseBody List<DoacaoAceita> findPresentAceitoPorInstituicaoAceito(HttpServletRequest request){
		// mudar para instituicao logada
		Instituicao instituicao = getLoggedInstituicao();
		String valuePage = request.getParameter("valuePageAceito");
		String situacao = request.getParameter("situacao");
		SituacaoDoacao situacaoDoacao = null;
		
		if(situacao.equals("ACEITO")){
			situacaoDoacao = SituacaoDoacao.ACEITO;
		}
		if(situacao.equals("ENTREGUE")){
			situacaoDoacao = SituacaoDoacao.ENTREGUE;
		}
		
		
		Pageable pagina = new PageRequest(Integer.parseInt(valuePage), 25);
		
		return doacaoAceitoService.findByInstituicao(pagina, instituicao, situacaoDoacao); 
		
		
		
	}
	
	
	@RequestMapping(value = "/instituicao/home/doacao/carregar-alterar", method = RequestMethod.GET )
	public @ResponseBody Doacao findDoacaoById(@RequestParam ("id") String id){
		return doacaoService.getById(Long.parseLong(id));
		
	}
	
	@RequestMapping (value = "/instituicao/home/doacao/alter", method = RequestMethod.POST)
	public @ResponseBody String alterDoacao(@RequestParam(value = "file", required =false) MultipartFile file, HttpServletRequest request ){
		String idDoacao = request.getParameter("id");
		String nome = request.getParameter("nome");
		String descricao = request.getParameter("descricao");
		String idHospede = request.getParameter("idHospede");
		
		Hospede hospede = hospedeService.findById(Long.parseLong(idHospede));
		if(hospede == null ){
			return "É necessário escolher o hospede!";
		}
		Doacao doacaoVelho = doacaoService.getById(Long.parseLong(idDoacao));
		if (doacaoVelho == null){
			return "Erro ao carregar doacao!";
		}
		if(file!=null){
			String filePath = PictureSave.sendPicture(file, getLoggedPersonEmail());
			doacaoVelho.setPhoto(filePath);
		}
		
		
		doacaoVelho.setNome(nome);
		doacaoVelho.setDescricao(descricao);
		doacaoVelho.setHospede(hospede);
		
		
		doacaoService.updateDoacao(doacaoVelho);
		return "Doação alterada com sucesso!";
		
	}
	
	@RequestMapping(value = "/instituicao/home/doacao/excluir", method = RequestMethod.GET)
	public @ResponseBody String excluirDoacao(HttpServletRequest request){
		String id = request.getParameter("id");
		Doacao doacao = doacaoService.getById(Long.parseLong(id));
		
		if(doacao == null){
			return "Erro ao excluir doação!";
		}
		
		doacaoService.deleteDoacao(doacao);
		return "A Doação "+ doacao.getNome() + " foi excluida com sucesso!";
	}
	
	@RequestMapping(value = "/instituicao/home/doacao/entregue", method = RequestMethod.GET)
	public @ResponseBody String doacaoEntregue(HttpServletRequest request){
		String id = request.getParameter("id");
		Doacao doacao = doacaoService.getById(Long.parseLong(id));
		DoacaoAceita doacaoAceito = doacaoAceitoService.findByDoacao(doacao);
		
		if(doacaoAceito == null){
			return "Ops! Ocorreu algum erro ao confirmar entrega da doação!";
		}
		
		if(doacao == null){
			return "Ops! Ocorreu algum erro ao confirmar entrega da doação!";
		}
		
		doacaoAceito.setDataEntregaConfirmada(Dates.obterTimestampAtual());
		doacaoAceitoService.updateDoacaoAceito(doacaoAceito);
		
		doacao.setSituacao(SituacaoDoacao.ENTREGUE);
		doacaoService.updateDoacao(doacao);
		return "A confirmação da entrega foi registrada com sucesso!";
		
	}
	

	
	@RequestMapping(value = "/login-fb-teste", method=RequestMethod.GET)
	public ModelAndView viewIndesx(){
		ModelAndView mv = new ModelAndView("/connect");
		return mv;
	}

	
	@RequestMapping(value="/home/donation/confirm", method=RequestMethod.GET)
	public ModelAndView viewConfirm(){
		ModelAndView mv = new ModelAndView("/page-confirm");
		return mv;
	}
	
	@RequestMapping(value="/instituicao/login", method = RequestMethod.GET)
	public ModelAndView loginIntituicao(){
		ModelAndView mv = new ModelAndView("/instituicao/login-instituicao");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/403", method =RequestMethod.GET)
	public ModelAndView notFound(){
		ModelAndView mv = new ModelAndView("/acesso-negado");
		return mv;
	}
	
	
	private String getLoggedPersonEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	private Usuario getLoggedUser() {
		return usuarioService.findByEmail(getLoggedPersonEmail());
	}
	
	private Instituicao getLoggedInstituicao(){
		return instituicaoService.findByEmail(getLoggedPersonEmail());
	}
	
	private Profile getUserProfile(Usuario usuario) {
		return profileService.findByUsuarioEmail(usuario.getEmail());
	}
	
	public boolean isMore24Hours(String dataSolicitacao, String dataAtual) throws ParseException{
		   /*	String a = "20120401";
	        String b = "20120331";
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        //Converte para Date
	        Date dateA = df.parse(a);
	        Date dateB = df.parse(b);*/
			
		
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        //Converte para Date
	        Date dateA = df.parse(dataSolicitacao);
	        Date dateB = df.parse(dataAtual);
	        //Adiciona 1 dia a B
	        Calendar calB = Calendar.getInstance();
	        calB.setTime(dateB);
	        //calB.add(Calendar.DAY_OF_MONTH, 1);
	        dateB = calB.getTime();
	        
	        if(dateB.after(dateA)){
	        	return true;
	        }else{
	        	return false;
	        }
	       
	}
	
	
	
}

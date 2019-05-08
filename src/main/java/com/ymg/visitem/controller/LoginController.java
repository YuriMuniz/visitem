package com.ymg.visitem.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/form-login", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value="error", required=false) boolean error,@RequestParam(value="uf", required=false) String uf, Model model ) {
		if(error){
			model.addAttribute("message", "Usuário e/ou senha inválido(s)");
			return new ModelAndView("/usuario/login");
		}
		
		
		return new ModelAndView("/usuario/login");
	}
	
	
	
	@RequestMapping(value = "/teste-login", method = RequestMethod.GET)
	public String loginPageTeste() {
		
		return "/teste-login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(defaultValue = "false") boolean signup,
			@RequestParam(defaultValue = "false") boolean error,
			@RequestParam(defaultValue = "false") boolean logout, Model model) {
		
		if(error){
			model.addAttribute("message", "Login inválido");
			return new ModelAndView("/home");
		}
		
		if(logout){
			
			model.addAttribute("message", "você foi deslogado!");
			return new ModelAndView("/login");
		}
		
		if(signup){
			
			model.addAttribute("message", "você foi registrado");
			return new ModelAndView("/login");
		}
		
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		
//		Person person = personService.getPersonByEmail(auth);
//		
//		if (person != null && person.getSituation().equals(Situation.INACTIVE)) {
//			
//			model.addAttribute("message", "Seu usu�rio foi desativado, contate o administrador");
//			return new ModelAndView("/person/login");
//		}
		
		if (auth == "anonymousUser") {
			
			return new ModelAndView("redirect:/form");
		}

		return new ModelAndView("/teste-view-user");
	}
	
}

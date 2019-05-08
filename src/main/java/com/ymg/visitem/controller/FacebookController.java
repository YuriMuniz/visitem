package com.ymg.visitem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ymg.visitem.entity.Usuario;
import com.ymg.visitem.service.IProfileService;
import com.ymg.visitem.service.IUsuarioService;

@Controller
@RequestMapping("/")
public class FacebookController {
	
	@Autowired
	IUsuarioService usuarioService;
		
	@Autowired
	IProfileService profileService;

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository){
    	this.facebook = facebook;
    	this.connectionRepository = connectionRepository;
    }
    
    @RequestMapping(value="/hello-facebook")
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
      
        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        model.addAttribute("facebookProfileEmail", facebook.userOperations().getUserProfile().getEmail());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "/usuario/login-social";
    }
	
	 @RequestMapping(value="/verificar-usuario", method=RequestMethod.GET)
	    public ModelAndView verifyUser(){
	    	ModelAndView mv3 = new ModelAndView("redirect:/index");
	    	if(!getLoggedPersonEmail().equals("anonymousUser")){
	    		return mv3;
	    	}
	    	
	    	Usuario usuario = new Usuario();
	    	if(usuarioService.existUsuario(facebook.userOperations().getUserProfile().getEmail())){
	    		usuario = usuarioService.findByEmail(facebook.userOperations().getUserProfile().getEmail());
	    	}
	    	ModelAndView mv1 = new ModelAndView("/connect/novo-usuario-facebook");
	    	ModelAndView mv2 = new ModelAndView("/connect/login-usuario-facebook");
	    	
	    	System.out.println(usuario);
	    	if(usuario.getId()==null){
	    		mv1.addObject("usuarioFacebook",facebook.userOperations().getUserProfile());
	    		return mv1;
	    	}else{
	    		mv2.addObject("usuarioFacebook",facebook.userOperations().getUserProfile());
	        	return mv2;
	    	}
	    	
	    	
	    }

	    private String getLoggedPersonEmail() {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}

}

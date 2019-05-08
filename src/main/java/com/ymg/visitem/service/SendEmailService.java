package com.ymg.visitem.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.ImageType;
import it.ozimov.springboot.mail.model.InlinePicture;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultInlinePicture;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import static com.google.common.collect.Lists.newArrayList;

@Service
public class SendEmailService implements ISendEmailService {
	
	@Autowired
	public EmailService emailService;
	
	public void sendEmailValidarCadastro (String toAddress,String nome, String subject,String msgBody) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		InlinePicture inlinePicture = createGalaxyInlinePicture();
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress("fromAddress", "Visitem"))
				.to(newArrayList(new InternetAddress(toAddress, nome)))
		        .subject(subject)
		        //.body("Olá " + nome +", " + quebraLinha + "Obrigado por fazer parte de nossa rede. Para concluir o cadastro clique no link abaixo e preencha as informações necessárias para ingressar de vez em nosso sistema.  "+ quebraLinha + quebraLinha + quebraLinha + msgBody + quebraLinha + quebraLinha + quebraLinha + "Agradecemos seu compromisso em tornar nosso mundo melhor." + quebraLinha + quebraLinha + "att, " +  quebraLinha +"Equipe Visitem!"  )
		        .body("")
		        .encoding("UTF-8").build();
		
			String template = "emailTemplate/emailTemplateValidarCadastro";
			
			  Map<String, Object> modelObject = ImmutableMap.of(
		                "nome", nome,
		                "link", msgBody
		                
		        );

		   emailService.send(email, template, modelObject, inlinePicture);
	
	}
	
	public void sendEmailConfirmacaoEntrega (String toAddress,String nome, String subject, String nomeUsuario,String nomeDoacao, String nomeBeneficiado, String dataAceitacaoDoacao) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		InlinePicture inlinePicture = createGalaxyInlinePicture();
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress("fromAddress", "Visitem"))
				.to(newArrayList(new InternetAddress(toAddress, nome)))
		        .subject(subject)
		        //.body("Olá " + nome +", " + quebraLinha + "O usuário " + nomeUsuario +" nos informou que já realizou a entrega da doação " + nomeDoacao + " que foi aceita no dia " +dataAceitacaoDoacao+ " para o beneficiado " +nomeBeneficiado +"."  + quebraLinha + "Por favor, se a doação não foi entregue responda esse email nos informando disso." + quebraLinha + quebraLinha + quebraLinha + "Agradecemos seu compromisso em tornar nosso mundo melhor." + quebraLinha + quebraLinha + "att, " +  quebraLinha +"Equipe Visitem!"  )
		        .body("")
		        .encoding("UTF-8").build();
		String template = "emailTemplate/emailTemplateConfirmacaoEntrega";
		
		  Map<String, Object> modelObject = ImmutableMap.of(
	                "nome", nome,
	                "nomeUsuario", nomeUsuario,
	                "nomeDoacao" , nomeDoacao,
	                "nomeBeneficiado" , nomeBeneficiado,
	                "dataAceitacaoDoacao" , dataAceitacaoDoacao
	                
	        );

		   emailService.send(email,template, modelObject, inlinePicture);
	
	}
	
	public void sendEmailEsqueciSenha(String toAddress, String nome, String subject, String msgBody) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		InlinePicture inlinePicture = createGalaxyInlinePicture();
		
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress("formAddress", "Visitem"))
				.to(newArrayList(new InternetAddress(toAddress, nome)))
				.subject(subject)
				.body("")
				.encoding("UTF-8").build();
		
		

        String template = "emailTemplate/emailTemplateEsqueciSenha";

        Map<String, Object> modelObject = ImmutableMap.of(
                "nome", nome,
                "link", msgBody
        );
		
			emailService.send(email,template, modelObject, inlinePicture);
		
		
		
	}
	
	
	public void sendEmailFinalizacaoCadastro(String toAddress, String nome, String subject) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException{
		InlinePicture inlinePicture = createGalaxyInlinePicture();
		
		final Email email = DefaultEmail.builder()
				.from(new InternetAddress("formAddress", "Visitem"))
				.to(newArrayList(new InternetAddress(toAddress, nome)))
				.subject(subject)
				.body("")
				.encoding("UTF-8").build();
		
		

        String template = "emailTemplate/emailTemplateFinalizacaoCadastro";

        Map<String, Object> modelObject = ImmutableMap.of(
                "nome", nome
        );
		
			emailService.send(email,template, modelObject, inlinePicture);
		
		
		
	}
	
	

    private InlinePicture createGalaxyInlinePicture() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        File pictureFile = new File(classLoader.getResource("static/img" + File.separator + "logo-simple.fw.png").toURI());
        Preconditions.checkState(pictureFile.exists(), "There is not picture %s", pictureFile.getName());

        return DefaultInlinePicture.builder()
                .file(pictureFile)
                .imageType(ImageType.PNG)
                .templateName("logo-simple.fw.png").build();
    }
}

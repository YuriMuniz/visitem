package com.ymg.visitem.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.mail.internet.AddressException;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;


public interface ISendEmailService {
	public void sendEmailValidarCadastro(String toAddress,String nome, String subject,String msgBody) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException;
	public void sendEmailConfirmacaoEntrega (String toAddress,String nome, String subject, String nomeUsuario,String nomeDoacao, String nomeBeneficiado, String dataAceitacaoDoacao) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException;
	public void sendEmailEsqueciSenha(String toAddress, String nome, String subject, String msgBody) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException;
	public void sendEmailFinalizacaoCadastro(String toAddress, String nome, String subject) throws AddressException, UnsupportedEncodingException, CannotSendEmailException, URISyntaxException;
}

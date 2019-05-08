package com.ymg.visitem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ymg.visitem.service.LoggedPersonDetailService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private LoggedPersonDetailService service;
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			
		 auth.userDetailsService(service).passwordEncoder(new
		 BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http
		 .authorizeRequests()
		 .antMatchers ("/css/**").permitAll()
		 .antMatchers ("/fonts/**").permitAll()
		 .antMatchers ("/img/**").permitAll()
		 .antMatchers ("/icons/**").permitAll()
		 .antMatchers ("/images/**").permitAll()
		 .antMatchers ("/js/**").permitAll()
		 .antMatchers ("/").permitAll()
		 .antMatchers ("/public/**").permitAll()
		 .antMatchers ("/webjars/**", "/auth/**").permitAll()
		 .antMatchers("/webjars/**", "/signup").permitAll()
		 .antMatchers("/api/**").permitAll()
		 .antMatchers("/instituicao/login").permitAll()
		 .antMatchers("/instituicao/add").permitAll()
		 .antMatchers("/instituicao/admin").permitAll()
		 .antMatchers("/salvar-endereco-instituicao").permitAll()
		 .antMatchers("/admin/instituicao/add").permitAll()
		 .antMatchers("/cadastrar-instituicao").permitAll()
		 .antMatchers("/connect/facebook").permitAll()
		 .antMatchers("/connect/facebook#_=_").permitAll()
		 .antMatchers("/hello").permitAll()
		 .antMatchers("/verificar-usuario").permitAll()
		 .antMatchers("/cadastrar-usuario-facebook").permitAll()
		 .antMatchers("/salvar-instituicao").permitAll()
		 .antMatchers("/is-email-exist-instituicao").permitAll()
		 .antMatchers("/teste-anonimo").permitAll()
		 .antMatchers("/home").permitAll()
		 .antMatchers("/cadastrar-usuario").permitAll()
		 .antMatchers("/add-admin").permitAll()
		 .antMatchers("/usuario/add").permitAll()
		 .antMatchers("/finalizar-cadastro-instituicao").permitAll()
		 .antMatchers("/ativar-cadastro-instituicao").permitAll()
		 .antMatchers("/cadastro-sucesso").permitAll()
		 .antMatchers("/salvar-admin").permitAll()
		 .antMatchers("/login*","/signin/**","/signup/**").permitAll()
		 .antMatchers("/add-admin").permitAll()
		 .antMatchers("/instituicoes").permitAll()
		 .antMatchers("/buscar-doacoes").permitAll()
		 .antMatchers("/instituicao/esqueci-senha").permitAll()
		 .antMatchers("/instituicao/enviar-email-esqueci-senha").permitAll()
		 .antMatchers("/instituicao/alterar-senha").permitAll()
		 .antMatchers("/instituicao/salvar-alteracao-senha").permitAll()
		 .antMatchers("/instituicoes-estado").permitAll()
		 .antMatchers("/instituicao/list-beneficiados").permitAll()
		 .antMatchers("/pedidos").permitAll()
		 .anyRequest().authenticated()
		 .and()
		 .formLogin()
		 .loginPage ("/form-login")
		 .defaultSuccessUrl("/index")
		 .failureUrl ("/form-login?error=true")
		 .usernameParameter ("username")
		 .passwordParameter ("password")
		 .permitAll()
		
		 .and()
		 .logout()
		 //.logoutSuccessUrl ("/login?logout=true")
		 .logoutSuccessUrl ("/")
		 .invalidateHttpSession (true)
		 .deleteCookies ("JSESSIONID")
		
		 .and()
		 .exceptionHandling()
		 .accessDeniedPage ("/403");

//		http.authorizeRequests()
//		.antMatchers ("/css/**").denyAll()
//		.antMatchers ("/js/**").denyAll()
//		.antMatchers ("/images/**").denyAll()
//		.antMatchers("/twitters_outro").hasAnyAuthority("USER").anyRequest().authenticated()
//		.antMatchers("/person_teste/**", "/auth/form").permitAll()
//		.and().formLogin()
//				.loginPage("/login").defaultSuccessUrl("/twitters_outro").failureUrl("/person_teste?id=1")
//				.usernameParameter("username").passwordParameter("password")
//				.permitAll().and().logout().permitAll();

		 http
		 .httpBasic()
		 .and()
		 .csrf().disable()
		 .authorizeRequests()
		 .antMatchers("/", "/login**", "/webjars/**").permitAll()
		 .antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
		 .antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
		 .antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
		 .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
		 .anyRequest().authenticated();
	}
	
	// @Override
	// public void configure(AuthenticationManagerBuilder auth)throws Exception
	// {
	//
	// auth.userDetailsService(service).passwordEncoder(new
	// BCryptPasswordEncoder());

	// auth.inMemoryAuthentication().withUser("mm1").password("mm1").roles("USER").and()
	// .withUser("mm2").password("mm2").roles("USER","ADMIN");
	// }
}
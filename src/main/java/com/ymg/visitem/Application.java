package com.ymg.visitem;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ymg.visitem.service.ISendEmailService;
import com.ymg.visitem.storage.StorageServiceLocal;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;


@SpringBootApplication
@EnableEmailTools
@EnableAsync
public class Application implements CommandLineRunner {

	@Autowired
	ISendEmailService emailService;
	
	@Resource
	StorageServiceLocal storageService;
	
	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}

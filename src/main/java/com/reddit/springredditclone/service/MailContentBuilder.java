package com.reddit.springredditclone.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {

	private final TemplateEngine templateEngine;
	
	String build(String message) {
		
		Context ctx = new Context();
		
		ctx.setVariable("message", message);
		
		return templateEngine.process("mailTemplate", ctx);
	}
}

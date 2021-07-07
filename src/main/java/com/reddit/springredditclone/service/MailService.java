package com.reddit.springredditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.springredditclone.model.NotificationEmail;

import com.reddit.springredditclone.exceptions.SpringRedditException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		
        MimeMessagePreparator messagePreparator = mimeMessage -> {
        	
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
         // messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            
            messageHelper.setText(notificationEmail.getBody());
        };
        
        try {
        	
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
            
        } catch (MailException e) {
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
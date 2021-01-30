package br.com.btsoftware.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.btsoftware.algafood.core.email.EmailProperties;
import br.com.btsoftware.algafood.domain.service.SendEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;

@ConditionalOnProperty(name = "algafood.email.impl", havingValue ="sandbox")
public class SandboxSendEmailService implements SendEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void send(Message message) {
		try {
			
			MimeMessage mimeMessage = createMimeMessage(message);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}
	
	protected String processTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}

	
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String body = processTemplate(message);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		helper.setFrom(emailProperties.getSender());
		helper.setTo(emailProperties.getSandbox().getRecipient());
		helper.setSubject(message.getSubject());
		helper.setText(body, true);
		
		return mimeMessage;
	}
}

package br.com.btsoftware.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.btsoftware.algafood.core.email.EmailProperties;
import br.com.btsoftware.algafood.domain.service.SendEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpSendEmailService implements SendEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void send(Message message) {
		try {
			
			String body = processTemplate(message);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			
			helper.setFrom(emailProperties.getSender());
			helper.setTo(message.getRecipients().toArray(new String[0]));
			helper.setSubject(message.getSubject());
			helper.setText(body, true);
			
			
			
			//helper.addInline("logo", new ClassPathResource("/templates/like.png"));
			
			//FileSystemResource file = new FileSystemResource(new File("C:\\Users\\BEN\\Documents\\estudo\\java\\algafood\\algafood-api\\src\\main\\resources\\templates\\like.png").getCanonicalPath());
			//helper.addInline("logo", file, "image/png");
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}
	
	private String processTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}

}

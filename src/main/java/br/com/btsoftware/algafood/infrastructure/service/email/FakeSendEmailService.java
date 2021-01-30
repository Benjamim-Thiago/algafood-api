package br.com.btsoftware.algafood.infrastructure.service.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = "algafood.email.impl", havingValue ="fake")
public class FakeSendEmailService extends SmtpSendEmailService {

    @Override
    public void send(Message message) {
    
    	String body = processTemplate(message);

        log.info("[FAKE E-MAIL] Para: {}\n{}", message.getRecipients(), body);
    }
}        
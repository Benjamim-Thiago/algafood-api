package br.com.btsoftware.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.btsoftware.algafood.domain.service.SendEmailService;
import br.com.btsoftware.algafood.infrastructure.service.email.FakeSendEmailService;
import br.com.btsoftware.algafood.infrastructure.service.email.SandboxSendEmailService;
import br.com.btsoftware.algafood.infrastructure.service.email.SmtpSendEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    @Primary
    public SendEmailService sendEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeSendEmailService();
            case SMTP:
                return new SmtpSendEmailService();                 
            case SANDBOX:
                return new SandboxSendEmailService();
            default:
                return null;
        }
    }
}    

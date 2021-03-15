package br.com.btsoftware.algafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;


@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			
			.and()
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.cors()
			.and()
			.oauth2ResourceServer().jwt(); //.oauth2ResourceServer().opaqueToken();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		String key = "Porque Deus tanto amou o mundo que deu o seu Filho Unigênito,"
				+ "para que todo o que nele crer não pereça, mas tenha a vida eterna. João 3:16 (NVI)";
		
		var secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	
}

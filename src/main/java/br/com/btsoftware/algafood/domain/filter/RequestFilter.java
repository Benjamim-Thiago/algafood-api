package br.com.btsoftware.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestFilter {
	public Long clientId;
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime firstCreatedDate;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime lastCreatedDate;
}

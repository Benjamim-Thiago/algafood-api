package br.com.btsoftware.algafood.api.controller.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.btsoftware.algafood.domain.exception.BusinessException;
import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(EntityNotExistException.class)
	public ResponseEntity<?> handlerEntityNotExistException(EntityNotExistException e) {
		Problem problem = Problem.builder()
				.dateHour(LocalDateTime.now())
				.message(e.getMessage()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handlerBusinessException(BusinessException e) {
		Problem problem = Problem.builder()
				.dateHour(LocalDateTime.now())
				.message(e.getMessage()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handlerEntityInUseException(EntityInUseException e) {
		Problem problem = Problem.builder()
				.dateHour(LocalDateTime.now())
				.message(e.getMessage()).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> handlerHttpMediaTypeNotSupportedException() {
		Problem problem = Problem.builder()
				.dateHour(LocalDateTime.now())
				.message("O tipo de mídia não é aceito").build();

		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problem);
	}
}

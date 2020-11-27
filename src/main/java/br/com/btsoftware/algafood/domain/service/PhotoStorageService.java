package br.com.btsoftware.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	InputStream recover(String fileName);
	
	void store(NewPhoto newPhoto);
	
	void remove(String fileName);
	
	default void replaceFile(String nameOldFile, NewPhoto newPhoto) {
		this.store(newPhoto);
		
		if (nameOldFile != null) {
			this.remove(nameOldFile);
		}
	}
	
	default String generateFileName(String originalName) {
		return UUID.randomUUID().toString() + "_" + originalName;
	}
		

	@Builder
	@Getter
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		
	}
}

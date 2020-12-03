package br.com.btsoftware.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	ToRecoverPhoto recover(String fileName);
	
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
		private String contentType;
		private Long size;
		private InputStream inputStream;
		
	}
	
	@Builder
	@Getter
	class ToRecoverPhoto {
		
		private InputStream inputStream;
		private String url; 
		
		public Boolean temUrl() {
			return this.url != null;
		}
		
		public Boolean temInputStream() {
			return this.inputStream != null;
		}
		
	}
	
}

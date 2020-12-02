package br.com.btsoftware.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import br.com.btsoftware.algafood.core.storage.StorageProperties;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService;

//@Service
public class LocalPhotoStorageService implements PhotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
		
	@Override
	public void store(NewPhoto newPhoto) {
		try {
			Path filePath = getFilePath(newPhoto.getFileName());
			
			FileCopyUtils.copy(newPhoto.getInputStream(), 
					Files.newOutputStream(filePath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}

	private Path getFilePath(String fileName) {
		return storageProperties.getLocal().getDirectoryPhotos().resolve(Path.of(fileName));
	}

	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
		
	}

	@Override
	public InputStream recover(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			
			return Files.newInputStream(filePath);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo.", e);
		}
		
	}
}
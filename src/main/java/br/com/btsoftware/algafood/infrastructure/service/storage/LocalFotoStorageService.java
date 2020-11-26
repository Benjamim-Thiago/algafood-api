package br.com.btsoftware.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import br.com.btsoftware.algafood.domain.service.PhotoStorageService;

@Service
public class LocalFotoStorageService implements PhotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretoryPhotos;
		
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
		return diretoryPhotos.resolve(Path.of(fileName));
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
}

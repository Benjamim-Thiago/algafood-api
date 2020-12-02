package br.com.btsoftware.algafood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.btsoftware.algafood.core.storage.StorageProperties;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

	@Autowired
	private StorageProperties storageProperties; 
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Override
	public InputStream recover(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(NewPhoto newPhoto) {
		try {
			String fileDirectory = getFileDirectory(newPhoto.getFileName());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newPhoto.getContentType());
			objectMetadata.setContentLength(newPhoto.getSize());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					fileDirectory,
					newPhoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
		}
		
	}

	private String getFileDirectory(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
	}

	@Override
	public void remove(String fileName) {
		try {
			String fileDirectory = getFileDirectory(fileName);
			
			var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), fileDirectory); 
			amazonS3.deleteObject(deleteObjectRequest);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo para Amazon S3.", e);
		}
				
	}

	
}

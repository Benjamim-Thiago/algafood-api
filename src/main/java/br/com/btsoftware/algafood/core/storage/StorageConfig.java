package br.com.btsoftware.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.btsoftware.algafood.core.storage.StorageProperties.TypeStorage;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService;
import br.com.btsoftware.algafood.infrastructure.service.storage.LocalPhotoStorageService;
import br.com.btsoftware.algafood.infrastructure.service.storage.S3PhotoStorageService;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
	public AmazonS3 amazonS3() {
		
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getKeyAccessId(), 
				storageProperties.getS3().getSecretAccessKey());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
		
	}
	
	@Bean
	public PhotoStorageService photoStorageService() {
		if (TypeStorage.S3.equals(storageProperties.getType())) {
			return new S3PhotoStorageService();
		} else {
			return new LocalPhotoStorageService();
		}
	}
}

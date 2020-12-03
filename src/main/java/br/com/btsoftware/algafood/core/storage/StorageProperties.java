package br.com.btsoftware.algafood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {
	private Local local = new Local();
	private S3 s3 = new S3();

	private TypeStorage type = TypeStorage.LOCAL;

	public enum TypeStorage {

		LOCAL, S3

	}

	@Getter
	@Setter
	public class Local {

		private Path directoryPhotos;

	}

	@Getter
	@Setter
	public class S3 {

		private String keyAccessId;
		private String secretAccessKey;
		private String bucket;
		private Regions region;
		private String directoryPhotos;

	}
}

package br.com.baseapi.core.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@NoArgsConstructor
@Configuration
public class FileStorageProperties {
    private String uploadDir;
    private String name;
}


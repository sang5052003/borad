package com.board.board;

import org.metaworks.multitenancy.ClassManager;
import org.metaworks.multitenancy.MetadataService;
import org.metaworks.multitenancy.persistence.MultitenantRepositoryImpl;
import org.metaworks.rest.MetaworksRestService;
import org.metaworks.springboot.configuration.Metaworks4WebConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.uengine.modeling.resource.LocalFileStorage;
import org.uengine.modeling.resource.Storage;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class, MetaworksRestService.class, ClassManager.class, MultitenantRepositoryImpl.class, MetadataService.class})
@EnableJpaRepositories(repositoryBaseClass = MultitenantRepositoryImpl.class)
public class WebConfig extends Metaworks4WebConfig {

    @Override
    public void addCorsMappings(CorsRegistry registry){

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:8081", "*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("access_token", "Content-Type");
    }

    @Bean
    protected Storage storage() {

        LocalFileStorage storage = new LocalFileStorage();
        storage.setBasePath("oce/repository");

        return storage;
    }
}

package com.datahive;

import com.datahive.entities.HistoryEntity;
import com.datahive.entities.NotWorkingWebsiteEntity;
import com.datahive.entities.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class DataHiveApplication implements RepositoryRestConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(DataHiveApplication.class, args);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(HistoryEntity.class);
        config.exposeIdsFor(NotWorkingWebsiteEntity.class);
        config.exposeIdsFor(UserEntity.class);
    }
}

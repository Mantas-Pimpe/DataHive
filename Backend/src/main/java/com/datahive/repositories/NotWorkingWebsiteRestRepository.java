package com.datahive.repositories;

import com.datahive.entities.NotWorkingWebsiteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "not_working_website", path = "not_working_website")
public interface NotWorkingWebsiteRestRepository extends CrudRepository<NotWorkingWebsiteEntity, Long> {

}
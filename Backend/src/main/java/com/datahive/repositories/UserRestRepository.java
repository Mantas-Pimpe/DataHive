package com.datahive.repositories;


import com.datahive.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRestRepository extends CrudRepository<UserEntity, Long> {

}
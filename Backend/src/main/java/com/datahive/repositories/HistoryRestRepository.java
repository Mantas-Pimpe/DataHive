package com.datahive.repositories;


import com.datahive.entities.HistoryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "history", path = "history")
public interface HistoryRestRepository extends CrudRepository<HistoryEntity, Long> {
    List<HistoryEntity> findByUserId(@Param("userId") Long userId, Sort sort);

}
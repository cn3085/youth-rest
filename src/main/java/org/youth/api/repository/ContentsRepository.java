package org.youth.api.repository;

import org.springframework.stereotype.Repository;
import org.youth.api.entity.ContentsEntity;

@Repository
public interface ContentsRepository extends SoftDeleteRepositoryAdapter<ContentsEntity, Long>, ContentsCustomRepository{

}

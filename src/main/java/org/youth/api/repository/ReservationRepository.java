package org.youth.api.repository;

import org.springframework.stereotype.Repository;
import org.youth.api.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends SoftDeleteRepositoryAdapter<ReservationEntity, Long>, ReservationCustomRepository {


}

package org.youth.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.youth.api.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends SoftDeleteRepositoryAdapter<ReservationEntity, Long>, ReservationCustomRepository {

	List<ReservationEntity> findByRegDateLessThan(LocalDateTime of);

}

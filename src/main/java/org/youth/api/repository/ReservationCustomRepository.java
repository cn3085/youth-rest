package org.youth.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;

public interface ReservationCustomRepository {
	
	Page<ReservationEntity> searchAll(Pageable page, ReservationParam searchParam);
	List<ReservationEntity> searchAll(ReservationParam searchParam);
	
	List<ReservationEntity> findByReservationTime(LocalDateTime startTime, LocalDateTime endTime);
	List<ReservationEntity> findByReservationTimeExcludeThis(LocalDateTime startTime, LocalDateTime endTime, long reservationId);
}

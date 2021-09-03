package org.youth.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;

public interface ReservationCustomRepository {
	Page<ReservationEntity> searchAll(Pageable page, ReservationParam searchParam);
}

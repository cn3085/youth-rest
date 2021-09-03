package org.youth.api.repository;

import static org.youth.api.entity.QReservationEntity.reservationEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ReservationEntity> searchAll(Pageable pageable, ReservationParam searchParam) {
		
		QueryResults<ReservationEntity> result = queryFactory.selectFrom(reservationEntity)
															//.where()
															.offset(pageable.getOffset())
															.limit(pageable.getPageSize())
															.orderBy(reservationEntity.regDate.desc())
														.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}

}

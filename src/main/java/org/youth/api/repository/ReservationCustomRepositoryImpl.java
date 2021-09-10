package org.youth.api.repository;

import static org.youth.api.entity.QReservationEntity.reservationEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
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

	
	
	@Override
	public List<ReservationEntity> findByReservationTime(LocalDateTime startTime, LocalDateTime endTime) {
		
		return queryFactory.selectFrom(reservationEntity)
								.where(overwrapTime(startTime, endTime))
							.fetch();
	}
	
	
	
	@Override
	public List<ReservationEntity> findByReservationTimeExcludeThis(LocalDateTime startTime, LocalDateTime endTime, long reservationId) {
		return queryFactory.selectFrom(reservationEntity)
				.where(
						overwrapTime(startTime, endTime),
						reservationEntity.reservationId.ne(reservationId))
				.fetch();
	}
	
	
	
	private BooleanExpression overwrapTime(LocalDateTime startTime, LocalDateTime endTime) {
		return (reservationEntity.startTime.goe(startTime).and(reservationEntity.endTime.loe(endTime)))
				.or(
			   (reservationEntity.startTime.before(startTime).and(reservationEntity.endTime.after(endTime))))
				.or(
			   (reservationEntity.startTime.before(startTime).and(reservationEntity.endTime.after(startTime))))
				.or(
			   (reservationEntity.startTime.before(endTime).and(reservationEntity.endTime.after(endTime))));
	}




}

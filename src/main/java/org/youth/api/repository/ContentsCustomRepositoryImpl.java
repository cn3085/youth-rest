package org.youth.api.repository;

import static org.youth.api.entity.QContentsEntity.contentsEntity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ContentsParam;
import org.youth.api.entity.ContentsEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentsCustomRepositoryImpl implements ContentsCustomRepository {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ContentsEntity> searchAll(Pageable pageable, ContentsParam searchParam) {
		
		
		QueryResults<ContentsEntity> result = queryFactory.selectFrom(contentsEntity)
															.where(
																	likeName(searchParam.getNm()),
																	eqEanableReservation(searchParam.getEr())
																	)
															.offset(pageable.getOffset())
															.limit(pageable.getPageSize())
															.orderBy(contentsEntity.regDate.desc())
														.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
	
	
	
	@Override
	public List<ContentsEntity> searchAll(ContentsParam searchParam) {
		
		return queryFactory.selectFrom(contentsEntity)
								.where(
										likeName(searchParam.getNm()),
										eqEanableReservation(searchParam.getEr())
										)
								.orderBy(contentsEntity.regDate.desc())
							.fetch();
	}
	
	
	
	private BooleanExpression likeName(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return contentsEntity.name.like("%" + name + "%");
	}
	
	
	private BooleanExpression eqEanableReservation(Boolean enableReservation) {
		if(enableReservation == null) {
			return null;
		}
		return contentsEntity.enableReservation.eq(enableReservation);
	}

	
}

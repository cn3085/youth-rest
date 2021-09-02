package org.youth.api.repository;

import static org.youth.api.entity.QContentsEntity.contentsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ContentsParam;
import org.youth.api.entity.ContentsEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentsCustomRepositoryImpl implements ContentsCustomRepository {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ContentsEntity> searchAll(Pageable pageable, ContentsParam searchParam) {
		
		
		QueryResults<ContentsEntity> result = queryFactory.selectFrom(contentsEntity)
															//.where()
															.offset(pageable.getOffset())
															.limit(pageable.getPageSize())
															.orderBy(contentsEntity.regDate.desc())
														.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
}

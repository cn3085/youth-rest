package org.youth.api.repository;

import static org.youth.api.entity.QMemberEntity.memberEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.MemberParam;
import org.youth.api.entity.MemberEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<MemberEntity> searchAll(Pageable pageable, MemberParam searchParam) {
		
		
		QueryResults<MemberEntity> result = queryFactory.selectFrom(memberEntity)
															//.where()
															.offset(pageable.getOffset())
															.limit(pageable.getPageSize())
															.orderBy(memberEntity.regDate.desc())
														.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
}

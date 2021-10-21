package org.youth.api.repository;

import static org.youth.api.entity.QMemberEntity.memberEntity;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.youth.api.code.SexType;
import org.youth.api.dto.MemberParam;
import org.youth.api.entity.MemberEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<MemberEntity> searchAll(Pageable pageable, MemberParam searchParam) {
		
		QueryResults<MemberEntity> result = queryFactory.selectFrom(memberEntity)
															.where(
																likeName(searchParam.getNm()),
																eqSex(searchParam.getSt()),
																afterBirth(searchParam.getAb()),
																beforeBirth(searchParam.getBb()),
																likeMyPhoneNumber(searchParam.getMp()),
																likeParentsPhoneNumber(searchParam.getPp()),
																likeAddress(searchParam.getAdrs()),
																likeSchool(searchParam.getSch()),
																eqGrade(searchParam.getGd()),
																likeMemo(searchParam.getMm())
																	)
															.offset(pageable.getOffset())
															.limit(pageable.getPageSize())
															.orderBy(memberEntity.regDate.desc())
														.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
	
	
	
	@Override
	public List<MemberEntity> searchAll(MemberParam searchParam) {
		
		return  queryFactory.selectFrom(memberEntity)
							.where(
									likeName(searchParam.getNm()),
									eqSex(searchParam.getSt()),
									afterBirth(searchParam.getAb()),
									beforeBirth(searchParam.getBb()),
									likeMyPhoneNumber(searchParam.getMp()),
									likeParentsPhoneNumber(searchParam.getPp()),
									likeAddress(searchParam.getAdrs()),
									likeSchool(searchParam.getSch()),
									eqGrade(searchParam.getGd()),
									likeMemo(searchParam.getMm())
									)
							.orderBy(memberEntity.regDate.desc())
							.fetch();
		
	}
	
	
	private BooleanExpression likeName(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return memberEntity.name.like("%" + name + "%");
	}
	
	
	private BooleanExpression eqSex(SexType sexType) {
		if(sexType == null) {
			return null;
		}
		return memberEntity.sex.eq(sexType);
	}
	
	
	private BooleanExpression afterBirth(LocalDate startBirth) {
		if(startBirth == null) {
			return null;
		}
		return memberEntity.birth.goe(startBirth);
	}
	
	
	private BooleanExpression beforeBirth(LocalDate endBirth) {
		if(endBirth == null) {
			return null;
		}
		return memberEntity.birth.loe(endBirth);
	}
	
	
	private BooleanExpression likeMyPhoneNumber(String myPhoneNumber) {
		if(StringUtils.isBlank(myPhoneNumber)) {
			return null;
		}
		return memberEntity.myPhoneNumber.like("%" + myPhoneNumber + "%");
	} 
	
	
	private BooleanExpression likeParentsPhoneNumber(String parentsPhoneNumber) {
		if(StringUtils.isBlank(parentsPhoneNumber)) {
			return null;
		}
		return memberEntity.parentsPhoneNumber.like("%" + parentsPhoneNumber + "%");
	} 
	
	
	private BooleanExpression likeAddress(String address) {
		if(StringUtils.isBlank(address)) {
			return null;
		}
		return memberEntity.address.like("%" + address + "%");
	} 
	
	
	private BooleanExpression likeSchool(String school) {
		if(StringUtils.isBlank(school)) {
			return null;
		}
		return memberEntity.school.like("%" + school + "%");
	} 
	
	
	private BooleanExpression eqGrade(String grade) {
		if(StringUtils.isBlank(grade)) {
			return null;
		}
		return memberEntity.grade.like("%" + grade + "%");
	} 
	

	private BooleanExpression likeMemo(String memo) {
		if(StringUtils.isBlank(memo)) {
			return null;
		}
		return memberEntity.memo.like("%" + memo + "%");
	} 
}

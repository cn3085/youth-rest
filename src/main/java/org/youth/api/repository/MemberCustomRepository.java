package org.youth.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.MemberParam;
import org.youth.api.entity.MemberEntity;

public interface MemberCustomRepository {
	
	public Page<MemberEntity> searchAll(Pageable pageable, MemberParam searchParam);
	public List<MemberEntity> searchAll(MemberParam searchParam);
}

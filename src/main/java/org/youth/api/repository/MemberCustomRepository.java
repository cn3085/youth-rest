package org.youth.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.MemberParam;
import org.youth.api.entity.MemberEntity;

public interface MemberCustomRepository {
	
	public Page<MemberEntity> searchAll(Pageable pageable, MemberParam searchParam);
}

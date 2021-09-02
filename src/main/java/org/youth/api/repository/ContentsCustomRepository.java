package org.youth.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youth.api.dto.ContentsParam;
import org.youth.api.entity.ContentsEntity;

public interface ContentsCustomRepository {
	
	Page<ContentsEntity> searchAll(Pageable page, ContentsParam searchParam);

}

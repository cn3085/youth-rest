package org.youth.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.ContentsDTO.Details;
import org.youth.api.dto.ContentsParam;
import org.youth.api.entity.ContentsEntity;
import org.youth.api.repository.ContentsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContentsService {
	
	private final ContentsRepository contentsRepository;

	
	@Transactional(readOnly = true)
	public Page<ContentsDTO.Details> getContents(Pageable page, ContentsParam searchParam) {
		
		return contentsRepository.searchAll(page, searchParam).map(ContentsDTO.Details::of);
	}

	
	
	@Transactional(rollbackFor = Exception.class)
	public ContentsEntity registContents(ContentsDTO.Regist contentsDTO) {
		
		ContentsEntity contentsEntity = contentsDTO.toEntity();
		return contentsRepository.save(contentsEntity);
	}


	
	@Transactional(readOnly = true)
	public ContentsEntity getContentsDetails(long contentsId) {
		return contentsRepository.findById(contentsId).orElseThrow(() -> new IllegalStateException("존재하지 않는 컨텐츠입니다."));
	}



	@Transactional(rollbackFor = Exception.class)
	public void deleteContents(long contentsId) {
		ContentsEntity contents = getContentsDetails(contentsId);
		contentsRepository.delete(contents);
	}



	@Transactional(rollbackFor = Exception.class)
	public void updateContents(long contentsId, Details contentsDTO) {
		
		ContentsEntity contents = getContentsDetails(contentsId);
		contents.updateDetails(contentsDTO);
	}
	

}

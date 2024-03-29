package org.youth.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.ContentsParam;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.service.ContentsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/contents")
public class ContentsController {

	private final ContentsService contentsService;
	
	@GetMapping
	public ResponseEntity<ResponseDTO> getContentsList(@PageableDefault(page = 0, size = 20) Pageable page,
													   ContentsParam searchParam){
		
		Page<ContentsDTO.Details> pageContent = contentsService.getContents(page, searchParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(pageContent)
											.build());
	}
	
	
	
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAllContentsList(ContentsParam searchParam){
		
		List<ContentsDTO.Details> contentsList = contentsService.getContents(searchParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(contentsList)
											.build());
	}
	
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> registContents(@RequestBody @Valid ContentsDTO.Regist contentsDTO){
		
		ContentsDTO.Details contents = ContentsDTO.Details.of(contentsService.registContents(contentsDTO));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(contents)
											.message("컨텐츠를 등록했습니다.")
											.build());
	}
	
	
	
	@GetMapping("/{contentsId}")
	public ResponseEntity<ResponseDTO> getThisContents(@PathVariable Long contentsId){
		
		ContentsDTO.Details memberDTO = ContentsDTO.Details.of(contentsService.getContentsDetails(contentsId));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.data(memberDTO)
											.code(ResponseCode.SUCC)
											.build());
	}
	
	
	
	@DeleteMapping("/{contentsId}")
	public ResponseEntity<ResponseDTO> deleteContents(@PathVariable Long contentsId){
		
		contentsService.deleteContents(contentsId);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("삭제되었습니다.")
											.build());
	}
	
	
	
	@PutMapping("/{contentsId}")
	public ResponseEntity<ResponseDTO> updateContents(@PathVariable Long contentsId,
													  @RequestBody @Valid ContentsDTO.Details contentsDTO){
		
		contentsService.updateContents(contentsId, contentsDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("수정되었습니다.")
											.build());
	}
}

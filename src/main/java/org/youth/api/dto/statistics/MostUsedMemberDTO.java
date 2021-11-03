package org.youth.api.dto.statistics;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MostUsedMemberDTO {
	
	private Long memberId;
	private String memberName;
	private Long allCount;
	
	List<MostUsedMemberContentsDTO> contents;
	
}
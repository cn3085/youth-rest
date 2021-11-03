package org.youth.api.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MostUsedMemberContentsDTO{
	
	private Long contentsId;
	private String contentsName;
	private String color;
	private Long eachCount;
}
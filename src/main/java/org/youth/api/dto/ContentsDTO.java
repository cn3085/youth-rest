package org.youth.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.youth.api.entity.ContentsEntity;
import org.youth.api.mapper.ContentsMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ContentsDTO {
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Regist {
		
		@NotBlank
		private String name;
		
		@NotBlank
		private String color;
		
		private String description;
		
		@NotNull
		private boolean enableReservation;
		
		private String notice;
		
		public ContentsEntity toEntity() {
			return ContentsMapper.INSTANCE.toEntity(this);
		}
	}
	
	
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Details {
		
		private Long contentsId;
		
		@NotBlank
		private String name;
		
		@NotBlank
		private String color;
		private String description;
		
		@NotNull
		private boolean enableReservation;
		
		private String notice;
		
		private LocalDateTime regDate;
		
		private LocalDateTime updDate;
		
		public ContentsEntity toEntity() {
			return ContentsMapper.INSTANCE.toEntity(this);
		}
		
		public static Details of(ContentsEntity contentsDetails) {
			return ContentsMapper.INSTANCE.of(contentsDetails);
		}
	}
	
}

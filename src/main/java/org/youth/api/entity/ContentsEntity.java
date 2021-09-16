package org.youth.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Where;
import org.youth.api.dto.ContentsDTO.Details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "contents")
@Where(clause = "data_state = 'A'")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ContentsEntity extends BaseDataEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long contentsId;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@Column(length = 30, nullable = false)
	private String color;
	
	@Lob
	private String description;
	
	private boolean enableReservation;
	
	private String notice;

	
	public void updateDetails(Details contentsDTO) {
		
		this.name = contentsDTO.getName();
		this.color = contentsDTO.getColor();
		this.description = contentsDTO.getDescription();
		this.enableReservation = contentsDTO.isEnableReservation();
		this.notice = contentsDTO.getNotice();
		
	}

}

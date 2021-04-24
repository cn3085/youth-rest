package org.youth.api.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.youth.api.enumtype.DataState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "TB_MEMBER")
@Getter
@AllArgsConstructor
@Builder
public class MemberEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String name;
	private String sex;
	
	@Temporal(value = TemporalType.DATE)
	private Date birth;
	
	private String phoneNumber1;
	private String phoneNumber2;
	private String address;
	
	@Lob
	private String memo;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;
	
	private LocalDateTime deleteDate;

	@Enumerated(EnumType.STRING)
	private DataState dataState;
	

}

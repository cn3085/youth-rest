package org.youth.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
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
import org.youth.api.enumtype.SexType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 5, nullable = false)
	private SexType sex;
	
	@Column(nullable = false)
	private LocalDate birth;
	
	@Column(unique = true)
	private String myPhoneNumber;
	
	private String parentsPhoneNumber;
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

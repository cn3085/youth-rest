package org.youth.api.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Where;
import org.youth.api.code.SexType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER")
@Where(clause = "data_state = 'A'")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberEntity extends BaseDataEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long memberId;
	
	@Column(length = 20, nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private SexType sex;
	
	@Column(nullable = false)
	private LocalDate birth;
	
	@Column(length = 15, unique = true, nullable = false)
	private String myPhoneNumber;
	
	@Column(length = 15)
	private String parentsPhoneNumber;
	
	@Column(length = 500)
	private String address;
	
	@Column(length = 30)
	private String school;
	
	@Column(length = 10)
	private String grade;
	
	@Lob
	private String memo;

}

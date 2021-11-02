package org.youth.api.entity;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Where;
import org.youth.api.code.SexType;
import org.youth.api.dto.MemberDTO.Details;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER")
@Where(clause = "data_state = 'A'")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "memberId")
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
	@Column(nullable = false)
	private SexType sex;
	
	@Column(nullable = false)
	private LocalDate birth;
	
	@Column(length = 15)
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
	
	@Builder.Default
	@ManyToMany(mappedBy = "members")
	private Set<ReservationEntity> reservations = new LinkedHashSet<>();

	
	
	public void updateDetails(Details memberDTO) {
		this.name = memberDTO.getName();
		this.sex = memberDTO.getSex();
		this.birth = memberDTO.getBirth();
		this.myPhoneNumber = memberDTO.getMyPhoneNumber();
		this.parentsPhoneNumber = memberDTO.getParentsPhoneNumber();
		this.address = memberDTO.getAddress();
		this.school = memberDTO.getSchool();
		this.grade = memberDTO.getGrade();
		this.memo = memberDTO.getMemo();
	}
	
	
}

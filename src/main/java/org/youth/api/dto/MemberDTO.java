package org.youth.api.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.youth.api.code.SexType;
import org.youth.api.entity.MemberEntity;
import org.youth.api.mapper.MemberMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDTO {
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Regist {
		
		@NotBlank(message = "이름은 필수값입니다.")
		private String name;
		
		private SexType sex;
		
		@DateTimeFormat(iso = ISO.DATE)
		@NotNull(message = "생일은 필수값입니다.")
		private LocalDate birth;

		@NotBlank(message = "핸드폰 번호는 필수값입니다.")
		@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "핸드폰 번호가 형식에 맞지 않습니다. (xxx-xxxx-xxxx)")
		private String myPhoneNumber;
		
		@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "핸드폰 번호가 형식에 맞지 않습니다. (xxx-xxxx-xxxx)")
		private String parentsPhoneNumber;
		
		private String address;
		private String school;
		
		private String grade;
		
		private String memo;

		public MemberEntity toEntity() {
			return MemberMapper.INSTANCE.toEntity(this);
		}
	}
	
	
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Details {
		
		private Long memberId;
		
		private String name;
		private SexType sex;
		private LocalDate birth;
		
		private String myPhoneNumber;
		private String parentsPhoneNumber;
		private String address;
		
		private String school;
		private String grade;
		
		private String memo;

		public static Details of(MemberEntity memberDetails) {
			return MemberMapper.INSTANCE.of(memberDetails);
		}
	}
}

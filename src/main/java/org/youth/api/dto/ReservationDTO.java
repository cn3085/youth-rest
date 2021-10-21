package org.youth.api.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.youth.api.code.ReservationState;
import org.youth.api.entity.ReservationEntity;
import org.youth.api.mapper.ReservationMapper;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReservationDTO {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Regist {
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime startTime;
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime endTime;
		
		@NotNull
		private ContentsDTO.Details contents;
		
		@NotNull
		private List<MemberDTO.MemberDetails> members = new ArrayList<>();
		
		
		public ReservationEntity toEntity() {
			return ReservationMapper.INSTANCE.toEntity(this);
		}
	}
	
	
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Details {
		
		private long reservationId;
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime startTime;
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime endTime;
		
		private ReservationState state;
		
		@NotNull
		private ContentsDTO.Details contents;
		
		@NotNull
		private List<MemberDTO.MemberDetails> members = new ArrayList<>();
		
		private LocalDateTime regDate;
		
		
		public static Details of(ReservationEntity reservationDetails) {
			return ReservationMapper.INSTANCE.of(reservationDetails);
		}
		
		public ReservationEntity toEntity() {
			return ReservationMapper.INSTANCE.toEntity(this);
		}
	}
	
	
	
	@NoArgsConstructor
	@Getter
	@Setter
	public static class DoubleBookingRes {
		
		private long reservationId;
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime startTime;
		
		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
		private LocalDateTime endTime;
		
		private ReservationState state;
		
		private ContentsDTO.Details contents;
		
		public static ReservationDTO.DoubleBookingRes of(ReservationEntity reservationDetails) {
			return ReservationMapper.INSTANCE.ofDoubleBookingRes(reservationDetails);
		}
		
	}
}

package org.youth.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;
import org.youth.api.code.ReservationState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="reservation")
@Where(clause = "data_state = 'A'")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReservationEntity extends BaseDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reservationId;
	
//	private ContentsEntity contents;
	
//	private List<MemberEntity> members = new ArrayList<>();
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	private ReservationState state;
}

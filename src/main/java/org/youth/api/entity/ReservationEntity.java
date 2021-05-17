package org.youth.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.youth.api.enumtype.ReservationState;

//@Entity(name="reservation")
//public class ReservationEntity {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	private String reservationId;
//	
//	private ContentsEntity contents;
//	
//	private List<MemberEntity> members = new ArrayList<>();
//	
//	private LocalDateTime startTime;
//	private LocalDateTime endTime;
//	
//	private ReservationState state;
//}

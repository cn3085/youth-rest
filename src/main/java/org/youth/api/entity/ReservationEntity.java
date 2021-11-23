package org.youth.api.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;
import org.youth.api.code.ReservationState;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="RESERVATION")
@Where(clause = "data_state = 'A'")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "reservationId")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReservationEntity extends BaseDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long reservationId;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	@Builder.Default
	private long useMinute = 0;
	
	@Builder.Default
	private ReservationState state = ReservationState.OK;
	
	@ManyToOne
	@JoinColumn(name="CONTENTS_ID")
	private ContentsEntity contents;
	
	@Builder.Default
	@BatchSize(size=100)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="RESERVATION_MEMBER",
				joinColumns = @JoinColumn(name="RESERVATION_ID"),
				inverseJoinColumns = @JoinColumn(name="MEMBER_ID"))
	private Set<MemberEntity> members = new LinkedHashSet<>();
	
	
	public void addMember(MemberEntity member) {
		members.add(member);
		member.getReservations().add(this);
	}

	public void cancel() {
		this.state = ReservationState.CANCEL;
	}
	
	@Override
	public String toString() {
		return "";
	}

	
	public void updateDetails(@Valid ReservationDTO.Details reservationDTO) {
		
		this.startTime = reservationDTO.getStartTime();
		this.endTime = reservationDTO.getEndTime();
		this.useMinute = reservationDTO.getUseMinute();
		this.state = reservationDTO.getState();
		this.contents = reservationDTO.getContents().toEntity();
		this.members = reservationDTO.getMembers().stream().map(MemberDTO.MemberDetails::toEntity).collect(Collectors.toSet());
	}


}

package org.youth.api.exception.reservation;

import java.util.List;

import org.youth.api.dto.MemberDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ContainsAnotherReservationException extends RuntimeException{

	private static final long serialVersionUID = 1827624657841129501L;
	
	private final transient List<MemberDTO.DoubleBookingRes> bannedMemberList;
	

	@Override
	public String getMessage() {
		return "다른 예약이 진행중인 사용자입니다.";
	}
}

package org.youth.api.exception.reservation;

import java.util.List;

import org.youth.api.dto.MemberDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OverTimeUseMemberException extends RuntimeException {
	
	private static final long serialVersionUID = 5995340270641808853L;

	private final long reservationMaxMinute;
	private final transient List<MemberDTO.OverTimeUseRes> overTimeMembers;
	
	@Override
	public String getMessage() {
		return "해당 컨텐츠의 사용제한시간이 초과되도록 예약할 수 없습니다.(" + reservationMaxMinute + " 분)";
	}
	
}

package org.youth.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.ContentsParam;
import org.youth.api.dto.ExcelTitleDTO;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.MemberParam;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.service.ContentsService;
import org.youth.api.service.MemberService;
import org.youth.api.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/view/excel")
public class ExcelController {
	
	private final MemberService memberService;
	private final ContentsService contentsService;
	private final ReservationService reservationService;
	
	
	
	@GetMapping(path = "/members", params = "format=xls")
	public String downloadMemberList(MemberParam searchParam, Model model) {
		
		String fileName = "member_list(" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + ")";
		
		List<MemberDTO.Details> memberList = memberService.getMembers(searchParam);
		
		List<ExcelTitleDTO> titleList = new ArrayList<>();
		
		titleList.add(new ExcelTitleDTO("memberId", "아이디", 10));
		titleList.add(new ExcelTitleDTO("name", "이름", 20));
		titleList.add(new ExcelTitleDTO("sex", "성별", 10));
		titleList.add(new ExcelTitleDTO("birth", "생일", 30));
		titleList.add(new ExcelTitleDTO("myPhoneNumber", "연락처1", 30));
		titleList.add(new ExcelTitleDTO("parentsPhoneNumber", "연락처2", 30));
		titleList.add(new ExcelTitleDTO("school", "학교", 30));
		titleList.add(new ExcelTitleDTO("grade", "학년", 10));
		titleList.add(new ExcelTitleDTO("address", "주소", 60));
		titleList.add(new ExcelTitleDTO("memo", "메모", 50));
		titleList.add(new ExcelTitleDTO("regDate", "등록일", 30));
		
		
		List<Map<String, Object>> bodyList = memberList.stream().map( m -> {
			Map<String, Object> body = new HashMap<>();
			body.put("memberId", m.getMemberId());
			body.put("name", m.getName());
			body.put("sex", m.getSex().value());
			body.put("birth", m.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			body.put("myPhoneNumber", m.getMyPhoneNumber());
			body.put("parentsPhoneNumber", m.getParentsPhoneNumber());
			body.put("school", m.getSchool());
			body.put("grade", m.getGrade());
			body.put("address", m.getAddress());
			body.put("memo", m.getMemo());
			body.put("regDate", m.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			return body;
		}).collect(Collectors.toList());
		
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("titleList", titleList);
		model.addAttribute("bodyList", bodyList);
		
		return "BasicExcelDownloadView";
	}
	
	
	
	@GetMapping(path = "/contents", params = "format=xls")
	public String downloadContentsList(ContentsParam searchParam, Model model) {
		
		String fileName = "contents_list(" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + ")";
		
		List<ContentsDTO.Details> contentsList = contentsService.getContents(searchParam);
		
		List<ExcelTitleDTO> titleList = new ArrayList<>();
		
		titleList.add(new ExcelTitleDTO("contentsId", "아이디", 10));
		titleList.add(new ExcelTitleDTO("name", "이름", 30));
		titleList.add(new ExcelTitleDTO("enableReservation", "예약 가능 여부", 30));
		titleList.add(new ExcelTitleDTO("description", "설명", 40));
		titleList.add(new ExcelTitleDTO("regDate", "등록일", 30));
		
		
		List<Map<String, Object>> bodyList = contentsList.stream().map( m -> {
			Map<String, Object> body = new HashMap<>();
			body.put("contentsId", m.getContentsId());
			body.put("name", m.getName());
			body.put("enableReservation", m.isEnableReservation() ? "가능" : "불가");
			body.put("description", m.getDescription());
			body.put("regDate", m.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			return body;
		}).collect(Collectors.toList());
		
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("titleList", titleList);
		model.addAttribute("bodyList", bodyList);
		
		return "BasicExcelDownloadView";
	}
	
	
	
	@GetMapping(path = "/reservations", params = "format=xls")
	public String downloadReservationList(ReservationParam searchParam, Model model) {
		
		String fileName = "reservation_list(" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + ")";
		
		List<ReservationDTO.Details> reservationList = reservationService.getReservationList(searchParam);
		
		List<ExcelTitleDTO> titleList = new ArrayList<>();
		
		titleList.add(new ExcelTitleDTO("reservationId", "아이디", 10));
		titleList.add(new ExcelTitleDTO("startTime", "예약시간(시작)", 30));
		titleList.add(new ExcelTitleDTO("endTime", "예약시간(종료)", 30));
		titleList.add(new ExcelTitleDTO("state", "예약상태", 15));
		titleList.add(new ExcelTitleDTO("contentsId", "콘텐츠 아이디", 10));
		titleList.add(new ExcelTitleDTO("contents", "콘텐츠 이름", 20));
		titleList.add(new ExcelTitleDTO("members", "예약 회원", 40));
		titleList.add(new ExcelTitleDTO("regDate", "등록일", 30));
		
		
		List<Map<String, Object>> bodyList = reservationList.stream().map( r -> {
			Map<String, Object> body = new HashMap<>();
			body.put("reservationId", r.getReservationId());
			body.put("startTime", r.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			body.put("endTime", r.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			body.put("state", r.getState().getValue());
			body.put("contentsId", r.getContents().getContentsId());
			body.put("contents", r.getContents().getName());
			body.put("members", String.join(", ", r.getMembers().stream().map(MemberDTO.MemberDetails::getName).collect(Collectors.toList())));
			body.put("regDate", r.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			return body;
		}).collect(Collectors.toList());
		
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("titleList", titleList);
		model.addAttribute("bodyList", bodyList);
		
		return "BasicExcelDownloadView";
	}

}

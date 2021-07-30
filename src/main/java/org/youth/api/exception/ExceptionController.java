package org.youth.api.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.dto.ResponseDTO;

//@RestController
//@RequestMapping("/error")
//public class ExceptionController implements ErrorController {
//
//	@RequestMapping
//	public ResponseEntity<ResponseDTO> getError(HttpServletRequest request, Model model) {
//		String status = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
//		String errorMsg = "";
//		switch (status) {
//		case "404":
//			errorMsg = "존재하지 않는 페이지입니다.";
//			break;
//		case "405":
//			errorMsg = "허용되지 않은 접근방법입니다.";
//			break;
//		default:
//			errorMsg = "존재하지 않는 페이지입니다.";
//			break;
//		}
//		
//		
//		return new ResponseEntity<ResponseDTO>();
//	}
//	
//}

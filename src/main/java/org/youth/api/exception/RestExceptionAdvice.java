package org.youth.api.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.exception.member.AlreadyRegistedMemberException;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionAdvice {
	
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ResponseDTO> usernameNotFoundException(HttpServletRequest request,
																HttpServletResponse response,
																Object handler,
																Exception exception) {

		return new ResponseEntity<>(ResponseDTO.builder()
											   .code(ResponseCode.FAIL)
											   .message(exception.getMessage())
										   	   .build(), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(ContainsAnotherReservationException.class)
	public ResponseEntity<ResponseDTO> containsAnotherReservationException(
																HttpServletRequest request,
																HttpServletResponse response,
																Object handler,
																ContainsAnotherReservationException exception) {
		
		return new ResponseEntity<>(ResponseDTO.builder()
											   .code(ResponseCode.FAIL)
											   .message(exception.getMessage())
											   .data(exception.getBannedMemberList())
										   	   .build(), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(DoubleBookingException.class)
	public ResponseEntity<ResponseDTO> doubleBookingException(HttpServletRequest request,
																HttpServletResponse response,
																Object handler,
																Exception exception) {
		
		return new ResponseEntity<>(ResponseDTO.builder()
												.code(ResponseCode.FAIL)
												.message(exception.getMessage())
												.build(), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ResponseDTO> illegalStateExceptionHandle(HttpServletRequest request,
																	HttpServletResponse response,
																	Object handler,
																	Exception exception) {
		
		return new ResponseEntity<>(ResponseDTO.builder()
											   .code(ResponseCode.FAIL)
											   .message(exception.getMessage())
											   .build(), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(value = BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseDTO> handleException(BindException exception) {
		return new ResponseEntity<>(ResponseDTO.builder()
											   .code(ResponseCode.FAIL)
											   .data(getResultMessage(exception.getBindingResult().getFieldErrors().iterator()))
											   .build(), HttpStatus.OK);
    }
	
	
	
	protected List<InvalidParam> getResultMessage(final Iterator<FieldError> errorIterator) {
		
		List<InvalidParam> invalidParams = new ArrayList<>();
		
        while (errorIterator.hasNext()) {
            final FieldError error = errorIterator.next();
            
            InvalidParam param = new InvalidParam();
            param.setField(error.getField());
            param.setValue(error.getRejectedValue());
            param.setMessage(error.getDefaultMessage());
            
            invalidParams.add(param);
        }
        
        return invalidParams;
    }
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> exceptionHandler(HttpServletRequest request,
													    HttpServletResponse response,
													    Object handler,
													    Exception exception) {
		log.error(exception.toString());
		
		return new ResponseEntity<>(ResponseDTO.builder()
												.message("요청을 처리하지 못했습니다. 잠시 후 다시 시도해주십시오.")
												.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	
	@ExceptionHandler(RuntimeException.class) //AlreadyRegistedMemberException
	public ResponseEntity<ResponseDTO> runtimeHandler(HttpServletRequest request,
														HttpServletResponse response,
														Object handler,
														Exception exception) {
		
		return new ResponseEntity<>(ResponseDTO.builder()
				.code(ResponseCode.FAIL)
				.message(exception.getMessage())
				.build(), HttpStatus.OK);
	}
}

package org.youth.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
	
	
	@ExceptionHandler(IllegalStateException.class)
	public ModelAndView illegalStateExceptionHandle(HttpServletRequest request,
													HttpServletResponse response,
													Object handler,
													Exception exception) {
		log.info(exception.toString(), exception.getMessage());
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/error");
		mav.addObject("errorMsg", exception.getMessage());
		
		return mav;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception exception) {
		
		final String msg = exception.getMessage();
		
		log.error(exception.toString());
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/error");
		mav.addObject("msg", msg);
		
		return mav;
	}

}

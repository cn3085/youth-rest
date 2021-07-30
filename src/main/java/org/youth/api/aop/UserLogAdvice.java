//package org.youth.api.aop;
//
//import java.lang.reflect.Method;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import org.youth.api.annotation.UserLog;
//import org.youth.api.code.LogActCode;
//import org.youth.api.code.LogSectCode;
//import com.eltov.air.system.user.dto.UserDTO;
//import com.eltov.air.system.user.dto.UserLogDTO;
//import com.eltov.air.system.user.dto.UserSession;
//import com.eltov.air.system.user.service.UserLogService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class UserLogAdvice {
//	
//	public static final String FIELD_METHOD_PREFIX = "get";
//	
//	private final UserLogService userLogService;
//	
////	@AfterReturning(value="execution(* com.eltov.air.system.user.UserService*.*(..))")
////	@AfterReturning(value="execution(* com.eltov.air.system.user.UserService*.loadUserByUsername(..))")
////	public void excute(JoinPoint jp) throws Throwable {
////		logger.info("hello : {}");
////	}
//	
//	
//	/**
//	 * @param jp
//	 * @throws Throwable
//	 */
//	@AfterReturning(pointcut = "@annotation(org.youth.api.annotation.UserLog)", returning = "returnEntity")
//	public void excute(JoinPoint jp, Object returnEntity) {
//		
//		try {
//			//어노테이션 값 get
//			MethodSignature methodSignature = (MethodSignature)jp.getSignature();
//			UserLog userLog = methodSignature.getMethod().getAnnotation(UserLog.class);
//			
//			Class<?> targetClass = userLog.targetClass();
//			String idField = userLog.idField();
//			String idNameField = userLog.idNameField();
//			String msgField = userLog.msgField();
//			LogSectCode userLogSect = userLog.logSect();
//			LogActCode userLogAct = userLog.logAct();
//			
//			
////            Object foundedTargetObject = Arrays.stream(jp.getArgs())
////                    .filter( arg -> arg.getClass() == targetClass)
////                    .findFirst()
////                    .orElseThrow(() -> new Exception("The argument of these class type could not be found. " + targetClass));
//            
//			if(returnEntity == null || returnEntity.getClass() != targetClass) {
//				throw new Exception("The return value type could not be found. " + targetClass);
//			}
//            
//            
//			
//			Class<?> cls = Class.forName(targetClass.getName());
//			
//			Long targetId = (Long)callMethod(returnEntity, cls, idField);
//			String targetIdName = valueToString(callMethod(returnEntity, cls, idNameField));
//			String targetMsg = valueToString(callMethod(returnEntity, cls, msgField));
//			
//			if(targetId == null && targetIdName == null && targetMsg == null) {
//				Object aopTargetMethodName = jp.toLongString();
//				logger.warn("Advice location:{} - message: {}", aopTargetMethodName, "All log data is null. Do you want it?");
//			}
//			
//			UserSession userSession = (UserSession)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			UserDTO user = userSession.getUser();
//			
//			userLogService.registUserLog(UserLogDTO.builder()
//												   .userId(user.getUserId())
//												   .userName(user.getUserName())
//												   .brnId(user.getBranch().getBrnId())
//												   .brnName(user.getBranch().getBrnName())
//												   .logAct(userLogAct)
//												   .logSect(userLogSect)
//												   .codeName(targetIdName)
//												   .codeId(targetId)
//												   .logMsg(targetMsg)
//												   .build());
//			
//		}catch(Exception e) {
//			Object aopTargetMethodName = jp.toLongString();
//			logger.error("Advice location:{} - message: {}", aopTargetMethodName, e.toString());
//			
//		}
//	}
//	
//	
//	/**
//	 * optional값인 fieldName이 없는 경우는 null을 리턴
//	 * @param targetObject
//	 * @param targetClass
//	 * @param fieldName
//	 * @return
//	 * @throws Exception
//	 */
//	private Object callMethod(Object targetObject, Class<?> targetClass, String fieldName) throws Exception {
//		
//		if(StringUtils.isBlank(fieldName)) {
//			return null;
//		}
//		
//		String getMethodName = FIELD_METHOD_PREFIX + StringUtils.capitalize(fieldName);
//		Method getMethod = targetClass.getDeclaredMethod(getMethodName);
//		
//		return getMethod.invoke(targetObject);
//	}
//	
//	
//	private String valueToString(Object value) {
//		if(value == null) {
//			return null;
//		}
//		if(value instanceof String) {
//			return (String)value;
//		}else {
//			return value.toString();
//		}
//	}
//
//}

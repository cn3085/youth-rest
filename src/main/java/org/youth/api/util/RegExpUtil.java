package org.youth.api.util;

public class RegExpUtil {
	
	public static String PHONE = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
	public static String SPECIAL_CHARACTERS = "[^\uAC00-\uD7A3xfe0-9a-zA-Z]";
	
	/**
	 * <pre>
	 * RegExpUtil.getCheckPhone("01012341234") = true;
	 * RegExpUtil.getCheckPhone("019-1234-1234") = true;
	 * RegExpUtil.getCheckPhone("0101231234") = true;
	 * RegExpUtil.getCheckPhone("  010-1234-1234") = false;
	 * RegExpUtil.getCheckPhone("013-1234-1234") = false;
	 * RegExpUtil.getCheckPhone("") = false;
	 * RegExpUtil.getCheckPhone("  ") = false;
	 * RegExpUtil.getCheckPhone(null) = false;
	 * </pre>
	 * @param phone
	 * @return boolean
	 */
	public static boolean isPhoneExp(String phone) {
		if(phone == null) return false;
		return phone.matches(PHONE);
	}

}

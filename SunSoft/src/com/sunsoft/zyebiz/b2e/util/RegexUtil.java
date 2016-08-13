package com.sunsoft.zyebiz.b2e.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * ���ƺ���Pattern
	 */
	public static final Pattern PLATE_NUMBER_PATTERN = Pattern
			.compile("^[\u0391-\uFFE5]{1}[a-zA-Z0-9]{6}$");

	/**
	 * ֤������Pattern
	 */
	public static final Pattern ID_CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * ����Pattern
	 */
	public static final Pattern CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * �̶��绰����Pattern
	 */
	public static final Pattern PHONE_NUMBER_PATTERN = Pattern
			.compile("0\\d{2,3}-[0-9]+");

	/**
	 * ��������Pattern
	 */
	public static final Pattern POST_CODE_PATTERN = Pattern.compile("\\d{6}");

	/**
	 * ���Pattern
	 */
	public static final Pattern AREA_PATTERN = Pattern.compile("\\d*.?\\d*");

	/**
	 * �ֻ����Pattern
	 */
	public static final Pattern MOBILE_NUMBER_PATTERN = Pattern
			.compile("^(0|86|17951)?(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$");

	/**
	 * �����ʺ�Pattern
	 */
	public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern
			.compile("\\d{16,21}");

	/**
	 * ���ƺ����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPlateNumber(String s) {
		Matcher m = PLATE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ֤�������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIDCode(String s) {
		Matcher m = ID_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isCode(String s) {
		Matcher m = CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �̻������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhoneNumber(String s) {
		Matcher m = PHONE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ���������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPostCode(String s) {
		Matcher m = POST_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isArea(String s) {
		Matcher m = AREA_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �ֻ�������ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isMobileNumber(String s) {
		Matcher m = MOBILE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}
	/**
	 * �Ƿ�Ϊ������
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    System.out.println( pattern.matcher(str).matches());
	    return pattern.matcher(str).matches();     
	}  

	/**
	 * �����˺ŷ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isAccountNumber(String s) {
		Matcher m = ACCOUNT_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

}

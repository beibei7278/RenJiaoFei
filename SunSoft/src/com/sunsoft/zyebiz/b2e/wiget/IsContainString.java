package com.sunsoft.zyebiz.b2e.wiget;
/**
 * Url字符串中是否包含str字符串
 * @author YGC
 *
 */
public class IsContainString {
	public static boolean containsString(String url, String str) {
		boolean flag = false;
		if (url.contains(str)) {
			flag = true;
		}
		return flag;
	}
}


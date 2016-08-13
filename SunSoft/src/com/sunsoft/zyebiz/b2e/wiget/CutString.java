package com.sunsoft.zyebiz.b2e.wiget;

import java.io.UnsupportedEncodingException;

public class CutString {	  
	    /**  
	     * 判断是否是一个中文汉字  
	     *   
	     * @param c  
	     *            字符  
	     * @return true表示是中文汉字，false表示是英文字母  
	     * @throws UnsupportedEncodingException  
	     *             使用了JAVA不支持的编码格式  
	     */  
	    public static boolean isChineseChar(char c)   
	            throws UnsupportedEncodingException {   
	        // 如果字节数大于1，是汉字   
	        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
	        return String.valueOf(c).getBytes("UTF-8").length > 1;   
	    }   
	  
	    /**  
	     * 按字节截取字符串  
	     *   
	     * @param orignal  
	     *            原始字符串  
	     * @param count  
	     *            截取位数  
	     * @return 截取后的字符串  
	     * @throws UnsupportedEncodingException  
	     *             使用了JAVA不支持的编码格式  
	     */  
	    public static String substring(String orignal, int count)   
	            throws UnsupportedEncodingException {   
	        // 原始字符不为null，也不是空字符串   
	        if (orignal != null && !"".equals(orignal)) {   
	            // 将原始字符串转换为GBK编码格式   
	            orignal = new String(orignal.getBytes(), "UTF-8");   
	            // 要截取的字节数大于0，且小于原始字符串的字节数   
	            if (count > 0 && count < orignal.getBytes("UTF-8").length) {   
	                StringBuffer buff = new StringBuffer();   
	                char c;   
	                for (int i = 0; i < count; i++) {   
	                    c = orignal.charAt(i);   
	                    buff.append(c);   
	                    if (CutString.isChineseChar(c)) {   
	                        // 遇到中文汉字，截取字节总数减1   
	                        count-=2;   
	                    }   
	                }   
	                return buff.toString();   
	            }   
	        }   
	        return orignal;   
	    }   
	  


	static String subStr(String str, int subSLength)
			throws UnsupportedEncodingException {
		if (str == null)
			return "";
		else {
			int tempSubLength = subSLength;// 截取字节数
			String subStr = str.substring(0,
					str.length() < subSLength ? str.length() : subSLength);// 截取的子串
			int subStrByetsL = subStr.getBytes("GBK").length;// 截取子串的字节长度
			// int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
			// 说明截取的字符串中包含有汉字
			while (subStrByetsL > tempSubLength) {
				int subSLengthTemp = --subSLength;
				subStr = str.substring(0,
						subSLengthTemp > str.length() ? str.length()
								: subSLengthTemp);
				subStrByetsL = subStr.getBytes("GBK").length;
				// subStrByetsL = subStr.getBytes().length;
			}
			return subStr;
		}
	}

	
}
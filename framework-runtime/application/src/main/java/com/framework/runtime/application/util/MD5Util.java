/**
* @Project: fmp-agent
* @Package com.freemypay.util
* FileName：MD5Util.java
* Version：v1.0
* date：2014-7-1
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.security.MessageDigest;

/**
* FileName: MD5Util.java
* @Description: MD5加密工具类
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月19日 下午2:50:26
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年3月19日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class MD5Util
{
	public static String toMD5(String s)
	{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try
		{
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static String toMD5UseKey(String s) {
		s = s + "&key=FI09KI3A1MBUPZ6Y52";
		return toMD5(s);
	}
	
	public static String toMD5UseKey(String s, String key) {
		s = s + "&key=" + key;
		return toMD5(s);
	}
}

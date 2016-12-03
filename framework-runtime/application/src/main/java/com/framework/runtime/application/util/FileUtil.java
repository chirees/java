/**
* @Project: framework-runtime-application
* @Package com.framework.runtime.application.util
* FileName：FileUtil.java
* Version：v1.0
* date：2016年3月26日
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.framework.runtime.application.LogU;

/**
* FileName: FileUtil.java
* @Description: 文件操作工具类
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月26日 上午11:04:58
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年3月26日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class FileUtil
{
	// GBK会有乱码
	// private static final String ENCODING_GBK = "GBK";
	public static final String ENCODING_UTF8 = "utf-8";

	/**
	* @Title: readFileByLines
	* @Description:按行读取上传文件
	* @param file
	* @return param
	* @return List<String> return type
	* @throws
	*/
	public static List<String> readFileByLines(MultipartFile file)
	{
		Set<String> snCodeSet = new HashSet<String>();
		BufferedReader reader = null;
		try
		{
			// InputStreamReader fReader = new InputStreamReader(file.getInputStream(),
			// ENCODING_UTF8);
			reader = new BufferedReader(new UnicodeReader(file.getInputStream(), Charset.defaultCharset().name()));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null)
			{
				if (StringUtils.isNotBlank(tempString))
				{
					snCodeSet.add(tempString.trim());
				}
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return new ArrayList<String>(snCodeSet);
	}

	/**
	* @Title: handleFileName
	* @Description: 根据浏览器类型确定编码方式，处理文件名乱码
	* @param request
	* @param fileName
	* @return 参数
	* @return String 返回类型
	* @throws
	*/
	public static String handleFileName(HttpServletRequest request, String fileName)
	{
		String browserType = request.getHeader("user-agent");
		try
		{
			if (browserType.contains("Firefox"))
			{
				// fireforx
				fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
			}
			else if (browserType.contains("MSIE"))
			{
				// IE
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * 指定返回的类型及文件名
	 * @param response
	 * @param fileName
	 * @param contentType
	 */
	public static void setResponse(HttpServletResponse response, String fileName, String contentType) {
		response.setHeader("Content-Disposition",
				"attachment; filename=" + convertEncode(fileName, "GB2312", "ISO-8859-1"));
		response.setContentType(contentType);
	}
	
	/**
	 * 字符格式转换
	 * @param fileName
	 * @return
	 */
	public static String convertEncode(String fileName, String origEncode, String targEncode) {
		String fileName_ = "";
		try {
			fileName_ = new String(fileName.getBytes(origEncode), targEncode);
		} catch (UnsupportedEncodingException e) {
			LogU.e("FileUtil", "convertEncode error", e);
		}
		return fileName_;
	}

	/**
	 * 格式化路径地址
	 * @param path
	 * @return
	 */
	public static String formatPath(String path) {
		path = path.trim();
		StringBuffer sb = new StringBuffer();
		char[] chars = path.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '/' || chars[i] == '\\') {
				sb.append(File.separator);
				for (int j = i; j < chars.length; j++) {
					if (chars[j] != '/' && chars[j] != '\\') {
						sb.append(chars[j]);
						i = j;
						break;
					}
				}
			} else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}
}

/**
* @Project: fmp-agent
* @Package com.freemypay.util
* FileName：CascadeJsonUtil.java
* Version：v1.0
* date：2014-8-4
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.util.Map;
import java.util.Set;

/**
* FileName: CascadeJsonUtil.java
* @Description: j-ui级联下拉列表返回json值处理工具类
*				服务器端返回 json 格式：
*				[
*				["all", "所有城市"],
*				["bj", "北京市"]
*				]
* @author: Hubert 
* @version: v1.0
* @create at: 2014-8-4 上午11:28:54
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2014-8-4    Hubert    v1.0        XXXX
*
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class CascadeJsonUtil
{
	public static String FIRST_SELECT = "请选择";

	/**
	* @Title: trans2ComboxJson
	* @Description:转换为下拉列表所属数据格式
	* @param map
	* @return param
	* @return String return type
	* @throws
	*/
	public static String trans2ComboxJson(Map<String, String> map)
	{
		StringBuffer sbuf = new StringBuffer("[[\"\",\"").append(FIRST_SELECT)
				.append("\"],");

		if (null != map)
		{
			Set<String> keys = map.keySet();
			if (null != keys && keys.size() > 0)
			{
				for (String key : keys)
				{
					sbuf.append("[\"").append(key).append("\",");
					sbuf.append("\"").append(map.get(key)).append("\"],");
				}
			}
		}

		String result = sbuf.substring(0, sbuf.length() - 1).toString() + "]";
		return result;
	}
}

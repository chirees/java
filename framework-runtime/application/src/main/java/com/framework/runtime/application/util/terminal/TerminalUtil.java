package com.framework.runtime.application.util.terminal;

import java.util.HashMap;
import java.util.Map;
/**
 *	设备工具类
 */
public enum TerminalUtil {
	;
	private static Map<String,String> map = new HashMap<String, String>();
	static{
		//测试设备列表,无需判断商户绑定
		map.put("Q8NL00000098", "1");
		map.put("Q0NL01196489", "1");
		map.put("Q0NL02624803", "1");
		map.put("Q0NL01777741", "1");
		map.put("Q0NL01196400", "1");
		map.put("Q0NL01196401", "1");
		map.put("Q0NL01196402", "1");
		map.put("Q0NL01196403", "1");
		map.put("Q0NL01196404", "1");
		map.put("Q0NL01196405", "1");
		map.put("Q0NL01196406", "1");
		map.put("Q0NL01196407", "1");
		map.put("Q0NL01196408", "1");
		map.put("Q0NL01196409", "1");
		map.put("Q8NL00000088", "1");
		map.put("F10100000001", "1");
		map.put("F90100000010", "1");
		map.put("F90100000006", "1");
		map.put("F90100000012", "1");
		map.put("F90300000003", "1");
		map.put("F90100000001", "1");
		map.put("F90300000002", "1");
		map.put("F90300000001", "1");
		map.put("F80100000001", "1");
		map.put("F80100000002", "1");
		map.put("F80100000003", "1");
		map.put("F80100000004", "1");
		map.put("F80100000005", "1");
		map.put("F90100000003", "1");
		map.put("F10100000008", "1");
		map.put("JHLA601601003598", "1");
		map.put("F90100000007", "1");
	}
	/**判断是否是指定测试设备*/
	public static boolean isTestTerminal(String snno){
		if(map.containsKey(snno)){
			return true;
		}
		
		return false;
	}
}

package com.framework.runtime.application.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;

public class StringUtil {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	// 只支持中国手机号
	public static String formatMobilePhone(String orgiMobilePhone) {
		if(orgiMobilePhone == null)
			return null;
		
		orgiMobilePhone = orgiMobilePhone.replaceAll("[^0-9]", "");
		
		if(orgiMobilePhone.length() > 11) {
			orgiMobilePhone = orgiMobilePhone.substring(orgiMobilePhone.length() - 11);
		}
		
		return orgiMobilePhone;
	}
	
	 public static String  getFirstPinyin(String strCN)   {  
	        if(null == strCN || strCN.trim().length() == 0){  
	            return "";  
	        }  
	        
	        Character  first = null;
	        try {
	        strCN = strCN.trim();
//	        StringBuffer firstSpell = new StringBuffer(); 
	       
	        char[] charOfCN = strCN.toCharArray();  
	        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
	        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
	        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
//	        for (int i = 0; i < charOfCN.length; i++) {  
	            // 是否为中文字符  
	            if (charOfCN[0] > 128) {  
	                String[] spellArray = PinyinHelper.toHanyuPinyinStringArray(  
	                        charOfCN[0], format);  
	                // 取拼音首字母  
	                if (null != spellArray) {  
	                	first = spellArray[0].charAt(0);  
	                }else{  
	                	first = charOfCN[0];  
	                }  
	            } else {  
	            	first = charOfCN[0];  
	            }  
//	        } 
	        } catch (Exception e) {
	        	logger.error("", e);
	        	return "";
	        }
	        return first.toString();  
	    } 
	

	public static void main(String[] args) throws Exception {
//		System.out.println(StringUtil.formatMobilePhone("1795113412345678"));
//		System.out.println(StringUtil.formatMobilePhone("+8613412345678"));
//		System.out.println(StringUtil.formatMobilePhone("+86134 1234 5678"));
//		System.out.println(StringUtil.formatMobilePhone("134-1234 5678"));
//		System.out.println(StringUtil.getFirstPinyin("13412345678"));
		System.out.println(StringUtil.getFirstPinyin("Allen"));
		System.out.println(StringUtil.getFirstPinyin("s哈哈"));
		System.out.println(StringUtil.getFirstPinyin("熊亮"));
		System.out.println(StringUtil.getFirstPinyin("*熊亮"));
		System.out.println(StringUtil.getFirstPinyin("!熊亮"));
		System.out.println(StringUtil.getFirstPinyin("%熊亮"));
		System.out.println(StringUtil.getFirstPinyin("~~熊亮"));
//		System.out.println(StringUtil.formatMobilePhone("13412345678"));
	}
	
	public static String randomStr(int len){
        if(len < 3){
            throw new IllegalArgumentException("字符串长度不能小于3");
        }
        //数组，用于存放随机字符
        char[] chArr = new char[len];
        //为了保证必须包含数字、大小写字母
        chArr[0] = (char)('0' + StdRandom.uniform(0,10));
        chArr[1] = (char)('A' + StdRandom.uniform(0,26));
        chArr[2] = (char)('a' + StdRandom.uniform(0,26));
        
    
        char[] codes = { '0','1','2','3','4','5','6','7','8','9',
                         'A','B','C','D','E','F','G','H','I','J',
                         'K','L','M','N','O','P','Q','R','S','T',
                         'U','V','W','X','Y','Z','a','b','c','d',
                         'e','f','g','h','i','j','k','l','m','n',
                         'o','p','q','r','s','t','u','v','w','x',
                         'y','z'};
        //charArr[3..len-1]随机生成codes中的字符
        for(int i = 3; i < len; i++){
            chArr[i] = codes[StdRandom.uniform(0,codes.length)];
        }
        
        //将数组chArr随机排序
        for(int i = 0; i < len; i++){
            int r = i + StdRandom.uniform(len - i);
            char temp = chArr[i];
            chArr[i] = chArr[r];
            chArr[r] = temp;
        }
        
        return new String(chArr);
    }
	
	/**
	 * @Description: 左边填充
	 * @param str待填充的数字
	 * @param strLength所需的位数
	 * @return
	 */
	public static String addZeroForNumLeft(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左(前)补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}
	
	public static String removeZeroForNumLeft(String str){
		int index = 0;//预定义第一个非零字符串的位置
		char strs[] = str.toCharArray();// 将字符串转化成字符数组
		for(int i = 0; i < str.length(); i++){
			if('0' != strs[i]){
				index = i;// 找到非零字符串并跳出
				break;
			}
		}
		return str.substring(index, str.length());
	}
	/**替换字符串,左边留几位,右边留几位,以什么字符串替换*/
	public static String pad(String value,int left,int right,String replace){
		if(value == null){
			return "";
		}
		StringBuffer temp = new StringBuffer();
		temp.append(value.substring(0, left));
		for(int i = 0;i<value.length() - left - right;i++){
			temp.append(replace);
		}
		temp.append(value.substring(value.length() - right, value.length()));
		return temp.toString();
	}
	/**将null替换为空字符串*/
	public static String replaceNullToStr(String str){
		return str != null ? str : "";
	}
	
	public static String replaceMobileNo(String mobileNo){
		if (null != mobileNo && mobileNo != "")
			return mobileNo.substring(0,3) + "****" + mobileNo.substring(7);
		return "";
	}
}

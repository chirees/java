/**
* @Project: fmp-agent
* @Package com.freemypay.util
* FileName：RegularUtil.java
* Version：v1.0
* date：2014-8-5
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* FileName: RegularUtil.java
* @Description:正则表达式工具类
* @author: Hubert 
* @version: v1.0
* @create at: 2014-8-5 下午1:21:08
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2014-8-5    Hubert    v1.0        XXXX
*
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class RegularUtil
{

	/**
	 * 验证Email
	 * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email)
	{
		String regex = "\\w+@\\w+\\.[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 验证身份证号码
	 * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard)
	{
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * @param mobile 移动、联通、电信运营商的号码段
	 *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
	 *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
	 *<p>电信的号段：133、153、180（未启用）、189</p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkMobile(String mobile)
	{
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码
	 * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：02085588447
	 * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
	 *  数字之后是空格分隔的国家（地区）代码。</p>
	 * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
	 * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String phone)
	{
		String regex = "^\\d{11,12}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 * @param digit 一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit)
	{
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}
	
	/**
	 * 验证数字
	 */
	public static boolean checkNumber(String digit)
	{
		String regex = "^[0-9]+$";
		return Pattern.matches(regex, digit);
	}
	

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals)
	{
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}
	
	/**
	 * 验证整数和浮点数（正整数和正浮点数）
	 * @param decimals 一位或多位0-9之间的正浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPositiveDecimals(String decimals)
	{
		String regex = "^\\d+|[0-9]*\\.?[0-9]+";
		return Pattern.matches(regex, decimals);
	}
	
	/**
	 *	检验是否包含空格
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String str)
	{
		String regex = "\\s+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	* 验证特殊字符
	* @param str 待检测的字符串
	* @return 验证成功返回true，验证失败返回false
	*/
	public static boolean checkSymbol(String str)
	{
		String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 验证中文
	 * @param chinese 中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese)
	{
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）
	 * @param date 日期，格式：20131218
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDateYYYYMMMDD(String date)
	{
		String regex = "[1|2][0-9]{7}";
		return Pattern.matches(regex, date);
	}

	/**
	 * 验证URL地址
	 * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url)
	{
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * 匹配中国邮政编码
	 * @param postalcode 邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostalcode(String postalCode)
	{
		return Pattern.matches("[0-9]\\d{5}", postalCode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * @param ipAddress IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress)
	{
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	* @Title: checkContractRatePattern
	* @Description:校验商户结算费率格式
	* @param contractRate
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkContractRatePattern(String contractRate)
	{
		return Pattern.matches("^0\\.0[0-9]{1,3}$", contractRate);
	}

	/**
	* @Title: checkRateTopAmountPattern
	* @Description:校验封顶金额格式
	* @param rateTopAmount
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkRateTopAmountPattern(String rateTopAmount)
	{
		return Pattern.matches("^\\d{2,3}", rateTopAmount);
	}

	/**
	* @Title: checkContractNoPattern
	* @Description:校验合同编号格式
	* @param contractNo
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkContractNoPattern(String contractNo)
	{
		return Pattern.matches("^FLMSDXY[0-9]*", contractNo);
	}

	/**
	* @Title: checkLimitAmountPattern
	* @Description:校验单笔刷卡金额格式
	* @param limitAmount
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkLimitAmountPattern(String limitAmount)
	{
		String regex = "^[1-9]{1}[0-9]{0,1}$";
		return Pattern.matches(regex, limitAmount);
	}

	/**
	* @Title: checkBusinessArea
	* @Description:校验营业面积格式
	* @param businessArea
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkBusinessArea(String businessArea)
	{
		return Pattern.matches("^\\d*$", businessArea);
	}

	/**
	* @Title: checkFileNameContainsSymbol
	* @Description: 检测文件名是否包含特殊符号
	* @param fileName
	* @return param
	* @return boolean return type
	* @throws
	*/
	public static boolean checkFileNameContainsSymbol(String fileName)
	{
		return Pattern.matches("[ ,\\`,\\~,\\,,\\！,\\@,\\-,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\：,\\；,\\，,\\。,\\、,\\<,\\>,\\{,\\},\\(,\\),\\',\\=,\"]", fileName);
	}
	
	/**
	 * @Title: checkDecimal
	 * @Description: 校验小数 通过返回true,不通过返回false
	 * @param str
	 * @return  
	 */
	public static boolean checkDecimal(String str) {
		return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();
	}
	
	/**
	 * 验证以1-9开头的数字
	 * @param positiveInteger 匹配非0的正整数(不带+)
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkNum(String number){
		String regex = "^[1-9][0-9]*$";
		return Pattern.matches(regex, number);
	}
	
	/**
	 * 验证以1-9开头的数字长度为4
	 * @param positiveInteger 匹配非0的正整数(不带+)
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkNums(String number){
		String regex = "^[1-9][0-9]{0,3}$";
		return Pattern.matches(regex, number);
	}
	
	/**
	 * @Description: 校验用户名,验证通过返回true;不通过返回false
	 * @param str
	 * @return
	 * 3，必须是数字或字母(6-20个字符)
	 */
	public static boolean checkStr(String str){
		String regex = "^[a-zA-Z0-9]{6,20}$";
		return Pattern.matches(regex, str);
	}
	
	
	/**
	 * @Description: 校验只能输入数字，字母或中文，通过返回true，不通过返回false
	 * @param str
	 * @return
	 */
	public static boolean checkString(String str){
		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,30}$";
		return Pattern.matches(regex, str);
	}
	
	/**
	 * @Description: 校验密码只能输入数字和字母（6-20个字符）
	 * @param str
	 * @return
	 */
	public static boolean checkPassword(String str) {
		String regex = "[a-zA-Z0-9]{6,20}$";
		return Pattern.matches(regex, str);
	}
	
	public static boolean checkProfitBasicRate(String str) {
		String regex = "^(0|100|[1-9][0-9]?)$";
		return Pattern.matches(regex, str);
	}
	
	public static boolean checkProfitRatio(String str) {
		String regex = "^(0|100|[1-9][0-9]?)$";
		return Pattern.matches(regex, str);
	}
	
	/**
	 * @Description: 校验日期格式（1月（1-31），二月（1-29），三月（1-31），四月（1-30），五月（1-31），六月（30），
	 * 七月（1-31），八月（1-31），九月（1-30），十月（1-31），十一月（1-30），十二月（1-31））
	 * @param str
	 * @return boolean
	 */ 
	public static boolean checkYYYYMMDD(String str) {
//		String regex = "^(\\d{4})(0\\d{1}|1[0-2])(0\\d{1}|[12]\\d{1}|3[01])$";
		String regex = "^(\\d{4})((02(0[1-9]|1[0-9]|2[0-9]))|((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30)))$";
		return Pattern.matches(regex, str);
	}
	
}

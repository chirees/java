package com.framework.runtime.application.util.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.runtime.application.LogU;

/**
 * 短信工具类
 */
public class SmsUtils {

	/**商户审核通过*/
	public static final String MERCHANT_AUDIT_PASS = "1";
	/**商户审核不通过*/
	public static final String MERCHANT_AUDIT_NOT_PASS = "2";
	/**用户注册验证码*/
	public static final String SMS_CODE_REGIST = "3";
	/**电子签购单*/
	public static final String BILL = "4";
	/**银行卡审核不通过*/
	public static final String BANK_CARD_AUDIT_NOT_PASS = "5";
	/**修改密码验证码*/
	public static final String SMS_CODE_UPDATE_PASSWORD = "6";
	/**用户修改结算账户*/
	public static final String SMS_CODE_UPDATE_BILL = "7";
	/**修改支付密码*/
	public static final String SMS_CODE_UPDATE_PAY_PWD = "8";
	/**修改手机号*/
	public static final String SMS_CODE_UPDATE_MOBILE = "9";
	/**设置支付密码*/
	public static final String SMS_CODE_SET_PAY_PWD = "10";
	/**结算卡审核失败*/
	public static final String BILL_BANK_CARD_NOT_PASS = "11";
	/**结算卡审核成功*/
	public static final String BILL_BANK_CARD_PASS = "12";
	
	public static final String TRANS_RETURN_ACCOUNT_NOTIFY = "13";
	
	public static final String WITHDRAW_RETURN_ACCOUNT_NOTIFY = "14";
	
	/**常用卡审核失败*/
	public static final String COMMON_BANK_CARD_NOT_PASS = "15";
	/**常用卡审核成功*/
	public static final String COMMON_BANK_CARD_PASS = "16";
	/**信用卡审核失败*/
	public static final String CREDIT_BANK_CARD_NOT_PASS = "17";
	/**信用卡审核成功*/
	public static final String CREDIT_BANK_CARD_PASS = "18";
	
	/**
	 * 给手机号发送短信
	 * @param mobile	手机号
	 * @param content	发送内容
	 */
	private static void send(String mobile,String content){
		//设置url
		String url = "http://178.18.58.177:8080/fmpsms/sms/send";//?mobileNo={0}&content={1}&account=freepay_web&password=2011smsforweb&processCode=800001";
		//设置post参数
		Map<String,String> map = new HashMap<String, String>();
		map.put("mobileNo", mobile);
		map.put("content", content);
		map.put("account", "freepay_web");
		map.put("password", "2011smsforweb");
		map.put("processCode", "800001");
		//发送短信
		HttpClientUtil.post(url,map);
	}
	
	
	public static void send(String id,String mobile,String args[]){
		LogU.i("SMS", "发送短信", mobile, id + "");
		if(StringUtils.isEmpty(mobile) || !mobile.matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))[0-9]{8}$")){
			LogU.c("SmsUtils", "send", "手机号格式错误");
			return;
		}
		String content = null;
		try{
			if(MERCHANT_AUDIT_PASS.equals(id)){
				content = MessageFormat.format("恭喜您！［{0}］账户商户资料审核已通过，请使用绑定信用卡进行刷卡测试交易，完成认证。",args[0]);
			}else if(MERCHANT_AUDIT_NOT_PASS.equals(id)){
				content = MessageFormat.format("您的［{0}］账户商户资料审核未通过，未通过原因：{1}，请修改后再次提交审核。",args[0],args[1]);
			}else if(SMS_CODE_REGIST.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在注册付临门自由刷账户，如非本人操作请忽略！感谢您的使用。",args[0]);
			}else if(SMS_CODE_UPDATE_PASSWORD.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在为［{1}］账户修改密码，如非本人操作请忽略！感谢您的使用。",args[0],args[1]);
			}else if(SMS_CODE_UPDATE_BILL.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在为［{1}］账户修改结算账户，如非本人操作请忽略！感谢您的使用。",args[0],args[1]);
			}else if(SMS_CODE_UPDATE_PAY_PWD.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在为［{1}］账户修改支付密码，如非本人操作请忽略！感谢您的使用。",args[0],args[1]);
			}else if(SMS_CODE_SET_PAY_PWD.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在为［{1}］账户设置支付密码，如非本人操作请忽略！感谢您的使用。",args[0],args[1]);
			}else if(SMS_CODE_UPDATE_MOBILE.equals(id)){
				content = MessageFormat.format("校验码：{0}，10分钟内有效。您正在为［{1}］账户修改绑定手机号，如非本人操作请忽略！感谢您的使用。",args[0],args[1]);
			}else if(BILL.equals(id)){
				content = MessageFormat.format("尊敬的客户，您正在进行刷卡交易，请查看电子签购单：{0}",args[0]);
			}else if(BANK_CARD_AUDIT_NOT_PASS.equals(id)){
				content = MessageFormat.format("［{0}］账户添加常用卡失败，失败原因：{1}。请修改后重新提交。", args[0],args[1]);
			}else if(BILL_BANK_CARD_NOT_PASS.equals(id)){
				content = MessageFormat.format("［{0}］账户结算卡修改失败，失败原因：{1}。请重新提交修改结算账户申请。", args[0],args[1]);
			}else if(BILL_BANK_CARD_PASS.equals(id)){
				content = MessageFormat.format("恭喜您！［{0}］账户结算卡修改成功，注意新账户资金变动。有问题可拨打付临门客服热线400-188-7889。", args[0]);
			}else if(TRANS_RETURN_ACCOUNT_NOTIFY.equals(id)){
				content = MessageFormat.format("您{0}的收款交易{1}由于自动提现失败，我们已经将款项退回到您的余额账户，请登录APP手动提现。",args[0],args[1]);
			}else if(WITHDRAW_RETURN_ACCOUNT_NOTIFY.equals(id)){
				content = MessageFormat.format("您{0}的提现交易{1}失败，我们已经将款项退回到您的余额账户，有问题可拨打付临门客服热线400-188-7889。",args[0],args[1]);
			}else if(COMMON_BANK_CARD_NOT_PASS.equals(id)){
				content = MessageFormat.format("［{0}］账户常用卡修改失败，失败原因：{1}。请修改后重新提交。", args[0],args[1]);
			}else if(COMMON_BANK_CARD_PASS.equals(id)){
				content = MessageFormat.format("恭喜您！［{0}］账户常用卡修改成功。有问题可拨打付临门客服热线400-188-7889。", args[0]);
			}else if(CREDIT_BANK_CARD_NOT_PASS.equals(id)){
				content = MessageFormat.format("［{0}］账户信用卡修改失败，失败原因：{1}。请修改后重新提交。", args[0],args[1]);
			}else if(CREDIT_BANK_CARD_PASS.equals(id)){
				content = MessageFormat.format("恭喜您！［{0}］账户信用卡修改成功。有问题可拨打付临门客服热线400-188-7889。", args[0]);
			}
			send(mobile, content);
		}catch(Exception e){
			LogU.c("SmsUtils", "send", "args 参数数量错误");
		}

		
	}
	/**根据手机号获取手机运营商和归属地*/
	public static String getMobileInfo(String mobile){
		String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone?tel=" + mobile;
		 BufferedReader reader = null;
		    String result = null;
		    StringBuffer sbf = new StringBuffer();
//		    httpUrl = httpUrl + "?" + mobile;

		    try {
		        URL url = new URL(httpUrl);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setRequestMethod("GET");
		        // 填入apikey到HTTP header
		        connection.setRequestProperty("apikey",  "c62110815e3ca43f999bc56c5b70a6dc");
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return result;
	}
	
//	public static void main(String args[]){
//		send(MERCHANT_AUDIT_NOT_PASS,"13971249247",new String[]{"111","222"});
//	}
}

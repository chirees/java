package com.framework.runtime.application;

public class ExtendFields implements java.io.Serializable{
	private String cardPin;
	private String track2;
	private String track3;
	private String merCatCode; // MCC
	private String icFlg;
	private String identityNo; // 身份证号后6位
	private String ic55; // ic卡55域
	private String icCardNo; // ic卡片序列号
	private String entryMode; // 接入点方式
	private boolean verifyCardFlg = false;
	private String addrCode;
	
	private String posSerialNo;		//POSP终端交易流水 由网关生成
	private String orgiPosSerialNo;	//终端原始交易流水
	private String transRefNo;		//终端交易参考号
	private String orgiTransRefNo;	//终端原始交易参考号
	private String posBatchNo;		//终端批次号
	private String orgiPosBatchNo;	//终端原始交易批次号
	
	private String validateDate;
	
	
	
	
	public String getValidateDate() {
		return validateDate;
	}
	public void setValidateDate(String validateDate) {
		this.validateDate = validateDate;
	}
	/* add by Nick 2016-04-13 for 平安代付 begin */
	private String reqDateTime;	//发送请求日期时间 8日期+6时间 ：YYYYMMDD + HHmmssSSS
	/* add by Nick 2016-04-13 for 平安代付 end */
	
	public String getReqDateTime() {
		return reqDateTime;
	}
	public void setReqDateTime(String reqDateTime) {
		this.reqDateTime = reqDateTime;
	}
	
	
	public String getOrgiPosSerialNo() {
		return orgiPosSerialNo;
	}
	public void setOrgiPosSerialNo(String orgiPosSerialNo) {
		this.orgiPosSerialNo = orgiPosSerialNo;
	}
	public String getTransRefNo() {
		return transRefNo;
	}
	public void setTransRefNo(String transRefNo) {
		this.transRefNo = transRefNo;
	}
	public String getOrgiTransRefNo() {
		return orgiTransRefNo;
	}
	public void setOrgiTransRefNo(String orgiTransRefNo) {
		this.orgiTransRefNo = orgiTransRefNo;
	}
	public String getPosBatchNo() {
		return posBatchNo;
	}
	public void setPosBatchNo(String posBatchNo) {
		this.posBatchNo = posBatchNo;
	}
	public String getOrgiPosBatchNo() {
		return orgiPosBatchNo;
	}
	public void setOrgiPosBatchNo(String orgiPosBatchNo) {
		this.orgiPosBatchNo = orgiPosBatchNo;
	}
	
	public String getPosSerialNo() {
		return posSerialNo;
	}
	public void setPosSerialNo(String posSerialNo) {
		this.posSerialNo = posSerialNo;
	}
	public String getAddrCode() {
		return addrCode;
	}
	public void setAddrCode(String addrCode) {
		this.addrCode = addrCode;
	}
	public boolean isVerifyCardFlg() {
		return verifyCardFlg;
	}
	public void setVerifyCardFlg(boolean verifyCardFlg) {
		this.verifyCardFlg = verifyCardFlg;
	}
	public String getEntryMode() {
		return entryMode;
	}
	public void setEntryMode(String entryMode) {
		if(entryMode != null) {
			if(entryMode.equals("0")) {
				entryMode = "02";
			}
			else if(entryMode.equals("1")) {
				entryMode = "05";
			}
			else if(entryMode.equals("2")) {
				entryMode = "07";
			}
		}
		this.entryMode = entryMode;
	}
	public String getIcCardNo() {
		return icCardNo;
	}
	public void setIcCardNo(String icCardNo) {
		this.icCardNo = icCardNo;
	}
	public String getIc55() {
		return ic55;
	}
	public void setIc55(String ic55) {
		this.ic55 = ic55;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getCardPin() {
		return cardPin;
	}
	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	public String getTrack3() {
		return track3;
	}
	public void setTrack3(String track3) {
		this.track3 = track3;
	}
	public String getMerCatCode() {
		return merCatCode;
	}
	public void setMerCatCode(String merCatCode) {
		this.merCatCode = merCatCode;
	}
	public String getIcFlg() {
		return icFlg;
	}
	public void setIcFlg(String icFlg) {
		this.icFlg = icFlg;
	}
	
	
	
	
	
}

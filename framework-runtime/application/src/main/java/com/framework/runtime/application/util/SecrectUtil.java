package com.framework.runtime.application.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.jpos.util.NumberUtils;

import com.framework.runtime.application.Application;

public class SecrectUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecrectUtil.class);

	public static final String HEXS = "0123456789ABCDEF";
	
	public static String PIN_KEY_PLAIN = "";
	public static String MAC_KEY_PLAIN = "";
	public static String MAIN_KEY_PLAIN = "";

	public static String randKey(int num) {
		Random rand = new Random(Application.getInstance().getIdWorker().nextId());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int randNum = Math.abs(rand.nextInt()) % 16;
			sb.append(HEXS.charAt(randNum));
		}

		return sb.toString();
		//return "11111111111111111111111111111111";
	}
	
	public static String mac(byte[] data, String key) {
		int dlen = data.length;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];
		byte[] encryptedData = new byte[8];

		Arrays.fill(plainData, (byte)0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);
		
		byte[] pbs = new byte[8];
		Arrays.fill(pbs, (byte)0x00);
		
		int blen = len / 8;
		for(int i = 0; i < blen; i++) {
			for(int j = 0; j < 8; j++) {
				pbs[j] ^= plainData[i * 8 + j];
			}
		}
		
		String hex = NumberUtils.toHex(pbs);
		String asc = NumberUtils.toAsc(hex);
		String des = encrypt(asc.substring(0, 16), key);
		byte[] xor = NumberUtils.xor(des, asc.substring(16));
		
		byte[] des2 = encrypt(xor, NumberUtils.fromHex(key));
		hex = NumberUtils.toHex(des2);
		asc = NumberUtils.toAsc(hex);
		return asc.substring(0, 16);
	}
	
	public static String MAC_CBC(byte[] data, String key) {

		int dlen = data.length;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];

		Arrays.fill(plainData, (byte) 0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);

		byte[] pbs = new byte[8];
		Arrays.fill(pbs, (byte) 0x00);

		String hex = "";

		int blen = len / 8;
		for (int i = 0; i < blen; i++) {

			for (int j = 0; j < 8; j++) {
				pbs[j] ^= plainData[i * 8 + j];
			}
			pbs = encrypt(pbs, NumberUtils.fromHex(key));
			hex = NumberUtils.toHex(pbs);
			//logger.info("hex:" + hex);
		}
		return hex.substring(0, 8);
	}
	
	// add by zzy for CBC mac verify
	public static boolean isMac_CBC(byte[] data, String key) {
		int dlen = data.length - 8;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];
		byte[] encryptedData = new byte[8];

		Arrays.fill(plainData, (byte) 0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);

		logger.info("MAC CHECK:" + NumberUtils.toHex(plainData) + " " + key);

		byte[] pbs = new byte[8];
		Arrays.fill(pbs, (byte) 0x00);

		String hex = "";
		String asc = "";

		int blen = len / 8;
		for (int i = 0; i < blen; i++) {
			for (int j = 0; j < 8; j++) {
				pbs[j] ^= plainData[i * 8 + j];
			}

			pbs = encrypt(pbs, NumberUtils.fromHex(key));
			hex = NumberUtils.toHex(pbs);
			logger.info("hex:" + hex);

		}

		asc = NumberUtils.toAsc(hex);
		System.arraycopy(data, data.length - 8, pbs, 0, 8);
		String inMac = NumberUtils.toHex(pbs);

		logger.info("inmac=" + inMac + " asc=" + asc);
		return asc.startsWith(inMac);
	}
	
	//˫����DES_CBC�㷨
	public static String PIN_CBC(byte[] data, String key) {

		int dlen = data.length;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];

		Arrays.fill(plainData, (byte) 0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);

		byte[] pbs = new byte[8];
		Arrays.fill(pbs, (byte) 0x00);

		String hex = "";
		// String asc = "";

		int blen = len / 8;
		for (int i = 0; i < blen; i++) {
			for (int j = 0; j < 8; j++) {
				pbs[j] ^= plainData[i * 8 + j];
			}
			// ˫����DES�㷨
			pbs = encrypt3des(pbs, NumberUtils.fromHex(key));
			hex = NumberUtils.toHex(pbs);
			//logger.info("hex:" + hex);
		}

		return hex.substring(0, 8);
	}
	
	//add by zzy 2013_05_24 CUPS MACУ�� CBCУ�� begin
	public static String encrypt3des_CBC(String input, String key) {
		byte[] keyBytes = NumberUtils.fromHex(key);
		byte[] data = NumberUtils.getSecData(input);	//��ȡ��Ҫ���м��ܵ����
		byte[] encodes = encrypt3des(data, keyBytes);
		
		return NumberUtils.toHex(encodes);
	}
	//add by zzy 2013_05_24 CUPS MACУ�� CBCУ�� end

	public static boolean isMac(byte[] data, String key) {
		int dlen = data.length - 8;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];
		byte[] encryptedData = new byte[8];

		Arrays.fill(plainData, (byte)0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);
		
		logger.info("MAC CHECK:" + NumberUtils.toHex(plainData) + " " + key);
		
		byte[] pbs = new byte[8];
		Arrays.fill(pbs, (byte)0x00);
		
		int blen = len / 8;
		for(int i = 0; i < blen; i++) {
			for(int j = 0; j < 8; j++) {
				pbs[j] ^= plainData[i * 8 + j];
			}
		}
		
		String hex = NumberUtils.toHex(pbs);
		String asc = NumberUtils.toAsc(hex);
		String des = encrypt(asc.substring(0, 16), key);
		byte[] xor = NumberUtils.xor(des, asc.substring(16));
		
		byte[] des2 = encrypt(xor, NumberUtils.fromHex(key));
		hex = NumberUtils.toHex(des2);
		asc = NumberUtils.toAsc(hex);
		System.arraycopy(data, data.length - 8, pbs, 0, 8);
		String inMac = NumberUtils.toHex(pbs);
		
		logger.info("inmac=" + inMac + " asc=" + asc);
		return asc.startsWith(inMac);
	}
	
	public static String convertPin(String key1, String key2, String enpin) {
		String dpin = descrypt3des(enpin, key1);
		dpin = encrypt3des(dpin, key2);
		return dpin;
	}
	
	public static String convertToSystemPin(String key1, String enpin) {
		String dpin = descrypt3des(enpin, key1);
		dpin = encrypt3des(dpin, M_KEY);
		return dpin;
	}
	
	public static String convertToUpayPin(String key1, String enpin) {
		String dpin = descrypt3des(enpin, M_KEY);
		dpin = encrypt3des(dpin, key1);
		return dpin;
	}
	
	public static String descrypt(String input, String key) {
		byte[] keyBytes = NumberUtils.fromHex(key);
		byte[] data = NumberUtils.fromHex(input);
		byte[] decodes = descrypt(data, keyBytes);
		
		return NumberUtils.toHex(decodes);
	}
	
	public static String encrypt(String input, String key) {
		byte[] keyBytes = NumberUtils.fromHex(key);
		byte[] data = NumberUtils.fromHex(input);
		byte[] encodes = encrypt(data, keyBytes);
		
		return NumberUtils.toHex(encodes);
	}
	
//	public static final String M_KEY = "31313131313131313131313131313131";
	public static final String M_KEY = "111B525C2B0EB353505450B1A0DE707B";
	public static String descryptMain(String input) {
		return descrypt3des(input, M_KEY);
	}
	
	
	public static String descrypt3des(String input, String key) {
		byte[] keyBytes = NumberUtils.fromHex(key);
		byte[] data = NumberUtils.fromHex(input);
		byte[] decodes = descrypt3des(data, keyBytes);
		
		return NumberUtils.toHex(decodes);
	}
	
	public static String encrypt3des(String input, String key) {
		byte[] keyBytes = NumberUtils.fromHex(key);
		byte[] data = NumberUtils.fromHex(input);
		byte[] encodes = encrypt3des(data, keyBytes);
		
		return NumberUtils.toHex(encodes);
	}
	
	/**使用base64解密*/
	public static String decodeBase64(String str){
		return new String(Base64.getDecoder().decode(str));
	}

	// ED39FC00012FC990FE4D99CCEB815E01
	public static byte[] descrypt(byte[] input, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding"); // DES/ECB/NoPadding

			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
			byte[] result = cipher.doFinal(input);
			
			return result;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}

	}

	public static byte[] encrypt(byte[] input, byte[] key) {
		int len = (input.length / 8 + ((input.length % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];

		Arrays.fill(plainData, (byte) 32);
		System.arraycopy(input, 0, plainData, 0, input.length);
		byte[] encryptedData;
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding"); // DES/ECB/NoPadding

			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			encryptedData = cipher.doFinal(plainData);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}

		return encryptedData;
	}
	
	public static byte[] encrypt3des(byte[] input, byte[] key) {
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		
		System.arraycopy(key, 0, key1, 0, 8);
		System.arraycopy(key, 8, key2, 0, 8);
		
		byte[] encodes = SecrectUtil.encrypt(input, key1);
		//System.out.println("encrypt3des1:" + NumberUtils.toHex(encodes));
		encodes = SecrectUtil.descrypt(encodes, key2);
		//System.out.println("encrypt3des2:" + NumberUtils.toHex(encodes));
		encodes = SecrectUtil.encrypt(encodes, key1);
		//System.out.println("encrypt3des3:" + NumberUtils.toHex(encodes));
		
		return encodes;
	}
	
	public static byte[] descrypt3des (byte[] input, byte[] key) {
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		
		System.arraycopy(key, 0, key1, 0, 8);
		System.arraycopy(key, 8, key2, 0, 8);
		
		byte[] encodes = SecrectUtil.descrypt(input, key1);
		//System.out.println("descrypt3des1:" + NumberUtils.toHex(encodes));
		encodes = SecrectUtil.encrypt(encodes, key2);
		//System.out.println("descrypt3des2:" + NumberUtils.toHex(encodes));
		encodes = SecrectUtil.descrypt(encodes, key1);
		//System.out.println("descrypt3des3:" + NumberUtils.toHex(encodes));
		
		return encodes;
	}
	
	
	public static void main(String[] args) {
	
		System.out.println(NumberUtils.toHex(NumberUtils.xor("A325D5F4B43A67DF342CA3F547A4089C", "B23E87A89F34D48C6478F344E77A78E7")));
		System.out.println(SecrectUtil.encrypt3des("0000000000000000", "111B525C2B0EB353505450B1A0DE707B"));
		
//		byte[] key = NumberUtils.fromHex("ED39FC00012FC990FE4D99CCEB815E01");
//		byte[] data = NumberUtils.fromHex("DDFC1B4750BE8F0E0062B8737EC9018D");
//		
//		byte[] encodes = SecrectUtil.encrypt3des(data, key);
//		
//		System.out.println(NumberUtils.toHex(encodes));
//		
//		byte[] decodes = SecrectUtil.descrypt3des(NumberUtils.fromHex("DDFC1B4750BE8F0E0062B8737EC9018D"), key);
//		
//		System.out.println(NumberUtils.toHex(decodes));
		//byte[] decodes = SecrectUtil.descrypt(encodes, NumberUtils.fromHex("FE4D99CCEB815E01"));
		
		//encodes = SecrectUtil.encrypt(decodes, NumberUtils.fromHex("ED39FC00012FC990"));
		//System.out.println(NumberUtils.toHex(encodes));
		
		byte[] data = NumberUtils.fromHex("0200702004C020C09811196227001215060466907000000000000000000175440902100006376227001215060466907D4107520750102000003037303030303031343033333130303438313638303031313536CA8827463278E9A12600000000000000000822000001");
		String mainKey = SecrectUtil.descrypt3des("99016798100914A90502608BDB68A84D",
				"ED39FC00012FC990FE4D99CCEB815E01");
		String macKey = SecrectUtil.descrypt3des("1E18127DB9EAD1A0",
				mainKey);
		System.out.println("Main key:" + mainKey + " " + macKey);
		//byte[] needMac = new byte[data.length];
		//System.arraycopy(data, 13, needMac, 0, needMac.length);
		String mac = SecrectUtil.mac(data, macKey);
//		logger.info("MAC STRING:" + NumberUtils.toHex(needMac));
        System.out.println("MAC:" + mac);
		//byte[] macData = NumberUtils.fromHex(mac);
		
		//System.arraycopy(macData, 0, data, data.length - 8, 8);
		
		
	}
}

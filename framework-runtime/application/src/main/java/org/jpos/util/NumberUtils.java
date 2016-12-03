package org.jpos.util;

import java.util.Arrays;

/**
 * 
 * @author xiongliang
 *
 */
public class NumberUtils {

	// 16杩涘埗
	public static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private NumberUtils() {

	}

	public static byte toByte(int i) {
		return (byte) (i & 0xFF);
	}
	
	//add by zzy 2013_05_22 CUPS获取加密数据 补零
	public static byte[] getSecData(String input) {

		byte[] data = input.getBytes();
		int dlen = data.length;
		int len = (dlen / 8 + ((dlen % 8 == 0) ? 0 : 1)) * 8;
		byte[] plainData = new byte[len];

		Arrays.fill(plainData, (byte)0x00);
		System.arraycopy(data, 0, plainData, 0, dlen);
		
		return plainData;
	}
	
	//add for field43 space to 40 length begin
	public static final int strLength = 40;
	
	
	
	
	public static void main(String[] args) {
		
	}
	
	public static String SubStringByte(String targetStr){
	    
	    while(targetStr.getBytes().length>strLength)
	        targetStr = targetStr.substring(0,targetStr.length()-1);
	    return targetStr;
	}
	//add for field43 space to 40 length end
	
	public static byte[] toBytes(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static final int toInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	}

	public static byte[] low2Bytes(int i) {
		byte[] result = new byte[2];

		result[0] = (byte) ((i >> 8) & 0xFF);
		result[1] = (byte) (i & 0xFF);
		return result;
	}

	public static final byte[] fromHex(String b) {
		char[] data = b.toCharArray();
		int l = data.length;

		byte[] out = new byte[l >> 1];
		int i = 0;
		for (int j = 0; j < l;) {
			int f = Character.digit(data[(j++)], 16) << 4;
			f |= Character.digit(data[(j++)], 16);
			out[i] = (byte) (f & 0xFF);
			++i;
		}

		return out;
	}
	
	public static byte[] xor(byte[] data1, byte[] data2) {
		if(data1.length == data2.length) {
			for(int i = 0; i < data1.length; i++) {
				data1[i] ^= data2[i];
			}
		}
		return data1;
	}
	
	public static byte[] xor(String s1, String s2) {
		byte[] data1 = fromHex(s1);
		byte[] data2 = fromHex(s2);
		
		return xor(data1, data2);
		
	}

	public static final String toHex(byte[] b) {
		char[] buf = new char[b.length * 2];
		int j;
		int i = j = 0;
		for (; i < b.length; ++i) {
			int k = b[i];
			buf[(j++)] = hex[(k >>> 4 & 0xF)];
			buf[(j++)] = hex[(k & 0xF)];
		}
		return new String(buf);
	}
	
	public static String toAsc(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String asc = Integer.toHexString(ch);
			sb.append(asc);
		}
		return sb.toString().toUpperCase();
	}

	public static int bcdToInt(byte[] b) {
		String s = toHex(b);
		int result = Integer.parseInt(s);
		return result;
	}

	public static byte intToBcd(int i) {
		i = i % 100;
		return (byte) ((i / 10) << 4 | ((i % 10) & 0x0f));
	}
	
	public static byte[] intToBcds(int n) {
		byte[] data = new byte[16];
//		int m = n % 100;
//		n = n / 100;
		int i = 0;
		do {
			int m = n % 100;
			n = n / 100;
			data[i] = intToBcd(m);
			
			i++;
		} while (n > 0);
		
		byte[] result = new byte[i];
		for(int j = 0; j < i; j++) {
			result[j] = data[i - 1 - j];
		}
		return result;
	}

	final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
			99999999, 999999999, Integer.MAX_VALUE };

	static int sizeOfInt(int x) {
		for (int i = 0;; i++)
			if (x <= sizeTable[i])
				return i + 1;
	}
	
	public static int setBit(int i, int bitNum) {
		int c = (int)(0x80000000 >>> (bitNum - 1));
		i |= c;
		return i;
	}
	
	public static boolean getBit(int i, int bitNum) {
		i = i >>> (32 - bitNum);
		return (i & 0x01) > 0;
	}

}
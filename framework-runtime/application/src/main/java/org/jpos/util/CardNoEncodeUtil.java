/**
 * 
 *	平安付
 * Copyright (c) 2013-2014 PingAnFu,Inc.All Rights Reserved.
 */
package org.jpos.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author beark91
 * @version $Id: CardNoEncodeUtil.java, v 0.1 2014年4月9日 上午11:32:45 beark91 Exp $
 */
public class CardNoEncodeUtil {
    public static final byte[]  desKey       = "z%894$#@".getBytes();
  //定义 加密算法,可用 DES,DESede,Blowfish
    private static final String Algorithm = "DES";                
    
    public static String simpleEncodeCardNo(String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return "";
        }
       int length=cardNo.length();
       String result=cardNo.substring(0,6)+"******"+cardNo.substring(length-3,length);
        return result;
    }
    
    public static String encodeCardNo(String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return "";
        }
        byte[] encodeString = encrypt3des(desKey, cardNo.getBytes());
        String encodeCardNo = NumberUtils.toHex(encodeString);
        return encodeCardNo;
    }
    
    
    public static String threeDesEncode(String srcString) {
        return encodeCardNo(srcString);
    }
    
    public static String decodeCardNo(String encodeCardNo) {
        if (StringUtils.isBlank(encodeCardNo)) {
            LogUtil.bizerror("CardNoEncodeUtil.class", "encodeCardNo is null");
            return "";
        }
        byte[] srcBytes = decrypt3des(desKey, NumberUtils.fromHex(encodeCardNo));
        String decodeCardNo=new String(srcBytes);
        return decodeCardNo;
    }
    
  

    // 加密字符串
    public static byte[] encrypt3des(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
 // 解密字符串
    public static byte[] decrypt3des(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
   
    public static void main(String[] args) { // 添加新安全算法,如果用JCE就要把它添加进去
        String cardNo="62220222246565462262062220222246546546226201";
//        String encodeCardNo=CardNoEncodeUtil.simpleEncodeCardNo(cardNo) ;
        System.out.println("加密后的字符串:" + new String(cardNo));
//        System.out.println("加密前的字符串:" + cardNo);
        String encodeCardNo=CardNoEncodeUtil.encodeCardNo(cardNo) ;
        System.out.println("加密后的字符串:" + new String(encodeCardNo));
//        System.out.println("加密后的字符串长度:" + new String(encodeCardNo).length());
        String decodeCardNo=CardNoEncodeUtil.decodeCardNo(encodeCardNo) ;
        System.out.println("解密后的字符串:" +decodeCardNo);
    }
    
    
    
}

/**
 * 
 * Copyright (c) 2013-2014 framework,Inc.All Rights Reserved.
 */

/**
 * 
 * @author yyl
 * @version $Id: Ognl.java, v 0.1 2014-5-15 下午5:29:28 yyl Exp $
 */
public class Ognl {

    public static boolean startWith(String strOri, String str){
        return strOri.startsWith(str);
    }
    
    public static boolean endWith(String strOri, String str){
        return strOri.endsWith(str);
    }
    
    public static boolean contains(String strOri, String str){
        return strOri.contains(str);
    }
    
    public static boolean isString(String str){
        return str instanceof String;
    }
}

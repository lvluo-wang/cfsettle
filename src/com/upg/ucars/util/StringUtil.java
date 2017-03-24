package com.upg.ucars.util;

import java.net.URLDecoder;
import java.util.Vector;


/**
 * @Title: StringUtil.java
 * @Package com.upg.ucars.util
 * @Description: TODO(字符串工具类)
 * @author shentuwy 
 * 
 * @date 2011-1-11 下午06:36:48
 * @version V1.0
 */
public class StringUtil {
	
	
	/**
	 * 
	 * @Description: TODO(格式化Double格式字符串数据)
	 * @param sValue 0099.9900
	 * @return  String 99.99
	 */
	public static String trimDouble(String sValue) {
		if (sValue == null || sValue.length() == 0) {
			return "0";
		}

		int index = sValue.indexOf('.');
		if (index == -1) { // 没有小数点
			index = 0;
			while (index < sValue.length() && sValue.charAt(index) == '0') {
				index++;
			}

			if (index == sValue.length()) {
				return "0";
			} else {
				return sValue.substring(index);
			}
		} else {
			String left = sValue.substring(0, index);
			String right = sValue.substring(index + 1);

			// 小数点左边
			index = 0;
			while (index < left.length() && left.charAt(index) == '0') {
				index++;
			}

			if (index == left.length()) {
				left = "0";
			} else {
				left = left.substring(index);
			}

			// 小数点右边
			index = right.length();
			while (index > 0
					&& (right.charAt(index - 1) == '0' || right
							.charAt(index - 1) == '.')) {
				index--;
			}
			right = right.substring(0, index);

			if (right.length() == 0) {
				return left;
			} else {
				return left + "." + right;
			}
		}
	}
	/**
	 * 
	 * @Description: TODO(判断字符串是否为空)
	 * @param s null or ""
	 * @return  boolean true
	 */
	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}

		if (s.length() == 0) {
			return true;
		}

		return false;
	}
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	public static String firstUpper(String s){
		if (s==null)
			return null;
		String f = s.substring(0, 1).toUpperCase();
		return f + s.substring(1);		
	}
	/**
	 * 首字母小写
	 *
	 * @param s
	 * @return
	 */
	public static String firstLower(String s){
		if (s==null)
			return null;
		String f = s.substring(0, 1).toLowerCase();
		return f + s.substring(1);		
	}
	
	/**
	 * 转换成java属性格式字符。首字母小写，下划线后第一个字母大写并去掉下划线，其余转为小写
	 * 如:输入table_column，返回tableColumn
	 * @param column
	 * @return
	 */
	public static String toJavaString(String table_column){
		StringBuffer sbf = new StringBuffer();
		char[] charArr = table_column.toLowerCase().toCharArray();
		for(int i = 0;i<charArr.length;i++){
			if(charArr[i] == '_'){
				if( (i < charArr.length - 1) && (charArr[i + 1] !='_')){
					charArr[i+1] = Character.toUpperCase(charArr[i+1]);
				}
			}else{
				sbf.append(charArr[i]);
			}
		}
		String str = sbf.toString();
		if(str.equalsIgnoreCase("")){
			str = table_column;
		}
		str = str.substring(0,1).toLowerCase() + str.substring(1);
		return  str;
	}
	
	/**
	 * 在非首大写字符前增加下划线。
	 * 如：输入tableColumn,返回 table_Column
	 * @return
	 */
	public static String addUnderlineBeforeUpper(String str){
		if(str == null){
			return str;
		}
		char[] cArr = str.toCharArray();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(cArr[0]);
		for(int i = 1; i < cArr.length; i++){
			if(Character.isUpperCase(cArr[i])){
				strBuf.append('_');
			}
			strBuf.append(cArr[i]);
		}
		return strBuf.toString();
	}
	
	/**
     * 将字符串按给定分割符分割，返回字符串数组
     * @param str
     * @param sgn
     * @return
     */
    public static final String[] split(String str, String sgn){
       String[] returnValue = null;
       if (str != null){
         Vector vectors = new Vector();
         int i = str.indexOf(sgn);
         String tempStr = "";
         for(; i >= 0; i = str.indexOf(sgn)){
           tempStr = str.substring(0, i);
           str = str.substring(i + sgn.length());
           vectors.addElement(tempStr);
         }
         if (!str.equalsIgnoreCase(""))
           vectors.addElement(str);
         returnValue = new String[vectors.size()];
         for(i = 0; i < vectors.size(); i++){
           returnValue[i] = (String) vectors.get(i);
           returnValue[i] = returnValue[i].trim();
         }
       }
       return returnValue;
     }
    
	/**
     * 查找某一字符串中str，特定子串s的出现次数
     * @param str String
     * @param sign String
     * @return int
     */
    public static int getHasCount(String str,String sign)
    {
      int iret=0;
      int signLen = sign.length();
      int strLen = str.length();
      String temp = str;
      if(strLen==0||strLen<signLen)
        iret = 0;
      else{
        for(int i=0;i<=strLen-signLen&&str.length()>=signLen;i++)
        {
          temp = str.substring(0,signLen);
          if(sign.equals(temp))
          {
            str = str.substring(signLen);
            iret++;
          }
          else
          {
              str = str.substring(1);
          }
        }
      }
      return iret;
    }
    
    /**
     * 将字符串中的 子字符串 替换为新 字符串  (不区分大小写)
     * @param string  被操作字符串        "abcedfg"
     * @param oldString  被替换子字符串    "Ce"
     * @param newString  新字符串          "11"
     * @return  替换后字符串              "ab11dfg"
     */

    public static final String replaceIgnoreCase(String line, String oldString, String newString)
     {
            if(line == null)
                return null;
            String lcLine = line.toLowerCase();
            String lcOldString = oldString.toLowerCase();
            int i = 0;
            if((i = lcLine.indexOf(lcOldString, i)) >= 0)
            {
                char line2[] = line.toCharArray();
                char newString2[] = newString.toCharArray();
                int oLength = oldString.length();
                StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);
                i += oLength;
                int j;
                for(j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i)
                {
                    buf.append(line2, j, i - j).append(newString2);
                    i += oLength;
                }

                buf.append(line2, j, line2.length - j);
                return buf.toString();
            } else
            {
                return line;
            }
     }
    
    
    /**
     *将字符串补足为指定长度 ，在前面加'0'
     * @param string "23433"  指定的字符串
     * @param length 8        转换后的长度
     * @return       "00023433"
     */
    public static final String zeroPadString(String string, int length)
    {
            if(string == null || string.length() > length)
            {
                return string;
            } else
            {
                StringBuffer buf = new StringBuffer(length);
                for(int i=0;i<length - string.length();i++)
                {
                	buf.append("0");
                }
                buf.append(string);
                //buf.append(zeroArray, 0, length - string.length()).append(string);
                return buf.toString();
            }
    }
	/**
	*将字符串补足为指定长度 ，在后面加'0'
	* @param string "23433"  指定的字符串
	* @param length 8        转换后的长度
	* @return       "23433000"
	*/
   public static final String zeroAftString(String string, int length)
   {
		   if(string == null || string.length() > length)
		   {
			   return string;
		   } else
		   {
			   StringBuffer buf = new StringBuffer(length);
			   buf.append(string);
			   for(int i=0;i<length - string.length();i++)
               {
               	buf.append("0");
               }
			   
			   //buf.append(string).append(zeroArray, 0, length - string.length());
			   return buf.toString();
		   }
   }
   
   /**
    * 将String型数组转换为Long型数组
    * @param str 转换前的String型数组
    * @return 转换后的Long型数组
    */
   public static final Long[] parseStrToLong(String[] str){
	   Long[] lon = null;
	   if(null != str && str.length > 0){
		   lon = new Long[str.length];
		   for(int i = 0; i < lon.length; i++){
			   lon[i] = Long.parseLong(str[i]);
		   }
	   	}
	   
	   return lon;
   }
   
   public static final String convertHTML(String content){
	   String ret = null;
	   if (content != null) {
		   ret = content.replaceAll("\\r\\n", "<br/>").replaceAll("\\r", "<br/>").replaceAll("\\n", "<br/>");
	   }
	   return ret;
   }
   
   public static final String decode(String str){
	   String result = str;
	   if (str != null) {
		   try{
			   result = URLDecoder.decode(str,"UTF-8");
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
	   }
	   return result;
   }
   
   
    public static void main(String[] args)
    {
    	String s = "select * ${param1} a from${date} ";
    	System.out.println(s.replace("${date}", "123").replace("${param1}", "alw"));
    	
    	System.out.println(s.substring(4,6));
    }
}

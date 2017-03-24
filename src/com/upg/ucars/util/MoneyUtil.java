package com.upg.ucars.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额相关工具
 * 
 * @author 
 */
public class MoneyUtil {
	private static String _pattern = "###,###,###,###.##";
	/**
     * 格式化
     * @param d
     * @return '##,###.00'
     */
	public static String format(double d){
        DecimalFormat df = new DecimalFormat(_pattern);
        String str = df.format(d);
//        if(str.equals(".00")){
//           str="0";
//        }
        return str;
    }
	
	public static String format(BigDecimal d){
        DecimalFormat df = new DecimalFormat(_pattern);
        String str = df.format(d);
//        if(str.equals(".00")){
//           str="0";
//        }
        return str;
    }
	
	public static final String normalFormat(Double d){
		String ret = null;
		if (d != null) {
			DecimalFormat df = new DecimalFormat("#.##");
			ret = df.format(d);
		}
		return ret;
	}
	
	
	//----------------------------
	private final static String[] bigUnit = { "元", "拾", "佰", "仟", "万", "拾", "佰",
			"仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟" };
	private final static String[] smallUnit = { "分", "角" };
	private final static String bigLetter[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆",
			"柒", "捌", "玖" };
	/**
	 * 金额转换成大写中文
	 * @param lzfmoney
	 * @return
	 */
	public static String transToUpperChinese(Double money) {

		//先格式化
		DecimalFormat decimalForamt = new DecimalFormat("#.##");
		String strMoney = decimalForamt.format(money);
		
		String[] sArr = String.valueOf(strMoney).split("\\.");

		String integerPart = sArr[0];
		String decimalPart = "";
		if (sArr.length == 2) {
			if (sArr[1].length() == 1) {
				decimalPart = sArr[1].concat("0");
			} else {
				decimalPart = sArr[1];
			}
		} else {
			decimalPart = "00";
		}
		return transInt(integerPart) + transSmall(decimalPart);
	}

	// 处理整数部分
	private static String transInt(String integerPart) {
		if (integerPart.equals("0")) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		char[] cArr = integerPart.toCharArray();
		for (int i = 0; i < cArr.length; i++) {
			if (cArr[i] != '0') {
				buffer.append(bigLetter[cArr[i] - 48]);
				buffer.append(bigUnit[cArr.length - i - 1]);
				System.out.println(buffer.toString());
			} else { 
				if (cArr[i] == '0' && cArr[i - 1] != '0' && i != cArr.length - 1) {
					buffer.append(bigLetter[cArr[i] - 48]);
				}
				
				int unitIndex = cArr.length - i - 1;
								
				if (unitIndex % 4 == 0) {
					if (buffer.length() > 0 && bigLetter[0].equals(""+buffer.charAt(buffer.length()-1))) {
						buffer.deleteCharAt(buffer.length()-1);
					}
					buffer.append(bigUnit[unitIndex]);
				}
			}
		}
		
		return buffer.toString();
	}

	// 处理小数部分
	private static String transSmall(String decimalPart) {
		if (decimalPart.equals("00")) {
			return "整";
		}
		StringBuffer buffer = new StringBuffer();
		char[] cArr = decimalPart.toCharArray();
		if (cArr[0] == '0' && cArr[1] != '0') {
			buffer.append(bigLetter[0]);
			buffer.append(bigLetter[cArr[1] - 48]);
			buffer.append(smallUnit[0]);
		} else if (cArr[0] != '0' && cArr[1] == '0') {
			buffer.append(bigLetter[cArr[0] - 48]);
			buffer.append(smallUnit[1]);
		} else if (cArr[0] != '0' && cArr[1] != '0') {
			buffer.append(bigLetter[cArr[0] - 48]);
			buffer.append(smallUnit[1]);
			buffer.append(bigLetter[cArr[1] - 48]);
			buffer.append(smallUnit[0]);
		}
		return buffer.toString();
	}
	//------------------------------------------------
	
}
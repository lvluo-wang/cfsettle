package com.upg.ucars.basesystem.security.core.branch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.mapping.basesystem.security.Branch;

public class BranchHelper {
	
	/** 机构树形编码级别的位数  */
	private static final int TREE_CODE_LEVEL_LENGTH = 4;
	
	/**
	 * 格式化机构树型编码
	 * @param no	自动生成序列
	 * @return	机构树型编码
	 */
	public static String formatIntTo4LengthStr(int no){
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(no);
	}
	/**
	 * 获取上级树型码
	 * @param treeCode	机构树型码
	 * @return 上级机构树型码
	 */
	public static String getParentTreeCode(String treeCode) {
		treeCode = StringUtils.trimToEmpty(treeCode);
		if(treeCode.length() >= 4) {
			return treeCode.substring(0, treeCode.length() - 4);
		} else {
			return "";
		}
		
	}
	/**
	 * 生成机构树形编码
	 * @param branchs	同级机构列表
	 * @return	机构树形编码
	 */
	public static synchronized String createTreeCode(List<Branch> branchs){
		int minCode = 0, maxCode = 0, curCode;
		String tc = "";
		List codeArr = new ArrayList();
		for(Branch brch : branchs) {
			tc = brch.getTreeCode();
			curCode = Integer.valueOf(tc.substring(tc.length() - 4, tc.length()));
			codeArr.add(curCode);
			if(curCode < minCode) minCode = curCode;
			if(curCode > maxCode) maxCode = curCode;			
		}
		for(;minCode < maxCode; minCode++) {
			if(minCode != 0 && !codeArr.contains(minCode)) {
				break;
			}
		}
		if(minCode == maxCode) {
			minCode++;
		}
		return formatIntTo4LengthStr(minCode);
	}
	/**
	 * 生成机构树形编码
	 * @param branchs	同级机构列表
	 * @param exTC		需要排除的树型码
	 * @return	机构树形编码
	 */
	public static synchronized String createTreeCode(String parentTC, List<Branch> brchs, List<String> exTC) {
		int minCode = 0, maxCode = 0, curCode;
		String tc = "";
		List codeArr = new ArrayList();
		for(Branch brch : brchs) {
			tc = brch.getTreeCode();
			curCode = Integer.valueOf(tc.substring(tc.length() - 4, tc.length()));
			codeArr.add(curCode);
			if(curCode < minCode) minCode = curCode;
			if(curCode > maxCode) maxCode = curCode;			
		}
		for(;minCode < maxCode; minCode++) {
			if(minCode != 0 && !codeArr.contains(minCode) && exTC.contains(parentTC + formatIntTo4LengthStr(minCode))) {
				break;
			}
		}
		if(minCode == maxCode) {
			minCode++;
			while(exTC.contains(parentTC + formatIntTo4LengthStr(minCode))){
				minCode++;
			}
		}
		return formatIntTo4LengthStr(minCode);
		
	}
	
	/**
	 * 
	 * 获取所有父机构树形编码的列表(包括本身)
	 *
	 * @param treeCode
	 * @return
	 */
	public static final List<String> getAllPreTreeCode(String treeCode){
		List<String> ret = new ArrayList<String>();
		if( StringUtils.isNotBlank(treeCode) ){
			String trimTreeCode = treeCode.trim();
			int length = trimTreeCode.length();
			int size = length / TREE_CODE_LEVEL_LENGTH;
			if( size != length ){
				//机构的树形编码有问题
			}
			for( int i = 0; i < size; i ++ ){
				int end = ( i + 1 ) * TREE_CODE_LEVEL_LENGTH;
				ret.add(trimTreeCode.substring(0,end));
			}
		}
		return ret;
	}
	
	public static void main(String[] args){
		String str = "0001000200030001";
		System.out.println(getAllPreTreeCode(str));
	}
	

}

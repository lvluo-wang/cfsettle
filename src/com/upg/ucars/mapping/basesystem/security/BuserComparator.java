/*
 * 源程序名称: BuserComparator.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.mapping.basesystem.security;

import java.util.Comparator;


public class BuserComparator implements Comparator<Buser> {

	public int compare(Buser o1, Buser o2) {
		int flag=o1.getUserNo().compareTo(o2.getUserNo());
		if(flag==0){
			return o1.getUserName().compareTo(o2.getUserName());
		}
		return flag;
	}

}

/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 4, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.analyse;

import java.util.HashMap;
import java.util.Map;

import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
/**
 * 
 * 
 * @author shentuwy
 */
public class AcctItemManager {
	
	private static Map map = new HashMap();
	
	private static void init(){
		/**/
	}

	private AcctItem[] getAcctItem(AcctRecordInfo record){
		
		return null;
		
	}
	

	private static String getItemName(String itemNo){
		init();
		return map.get(itemNo).toString();
	}
	
	
	
	

}

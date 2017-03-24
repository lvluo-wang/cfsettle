package com.upg.ucars.framework.base;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Locales {
	public Map<String,Locale> getLocales(){
		Map<String,Locale> locales=new HashMap<String,Locale>();
		locales.put("English", Locale.US);
		locales.put("中文", Locale.CHINA);
		return locales;
	}
}

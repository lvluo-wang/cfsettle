package com.upg.ucars.framework.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.security.UserLogonInfo;
/**
 * 
 * 功能说明：TODO(根据用户当前会话中的主题跳转到相应的页面)
 * @author shentuwy  
 * @date 2012-1-30 上午10:31:06 
 *
 */
public class ThemesViewPreparer implements ViewPreparer {

	public void execute(TilesRequestContext tilesContext, AttributeContext attrContext) {
		Attribute oldTemplate=attrContext.getTemplateAttribute();
		String oldValue=oldTemplate.getValue().toString();
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		if(logonInfo!=null){
			String theme=logonInfo.getTheme();
			if(StringUtils.isBlank(theme)){
				theme="default";
			}
			String themeTemplate=changeThemes(oldValue,theme);
			attrContext.setTemplateAttribute(new Attribute(themeTemplate));
		}
	}
	private  String changeThemes(String oldValue,String newThemes){
		if(!StringUtils.isBlank(oldValue)){
			int lastPathIndex=oldValue.lastIndexOf("/");
			int themesStartIndex=oldValue.indexOf("/themes/");
			if(lastPathIndex<0||themesStartIndex<0){
				return oldValue;
			}
			int themesEndIndex=themesStartIndex+8;
			String endText=oldValue.substring(lastPathIndex);
			String startText=oldValue.substring(0, themesEndIndex);
			return startText+newThemes+endText;
		}
		return oldValue;
	}
}

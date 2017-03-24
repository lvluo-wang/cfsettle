package com.upg.ucars.tools.msg;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ConfigException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.tools.msg.cfg.AbstractConfig;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenConfig;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenField;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenItem;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenItems;
import com.upg.ucars.tools.msg.cfg.fixlen.FixLenList;
import com.upg.ucars.tools.msg.cfg.fixlen.Padding;
import com.upg.ucars.util.JAXBUtil;


public class FixLenConfigParser implements ConfigParser {

	public AbstractConfig parse(String cfgPath) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(cfgPath);
		FixLenConfig config = null;
		try {
			config = JAXBUtil.unmarshall(is, new Class[]{FixLenItem.class,FixLenItems.class,FixLenField.class,FixLenConfig.class,FixLenList.class,Padding.class});
		} catch (JAXBException e) {
			ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,"file["+cfgPath+"] config error!");
		}
		return config;
	}

	public static void main(String[] args) {
		new FixLenConfigParser().parse("msg-transform-config/demo/spCfg.xml");
	}
}

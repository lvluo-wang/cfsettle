package com.upg.ucars.tools.msg;

import com.upg.ucars.tools.msg.cfg.AbstractConfig;

public interface ConfigParser {
	public AbstractConfig parse(String cfgPath);
}

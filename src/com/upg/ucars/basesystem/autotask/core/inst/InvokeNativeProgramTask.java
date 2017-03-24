
package com.upg.ucars.basesystem.autotask.core.inst;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.logging.LogFactory;

import com.upg.ucars.basesystem.autotask.core.AbstractCommonAutoTask;
import com.upg.ucars.model.BooleanResult;

/**
 * 
 * 调用本地程序的任务
 * 
 * @author shentuwy
 */
public class InvokeNativeProgramTask extends AbstractCommonAutoTask {

	private int rate;

	public int getRateOfProgress() {
		return rate;
	}


	public BooleanResult run() throws Exception {
		String cmdStr =this.getTaskPara();
		if (cmdStr == null || cmdStr.equals("")) {
			LogFactory.getLog(InvokeNativeProgramTask.class).error(
					"!!! command is null");
			return new BooleanResult(false, "命令执行失败：" + "command is null");
		}
		LogFactory.getLog(InvokeNativeProgramTask.class).error(
				"\ncmd is " + cmdStr + "\n");
		BufferedInputStream inStr = null;
		StringBuffer buffer = new StringBuffer();
		try {
			Process ps = Runtime.getRuntime().exec(cmdStr);
			inStr = new BufferedInputStream(ps.getErrorStream());
			int ptr = 0;
			while ((ptr = inStr.read()) != -1) {
				buffer.append((char) ptr);
			}
			int result = ps.waitFor();
			if (result == 0) {
				return new BooleanResult(true, "命令执行成功：" + cmdStr + "\n");
			} else {
				return new BooleanResult(false, "命令执行失败：" + cmdStr + "\n");
			}
		} catch (IOException e) {
			LogFactory.getLog(InvokeNativeProgramTask.class).error(e);
			return new BooleanResult(false, "异常：IOException: " + cmdStr + "\n"
					+ e.getMessage() + "\n" + buffer.toString());
		}
		// this.getTaskPara() 执行的结果是从页面获得了命令输入，例如 ：ls 或 echo。cmds数组就第一个元素有用。
		catch (InterruptedException e) {
			LogFactory.getLog(InvokeNativeProgramTask.class).error(e);
			return new BooleanResult(false, "异常：InterruptedException: "
					+ cmdStr + "\n" + e.getMessage() + "\n" + buffer.toString());
		}finally{
			if (inStr !=null){
				try {
					inStr.close();
				} catch (IOException e) {
					LogFactory.getLog(InvokeNativeProgramTask.class).error(e);
				}
			}
		}

	}

}

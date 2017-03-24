/*
 * 源程序名称: ReportAction.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.demo.report.jasper.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;

public class ReportAction extends BaseAction {
	private List<Buser> userList;
	private Map<String,Object> map;
	private String reportPath="";
	public List<Buser> getUserList() {
		return userList;
	}
	public void setUserList(List<Buser> userList) {
		this.userList = userList;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String execute(){
		
		userList=getData();
		map=getParameter();
		try{
			String reportSource;
			reportSource=getSc().getRealPath("/jasper/user.jrxml");
			File parent=new File(reportSource).getParentFile();
			JasperCompileManager.compileReportToFile(reportSource,new File(parent,"compiled_user.jasper").getAbsolutePath());
		}catch(Exception e){
			
		}
		return "success";
	}
	private List<Buser> getData(){
		int id=1;
		int id2=1;
		List<Buser> bList=new ArrayList<Buser>();
		for(int i=1;i<300;i++){
			String mi_no=id+"";
			Buser u=new Buser();
			u.setUserNo(i+"");
			u.setUserId(Long.valueOf(i));
			u.setUserName("user"+i);
			u.setMiNo(mi_no);
			u.setUserType(id2+"");
			id2++;
			
			if(i%5==0){
				id++;
				id2=1;
			}
			bList.add(u);
		}
		return bList;
	}
	private Map<String,Object> getParameter(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("date", "2011-07-13");
		return map;
	}
}

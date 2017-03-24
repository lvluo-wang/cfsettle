package com.upg.ucars.model.security;

public class UISelectMap implements java.io.Serializable{
	private String vuDes;//中文描述
	private double doubleVu;//小数型
	private int intVu;//整数型
	private String strVu;//字符型
	
	private int vuType;//1: double ; 2:int  ; 3:string
	
	
	public String getVu(){
		if(vuType==1)
			return getDoubleVu()+"";
		else if(vuType==2) 
			return getIntVu()+"";
		else 
			return getStrVu();
	}
	
	public String getStrVu() {
		return strVu;
	}

	public void setStrVu(String strVu) {
		this.strVu = strVu;
	}

	public UISelectMap(double vu,String vuDes){
		vuType=1;
		this.vuDes=vuDes;
		this.doubleVu=vu;
	
	}
	public UISelectMap(int vu,String vuDes){
		vuType=2;
		this.vuDes=vuDes;
		
		this.intVu=vu;
	}
	public UISelectMap(String vu,String vuDes){
		vuType=3;
		this.vuDes=vuDes;
		this.strVu=vu;
	}

	public String getVuDes() {
		return vuDes;
	}

	public void setVuDes(String vuDes) {
		this.vuDes = vuDes;
	}

	public double getDoubleVu() {
		return doubleVu;
	}

	public void setDoubleVu(double doubleVu) {
		this.doubleVu = doubleVu;
	}

	public int getIntVu() {
		return intVu;
	}

	public void setIntVu(int intVu) {
		this.intVu = intVu;
	}
	
	
	
	
	
	

}

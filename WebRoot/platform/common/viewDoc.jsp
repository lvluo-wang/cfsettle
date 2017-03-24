<%@page import="java.net.URLDecoder"%>
<%@page import="com.upg.ucars.framework.base.SessionTool"%>
<%@page import="org.apache.struts2.components.Else"%>
<%@ page language="java" contentType="text/html; charset=utf-8" session="false" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@page import="java.io.*,java.util.*,java.lang.String,com.upg.xfosc.amt.contract.utils.*"%>
<%
	String extName = "";
	String uploadPath = "";
	String uploadDir = "";
	String uploadParam = "";
	Properties prop = new Properties();
	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("upload.properties");

	try {
		prop.load(in);
		uploadPath = prop.getProperty("uploadPath").trim();
		uploadDir = prop.getProperty("uploadDir").trim()+java.io.File.separator;
		uploadParam = prop.getProperty("uploadParam").trim();
	} catch (IOException e) {
		e.printStackTrace();
		out.println("读取属性文件失败！");
	}
	String doc_path = "";
	String viewFile = "";
	String filenamedownload = "";
	
	//viewFile = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "UTF-8");
	viewFile = request.getParameter("filePath");
	if ("1".equalsIgnoreCase(uploadParam)) {
		doc_path = request.getSession().getServletContext().getRealPath(uploadPath);
		filenamedownload = doc_path + viewFile;
	} else if ("2".equalsIgnoreCase(uploadParam)) {
		filenamedownload = uploadDir + uploadPath + viewFile;
		filenamedownload = filenamedownload.replaceAll("/", "\\\\");
		// request.setAttribute("docPath", filenamedownload);
	}

	try {
		if (viewFile.lastIndexOf(".") >= 0) {
			extName = viewFile.substring(viewFile.lastIndexOf("."));
		}else{
			out.println("没有文件可显示！");
%>
			<script type="text/javascript">alert("不是正确的文件！");</script>
<%
			return;
		}
		
		//if(java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(filenamedownload)==true){
		//	filenamedownload = new String(filenamedownload.getBytes("ISO-8859-1"), "GBK");
		//}else if(java.nio.charset.Charset.forName("UTF-8").newEncoder().canEncode(filenamedownload)==true){
		//	filenamedownload = new String(filenamedownload.getBytes("ISO-8859-1"), "UTF-8");
		//}else if(java.nio.charset.Charset.forName("GB2312").newEncoder().canEncode(filenamedownload)==true){
		//	filenamedownload = new String(filenamedownload.getBytes("ISO-8859-1"), "GB2312");
		//}
		OutputStream os = null;
		if (".pdf".equalsIgnoreCase(extName)) {
			response.setContentType("application/pdf");
			out.clearBuffer();
			os = response.getOutputStream(); // 页面输出流
			response.addHeader("Content-Disposition","filename="+ filenamedownload); // 针对中文文件名 
		} else if (".doc".equalsIgnoreCase(extName)
				|| ".docx".equalsIgnoreCase(extName)) {
			response.setContentType("application/msword");
			out.clearBuffer();
			os = response.getOutputStream(); // 页面输出流
			response.addHeader("Content-Disposition",
					"inline; filename=" + filenamedownload);
		} else {
			out.println("<center>文件输出出错!不是有效的文件扩展名！</center>" + extName);
			return;
		}

		File f = new File(filenamedownload); // 你的文件 
		InputStream is = new FileInputStream(f); // 文件输入流 
		byte[] bs = new byte[1024]; // 读取缓冲区 
		int len;
		while ((len = is.read(bs)) != -1) { // 循环读取 
			os.write(bs, 0, len); // 写入到输出流 
		}
		is.close(); // 关闭 
		os.close(); // 关闭
		out.clear();
		out = pageContext.pushBody();
	} catch (Exception e) {

		out.clear();
		out = pageContext.pushBody();
	}
%>
package com.upg.ucars.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 功能说明：JAXB序列化工具类
 * @author shentuwy  
 * @date 2011-8-25 下午6:50:17 
 * */
public class JAXBUtil {

	public static <T> T unmarshall(String xml, Class... bindClass) throws JAXBException {
		T ret = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JAXBContext jaxbContext = JAXBContext.newInstance(bindClass);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ret = (T)unmarshaller.unmarshal(bais);
		return ret;
	}
	
	public static <T> T unmarshallWithFile(String fixLenFilePath, Class... bindClass) throws JAXBException {
		T ret = null;
		File file = new File(fixLenFilePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(bindClass);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ret = (T)unmarshaller.unmarshal(file);
		return ret;
	}
	
	public static <T> T unmarshall(InputStream is, Class... bindClass) throws JAXBException {
		T ret = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(bindClass);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ret = (T)unmarshaller.unmarshal(is);
		return ret;
	}

	public static String marshall(Object obj, Class... bindClass) throws JAXBException {
		String xml = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(bindClass);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(obj, os);
		try {
			xml = os.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("-- after marshall,result is --");
		System.out.println(xml);
		return xml;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

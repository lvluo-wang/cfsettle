package com.upg.ucars.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加解密工具类(单钥密码体制)
 * 
 * @author shentuwy
 */
public class DESKeyUtil {
	
	/**
	 * 解密 
	 *
	 * @param file 密钥文件 如：src/keydb.dat,则参数file="keydb.dat"
	 * @param hidepw
	 * @return 
	 */
	public static String dec(String file, String hidepw) {
		String[] bs = hidepw.split(",");
		byte[] ctext = new byte[bs.length];
		for (int i = 0; i < bs.length; i++) {
			ctext[i] = new Byte(bs[i]);
		}
		InputStream f2 = null;
		try {
			// 获取密钥
			
			f2 = DESKeyUtil.class.getClassLoader().getResourceAsStream(file);
			int len = f2.available();
			byte[] keyByte = new byte[len];
			f2.read(keyByte);

			SecretKeySpec k = new SecretKeySpec(keyByte, "DESede");

			// 解密
			Cipher cp = Cipher.getInstance("DESede");
			cp.init(Cipher.DECRYPT_MODE, k);
			byte[] ptext = cp.doFinal(ctext);

			String s = new String(ptext, "UTF-8");
			return s;
		} catch (Exception e) {
			throw new RuntimeException("password is no valid.",e);
		}finally{
			if (f2!=null){
				try {
					f2.close();
				} catch (IOException e) {
					//no deal
				}
			}
		}

		
	}
	/**************************************
	 * AES算法 开始
	 * 
	 * 可以按指定的字符串密钥进行加密解密
	 * 
	 * *************************************
	 */
	
	private static Key initKeyForAES(String key) throws NoSuchAlgorithmException {   
        if (null == key || key.length() == 0) {  
        	key = "hundsun";//默认的密钥
           //throw new NullPointerException("key not is null");   
        }   
        SecretKeySpec key2 = null;   
        try {   
            KeyGenerator kgen = KeyGenerator.getInstance("AES");   
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(key.getBytes());
            kgen.init(128, sr);   
            SecretKey secretKey = kgen.generateKey();   
            byte[] enCodeFormat = secretKey.getEncoded();   
            key2 = new SecretKeySpec(enCodeFormat, "AES");   
        } catch (NoSuchAlgorithmException ex) {   
            throw new NoSuchAlgorithmException();   
        }   
        return key2;   
  
    }   
  
    /**  
     * AES加密算法，不受密钥长度限制  
     * @param content  
     * @param key   如果key==null,则用默认密钥解密
     * @return  
     */  
    public static String EncryptAES(String content, String key){   
        try{   
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);   
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
            byte[] byteContent = content.getBytes("utf-8");   
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化   
            byte[] result = cipher.doFinal(byteContent);   
            return asHex(result); // 加密   
        }   
        catch (Exception e){   
            e.printStackTrace();   
        }   
        return null;   
    }   
  
    /**  
     * aes解密算法，不受密钥长度限制  
     * @param content  
     * @param key  如果key==null,则用默认密钥解密
     * @return  
     */  
    public static String DecryptAES(String content, String key){   
        try{   
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);   
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
            cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化   
            byte[] result = cipher.doFinal(asBytes(content));   
            return new String(result); // 加密   
        }   
        catch (Exception e){   
            e.printStackTrace();   
        }   
        return null;   
    }   
  
    /**  
     * 将2进制数值转换为16进制字符串  
     * @param buf  
     * @return  
     */  
    private static String asHex(byte buf[]){   
        StringBuffer strbuf = new StringBuffer(buf.length * 2);   
        int i;   
        for (i = 0; i < buf.length; i++){   
            if (((int) buf[i] & 0xff) < 0x10)   
                strbuf.append("0");   
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));   
        }   
        return strbuf.toString();   
    }   
       
       
    /**  
     * 将16进制转换  
     * @param hexStr  
     * @return  
     */  
    private static byte[] asBytes(String hexStr) {   
        if (hexStr.length() < 1)   
            return null;   
        byte[] result = new byte[hexStr.length() / 2];   
        for (int i = 0; i < hexStr.length() / 2; i++){   
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);   
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),16);   
            result[i] = (byte) (high * 16 + low);   
        }   
        return result;   
    }   
    /**************************************
	 * AES算法
	 * end
	 * *************************************
	 */
  
}

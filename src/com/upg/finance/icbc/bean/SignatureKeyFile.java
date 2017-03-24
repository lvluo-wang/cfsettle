package com.upg.finance.icbc.bean;

import java.io.File;
import java.net.URL;

public class SignatureKeyFile {
	private static File	privateKeyFile	= null;

	private static File	publicKeyFile	= null;

	public static final File getPublicKeyFile() {
		if (publicKeyFile == null) {
			publicKeyFile = getFileByName("public.key");
		}
		return publicKeyFile;
	}

	private static final File getFileByName(String fileName) {
		File result = null;
		URL url = BasePackage.class.getResource(fileName);
		if (url != null) {
			String fileString = url.toString();
			int startIndex = fileString.indexOf(":/");
			if (startIndex > -1) {
				fileString = fileString.substring(startIndex + 2);
			}
			result = new File(fileString);
		}
		return result;
	}

	public static final File getPrivateKeyFile() {
		if (privateKeyFile == null) {
			privateKeyFile = getFileByName("private.key");
		}
		return privateKeyFile;
	}
}

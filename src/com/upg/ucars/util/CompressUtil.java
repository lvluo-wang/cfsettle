package com.upg.ucars.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class CompressUtil {

	private static final Logger	LOG			= Logger.getLogger(CompressUtil.class);

	private static final int	BUFFER_SIZE	= 1024;

	/**
	 * 
	 * 
	 * @param sources
	 *            需要打包的文件列表
	 * @param names
	 *            打包的文件列表新名
	 * @param dest
	 * @return
	 */
	public static final String compress(List<String> sources, List<String> names, String dest) {
		String result = null;
		String destName = dest;
		File destFile = new File(destName);
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(destFile));
			int i = 0;
			for (String source : sources) {
				File sourceFile = new File(source);
				FileInputStream fis = null;
				try {
					ZipEntry ze = new ZipEntry(names.get(i));
					zos.putNextEntry(ze);
					if (!sourceFile.exists()) {
						LOG.error("[" + source + "] file is not exist.");
						continue;
					}
					fis = new FileInputStream(sourceFile);
					int len = 0;
					byte[] buf = new byte[BUFFER_SIZE];
					while ((len = fis.read(buf)) > 0) {
						zos.write(buf, 0, len);
					}
					zos.closeEntry();
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
				i++;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex.getMessage(), ex);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (Exception ex) {
					LOG.error(ex.getMessage(),ex);
				}
			}
		}
		return result;
	}

}

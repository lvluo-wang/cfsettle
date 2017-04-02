package com.upg.ucars.basesystem.component.core;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.other.Attachment;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.CompressUtil;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.PropertyTransVo;
import com.upg.ucars.util.XmlUtil;

/**
 * 附件服务实现
 * 
 * @author shentuwy
 * @date 2012-6-30
 * 
 */
@Service
public class AttachmentServiceImpl extends BaseService implements IAttachmentService {

	private static Logger			log						= LoggerFactory.getLogger(AttachmentServiceImpl.class);
	private static String			attachmentFilePath		= null;

	private static String			compressTempDir			= null;
	
	private static Set<String>		PICTURE_NAME_EXT		= new HashSet<String>();
	
	// private static final DateFormat DATE_FORMAT = new
	// SimpleDateFormat("yyyyMMdd");

	private static final DateFormat	DATE_YEAR_FORMAT		= new SimpleDateFormat("yyyy");
	private static final DateFormat	DATE_MONTH_DAY_FORMAT	= new SimpleDateFormat("MMdd");

	@Autowired
	private IAttachmentDao	attachmentDao;

	static {
		init();
	}

	private static final void init() {

		try {
			
			PICTURE_NAME_EXT.add("gif");
			PICTURE_NAME_EXT.add("jpg");
			PICTURE_NAME_EXT.add("png");
			PICTURE_NAME_EXT.add("jpeg");
			PICTURE_NAME_EXT.add("bmp");
			
			Document doc = XmlUtil.parseXmlDoc("attachment-config.xml");
			attachmentFilePath = doc.getRootElement().getChild("filePath").getValue();
			compressTempDir = doc.getRootElement().getChild("temp").getValue();
			checkFilePath(attachmentFilePath);
			checkFilePath(compressTempDir);
			log.info("attachment file path:" + attachmentFilePath + ",compressTempDir:" + compressTempDir);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static final void checkFilePath(String str) {
		File file = new File(str);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				throw new RuntimeException(str + " can't created");
			}
		}
		if (file.isFile()) {
			throw new RuntimeException(str + " is not file,need to direction");
		}
		if (!file.canWrite()) {
			throw new RuntimeException(str + " can't be writed");
		}
	}

	public Attachment save(File file, String fileName, boolean imgServer, String nowater, String fileTypeExt) {
		Attachment ret = save(file, fileName);
		return ret;
	}
	
	public Attachment save(File file, String fileName) {
		Attachment ret = null;
		if (file != null) {
			try {
				String filePath = generatorFilePath();
				createFilePath(filePath);
				String newFileName = generatorFileName(fileName);
				String fullFileName = getFullFilePath(filePath, newFileName);
				while (isFileExist(fullFileName)) {
					newFileName = generatorFileName(fileName);
					fullFileName = getFullFilePath(filePath, newFileName);
				}
				FileUtils.copyFile(file, new File(fullFileName));
				ret = new Attachment();
				ret.setName(fileName);
				ret.setSaveName(newFileName);
				ret.setAttachPath(filePath);
				ret.setAttachSize(file.length());
				if (SessionTool.getUserLogonInfo() != null) {
					ret.setCreator(SessionTool.getUserLogonInfo().getSysUserId());
				}
				ret.setUploadType("1");
				if (log.isDebugEnabled()) {
					log.debug(JsonUtils.objectToJsonString(ret));
				}
				attachmentDao.save(ret);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return ret;
	}
	
	private static final void createFilePath(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {
			File file = new File(attachmentFilePath + File.separator + filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	private static final boolean isFileExist(String filePath) {
		boolean ret = false;
		if (StringUtils.isNotBlank(filePath)) {
			File file = new File(filePath);
			ret = file.exists();
		}
		return ret;
	}

	public String getFullFilePath(String filePath, String fileName) {
		String ret = attachmentFilePath;
		if (StringUtils.isNotBlank(filePath)) {
			ret += File.separator + filePath;
		}
		if (StringUtils.isNotBlank(fileName)) {
			ret += File.separator + fileName;
		}
		return ret;
	}

	public void delete(Long id) {
		if (id != null) {
			Attachment attachment = getById(id);
			if (attachment != null) {
				attachmentDao.delete(attachment);
				deleteAttachmentFile(getFullFilePath(attachment.getAttachPath(), attachment.getSaveName()));
			}
		}
	}

	public Attachment getById(Long id) {
		Attachment ret = null;
		if (id != null) {
			ret = attachmentDao.get(id);
		}
		return ret;
	}

	private static final String getFileSuffix(String fileName) {
		String suffix = "";
		if (fileName.contains(".")) {
			suffix = fileName.substring(fileName.lastIndexOf("."));
		}
		return suffix;
	}

	private static final String generatorFileName(String fileName) {
		return "" + System.currentTimeMillis() + getFileSuffix(fileName);
	}

	private static final String generatorFilePath() {
		Date now = new Date();
		return DATE_YEAR_FORMAT.format(now) + File.separator + DATE_MONTH_DAY_FORMAT.format(now);
	}

	private static final void deleteAttachmentFile(String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}

	@Override
	public List<Attachment> getAttachListByIdList(List<Long> idList) {
		if (idList == null || idList.isEmpty()) {
			return null;
		}
		List<Attachment> attachList = attachmentDao.getAttachListByIdList(idList);
		List<PropertyTransVo> transVoList = new ArrayList<PropertyTransVo>();
		transVoList = new ArrayList<PropertyTransVo>();
		transVoList.add(new PropertyTransVo("creator", Buser.class, "userId", "userName", "creatorName"));
		return (List<Attachment>) DynamicPropertyTransfer.transform(attachList, transVoList);
	}

	public String compressAttachment(List<Long> idList) {
		String result = compressTempDir + System.currentTimeMillis() + ".zip";
		List<Attachment> list = getAttachListByIdList(idList);
		List<String> fileList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (Attachment attachment : list) {
				String fileName = getFullFilePath(attachment.getAttachPath(), attachment.getSaveName());
				fileList.add(fileName);
				nameList.add(attachment.getName());
			}
		}
		CompressUtil.compress(fileList, nameList, result);
		return result;
	}

	@Override
	public String getTempDir() {
		return compressTempDir;
	}
}

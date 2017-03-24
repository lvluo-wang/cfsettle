package com.upg.ucars.basesystem.component.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.component.core.IAttachmentService;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.other.Attachment;

/**
 * 附件
 * 
 * @author shentuwy
 * @date 2012-6-29
 * 
 */
public class AttachmentAction extends BaseAction {

	private static final long	serialVersionUID	= 2718945693717757249L;

	private static Logger		log					= LoggerFactory
															.getLogger(AttachmentAction.class);

	private File				fileInput;

	private String				fileName;

	private String				contentType;

	private String				charset				= IAttachmentService.CHARSET_UTF8;

	private IAttachmentService	attachmentService;
	
	private boolean				imgServer;
	
	private String				nowater;
	
	private String				fileTypeExt;
	
	public String getNowater() {
		return nowater;
	}

	public void setNowater(String nowater) {
		this.nowater = nowater;
	}

	public String getFileTypeExt() {
		return fileTypeExt;
	}

	public void setFileTypeExt(String fileTypeExt) {
		this.fileTypeExt = fileTypeExt;
	}

	public void setFileInput(File fileInput) {
		this.fileInput = fileInput;
	}

	public void setFileInputFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileInputFileName() {
		return this.fileName;
	}

	public void setFileInputContentType(String contextType) {
		this.contentType = contextType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	
	// ================================================
		
	public boolean isImgServer() {
		return imgServer;
	}

	public void setImgServer(boolean imgServer) {
		this.imgServer = imgServer;
	}

	public String main() {
		return MAIN;
	}

	public String upload() {
		log.debug("imgServer:" + imgServer + ",doc:" + fileInput + ",fileName:" + fileName + ",contentType:"
				+ contentType);
		Attachment attachment = attachmentService.save(fileInput, fileName, imgServer, nowater, fileTypeExt);
		attachment = (Attachment) DynamicPropertyTransfer.dynamicAddProperty(attachment, "userName", SessionTool
				.getUserLogonInfo().getUserName());
		return setInputStreamData(attachment);
	}

	public String downLoad() {
		String attachId = getId();
		if (StringUtils.isNotBlank(attachId)) {
			Attachment attachment = attachmentService.getById(Long.valueOf(attachId));
			try {
				getHttpResponse().setContentType("application/json");
				getHttpResponse().setHeader("Access-Control-Allow-Origin", "*");
				fileName = URLEncoder.encode(attachment.getName(), IAttachmentService.CHARSET_UTF8);
				//fileName = attachment.getName();
				InputStream in = new FileInputStream(
						attachmentService.getFullFilePath(
								attachment.getAttachPath(),
								attachment.getSaveName()));
				setDataStream(in);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			} finally {

			}
		}
		return "download";
	}

	public void delete() {
		String attachId = getId();
		if (StringUtils.isNotBlank(attachId)) {
			attachmentService.delete(Long.valueOf(attachId));
		}
	}

	public String getAttachments() {
		List<Attachment> attachList = new ArrayList<Attachment>();
		List<Long> attachIds = getIdList();
		if (attachIds != null && attachIds.size() > 0) {
			for (Long attId : attachIds) {
				Attachment attachment = attachmentService.getById(attId);
				if (attachment != null) {
					attachList.add(attachment);
				}
			}
		}
		return setInputStreamData(attachList);
	}
}

package com.upg.cfsettle.mapping.banner;

import java.util.Date;

import com.upg.ucars.util.DateTimeUtil;

/**
 * 	Banner model类
 * @author renzhuolun
 * @date 2014年8月5日 上午10:14:51
 * @version <b>1.0.0</b>
 */
public class Banner {
	
	private Long id;
	
	/** 标题 */
	private String title;
		
	/** 未知，，枚举数据E022 */
	private Integer position;
		
	/** 图片路径 */
	private String img;
		
	/** 排序 */
	private Integer sort;
		
	/** 连接 */
	private String href;
		
	/**  */
	private Integer ctime;
		
	/**  */
	private Integer mtime;
		
	/** 1-激活，0未激活 */
	private String isActive;
		
	/** 描述 */
	private String content;
		
	/** 接入点号 */
	private String miNo;
	
	private String miNoCode;
	
	private Date ctimeDate;
	
	private Date mtimeDate;
	
	private String miName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Integer getCtime() {
		return ctime;
	}

	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}

	public Integer getMtime() {
		return mtime;
	}

	public void setMtime(Integer mtime) {
		this.mtime = mtime;
	}

	public Date getCtimeDate() {
		return DateTimeUtil.getSecondToDate(ctime);
	}

	public void setCtimeDate(Date ctimeDate) {
		this.ctimeDate = ctimeDate;
	}

	public Date getMtimeDate() {
		return DateTimeUtil.getSecondToDate(mtime);
	}

	public void setMtimeDate(Date mtimeDate) {
		this.mtimeDate = mtimeDate;
	}

	public String getMiNoCode() {
		return miNoCode;
	}

	public void setMiNoCode(String miNoCode) {
		this.miNoCode = miNoCode;
	}

	public String getMiName() {
		return miName;
	}

	public void setMiName(String miName) {
		this.miName = miName;
	}
}

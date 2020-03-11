package entity;

import java.sql.Timestamp;


/**
 * @author: 李旭芸
 * @className: FileMessage
 * @packageName: entity
 * @description: 文件信息实体类
 **/
public class FileMessage {
	private String fileName = "";
	private String uuidName = "";
	private Timestamp updateTime =null;
	private String type = "";
	private String path = "";
	private String user = "";
	private long size = 0;
	private String showSize;

	public FileMessage(String fileName, String uuidName, Timestamp updateTime, String type, String path, String user,
			long size) {
		super();
		this.fileName = fileName;
		this.uuidName = uuidName;
		this.updateTime = updateTime;
		this.type = type;
		this.path = path;
		this.user = user;
		this.size = size;
		//showSize = Conversion.conversion(size);
	}
	
	public FileMessage(FileMessage file) {
		super();
		this.fileName = file.getFileName();
		this.uuidName=file.getUuidName();
		this.updateTime = file.getUpdateTime();
		this.type = file.getType();
		this.path = file.getPath();
		this.user = file.getUser();
		this.size=file.getSize();
	}

	public String getShowSize() {
		return showSize;
	}

	public void setShowSize(String showSize) {
		this.showSize = showSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUuidName() {
		return uuidName;
	}

	public void setUuidName(String uuidName) {
		this.uuidName = uuidName;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}

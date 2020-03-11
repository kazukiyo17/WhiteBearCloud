package entity;


/**
 * @author: 林源
 * @className: ShareMessage
 * @packageName: entity
 * @lastmodifiedtime:2019年9月4日 
 **/
public class ShareMessage {
	private String user;


	private String fileName="";
	private String uuidName="";
	private String url="";
	public String shareTime="";

	private int downloads=0;
	private String size="";
	private String type="";

	public ShareMessage(String user,String fileName,String uuidName,String url,
						String shareTime, int downloads,String size,String type)
	{
		super();
		this.user=user;
		this.fileName=fileName;
		this.uuidName=uuidName;
		this.url=url;
		this.shareTime=shareTime;

		this.downloads=downloads;
		this.size=size;
		this.type=type;


	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShareTime() {
		return shareTime;
	}

	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}





	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

package biz;
import entity.ShareMessage;

/**
 * @author: 林源
 * @className: ShareHandleBiz
 * @packageName: biz
 * @description: 分享信息处理接口 
 **/
public interface ShareHandleBiz {

	/**
	 * 由url获取分享信息
	 * @param url
	 * @return
	 */
	public ShareMessage getsharebyurl(String url);

	/**
	 * 新增分享信息
	 * @param user
	 * @param fileName
	 * @param uuidName
	 * @param url
	 * @param shareTime
	 * @param downloads
	 * @param size
	 * @param type
	 * @return
	 */
	public String insertshare(String user,String fileName,String uuidName,String url,
							  String shareTime,  int downloads,String size,String type);


	/**
	 * 删除分享信息
	 * @param url
	 * @return
	 */
	public boolean delbyurl(String url);


}

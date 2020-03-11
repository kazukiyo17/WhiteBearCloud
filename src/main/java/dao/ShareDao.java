package dao;
import java.util.Vector;
import entity.ShareMessage;

/**
 * @author: 林源
 * @className: ShareDao
 * @packageName: dao
 * @lastmodifiedtime:2019年9月4日 
 **/
public interface  ShareDao {
	//统计分享文件
	public int countallshare();
	public int countusershare(String user);
	//增加分享文件
	public int addshare(ShareMessage share);
	public String adddownload(String uuidName);
	//删除分享文件
	public int delshare(String url);//删除指定一个
	public int delallshare(String user);//删除用户所有
	//查找分享文件及其信息
	public Vector<ShareMessage> findbyuser(String user);//user找信息
	//public Vector<ShareMessage> findbykey();//用key找所有信息
	public Vector<ShareMessage> findbyurl(String url);
}

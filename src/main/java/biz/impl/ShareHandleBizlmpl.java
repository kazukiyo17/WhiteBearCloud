package biz.impl;
import java.util.Vector;

import biz.ShareHandleBiz;
import dao.impl.ShareDaolmpl;
import entity.ShareMessage;

/**
 * @author: 林源
 * @className: LoginBizImpl
 * @packageName: biz.impl
 * @description: 登录的业务逻辑实现
 **/

public class ShareHandleBizlmpl implements ShareHandleBiz  {

	@Override
	public ShareMessage getsharebyurl(String url) {
		// TODO Auto-generated method stub
		ShareDaolmpl share=new ShareDaolmpl();
		Vector<ShareMessage> ashare=share.findbyurl(url);
		ShareMessage sharems=ashare.elementAt(0);
		return sharems;
	}


	@Override
	public boolean delbyurl(String url) {
		ShareDaolmpl share=new ShareDaolmpl();
		int i=share.delshare(url);
		if(i!=0)
			return true;
		else
			return false;
	}


	@Override
	public String insertshare(String user,String fileName,String uuidName,String url,
							  String shareTime,int downloads,String size,String type) {
		ShareDaolmpl share=new ShareDaolmpl();

		ShareMessage oneshare=new ShareMessage(user, fileName, uuidName, url,
				shareTime,0,size, type);

		share.addshare(oneshare);

		return null;
	}


}

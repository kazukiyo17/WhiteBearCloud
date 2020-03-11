package util;

/**
 * 
 *@class_name：htmlText
 *@comments:定义邮件内容
 *@param:
 *@return: 
 *@author:冯书逸
 *@createtime:2019年9月5日
 */
public class htmlText {
		//  返回页面Html携带的6位随机码
		public static String html(String code) {
			
			String html = "Email地址验证<br/>"+ 
			"这封邮件是由【白熊云】发送的。<br/>"+
			"你收到这封邮件是【白熊云】进行新用户注册或者用户修改账目信息时使用这个地址。<br/>"+
			"请将下面的验证码输入到提示框即可：<h3 style='color:red;'>" + code + "</h3><br/>";
			return html;
		}
	}

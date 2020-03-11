package biz;

import entity.User;

/**
 * @author: 李旭芸
 * @className: LoginBiz
 * @packageName: biz
 * @description: 登录的业务逻辑接口
 **/

public interface LoginBiz {
	/**
	 * 
	 * @param account 用户输入的账号
	 * @param password 用户输入的密码
	 * @return User 用户实体类，存放用户账号密码用户名
	 */
	public User login(String account, String password);
}

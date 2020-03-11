package biz.impl;

import biz.LoginBiz;
import dao.impl.UserDaoImpl;
import entity.User;

/**
 * @author: 李旭芸
 * @className: LoginBizImpl
 * @packageName: biz.impl
 * @description: 登录的业务逻辑实现
 **/

public class LoginBizImpl implements LoginBiz {
	private static UserDaoImpl userdao=new UserDaoImpl();
	public User login(String account, String pwd) {
		System.out.println("account:" + account);
		System.out.println(pwd);
		//从数据库获取用户信息
		User user=userdao.findUserByAccount(account);
		System.out.println(user+"$$$$");
		System.out.println(user.getPwd());
		if (user != null && pwd.equals(user.getPwd())) {
			return user;
		}
		return null;
	}

}

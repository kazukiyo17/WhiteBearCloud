package dao;

import java.util.Vector;
import entity.User;


/**
 * @author: 李旭芸
 * @className: UserDao
 * @packageName: dao
 * @lastmodifiedtime:2019年9月4日 
 **/
public interface UserDao {
	// 统计用户总数
	public int countUsers();
	// 查找用户
	public Vector<User> findbyaccount(String getaccount);
	public User findUserByAccount(String getaccount);
	public Vector<User> findbyusername(String getUserName);
	// 添加用户
	public int addUser(String getUserName,String pwd,String getaccount);
	// 删除用户
	public int delUser(String getaccount);
	public int updateUser(String newUserName,String newpwd,String getaccount);


}

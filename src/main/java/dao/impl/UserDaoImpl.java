package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dao.BaseDao;
import dao.UserDao;
import dao.RSProcessor;

import entity.User;

/**
 * @author: 林源
 * @className: doprefix
 * @packageName: biz
 * @description: 分离前后缀
 * @lastmodifiedtime:2019年9月1日 
 **/
public class UserDaoImpl extends BaseDao implements UserDao {

	public int countUsers() {

		String sql = "select count(*) as a usercount from userlist";

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				int count = 0;
				if (rs != null) {
					if (rs.next()) {
						count = rs.getInt("usercount");
					}
				}
				return new Integer(count);
			}

		};

		return (Integer) this.executeQuery(getResultProcessor, sql);
	}

	// TODO Auto-generated method stub

	@SuppressWarnings("unchecked")
	public Vector<User> findbyaccount(String getaccount) {
		// TODO Auto-generated method stub
		String sql = "select * from userlist where  account = ?";
		Object[] params = { getaccount };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<User> users = new Vector<User>();

				while (rs.next()) {
					String userName = rs.getString("username");
					String account = rs.getString("account");
					String pwd = rs.getString("pwd");

					User auser = new User(userName, pwd, account);
					users.add(auser);
				}
				return users;

			}
		};

		return (Vector<User>) this.executeQuery(getResultProcessor, sql, params);
	}

	@SuppressWarnings("unchecked")
	public User findUserByAccount(String getaccount) {
		// TODO Auto-generated method stub
		String sql = "select * from userlist where  account = ?";
		Object[] params = { getaccount };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				User auser = new User();
				if (rs.next()) {
					String userName = rs.getString("username");
					String account = rs.getString("account");
					String pwd = rs.getString("pwd");
					auser = new User(userName, pwd, account);
				}
				return auser;
			}
		};

		return (User) this.executeQuery(getResultProcessor, sql, params);
	}
	
	@SuppressWarnings("unchecked")
	public Vector<User> findbyusername(String getUserName) {
		String sql = "select * from userlist where  username = ?";
		Object[] params = { getUserName };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<User> users = new Vector<User>();

				while (rs.next()) {
					String userName = rs.getString("username");
					String account = rs.getString("account");
					String pwd = rs.getString("pwd");

					User auser = new User(userName, pwd, account);
					users.add(auser);
				}
				return users;

			}
		};

		return (Vector<User>) this.executeQuery(getResultProcessor, sql, params);
	}

	public int delUser(String getaccount) {

		String sql = "delete from userlist where account=?";
		Object[] params = { getaccount };
		 return this.executeUpdate(sql, params);

	}

	public int addUser(String getUserName, String getpwd, String getaccount) {
		String sql = "insert userlist (username,pwd,account) values(?,?,?)";
		Object[] params = { getUserName, getpwd, getaccount };
		 return this.executeUpdate(sql, params);

	}
	
	public int updateUser(String newUserName, String newpwd, String getaccount) {
		delUser(getaccount);

		return addUser(newUserName,newpwd,getaccount);

	}

}
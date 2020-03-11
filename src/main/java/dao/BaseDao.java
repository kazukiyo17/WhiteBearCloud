package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory;

/**
 * @author: 李旭芸
 * @className: BaseDao
 * @packageName: dao
 * @description: 数据库操作类
 **/

public abstract class BaseDao {
	public static String sqlDialect;
	public static DataSource dataSource;
	private static Properties properties = new Properties();

	static{
		try{

			InputStream rs = BaseDao.class.getClassLoader().getResourceAsStream("dbcp.properties");
			properties.load(rs);
			//properties.load(is);
			System.out.println("properties"+properties);
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			dataSource = BasicDataSourceFactory.createDataSource(properties);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接对象。
	 */
	public Connection getConnection() {
		Connection conn = null;// 数据连接对象
		// 获取连接并捕获异常
		try {
			conn = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();// 异常处理
		}
		return conn;// 返回连接对象
	}

	/**
	 * 关闭数据库连接。
	 *
	 * @param conn 数据库连接
	 * @param stmt Statement对象
	 * @param rs   结果集
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		// 若结果集对象不为空，则关闭
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 若Statement对象不为空，则关闭
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 若数据库连接对象不为空，则关闭
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void closeAll(Connection conn, Statement stmt) {
		closeAll(conn, stmt, null);
	}

	/**
	 * 增、删、改操作
	 *
	 * @param sql    sql语句
	 * @param params 参数数组
	 * @return 执行结果
	 */
	public int executeUpdate(String sql, Object... params) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			if (conn != null && !conn.isClosed()) {
				pstmt = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				System.out.println(pstmt);
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt);
		}
		return result;
	}

	public Object executeQuery(RSProcessor processor, String sql, Object... params) {
		Object result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			if (conn != null && conn.isClosed() == false) {
				pstmt = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				result = processor.process(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return result;
	}
}

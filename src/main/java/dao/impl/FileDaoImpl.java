package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import dao.BaseDao;
import dao.FileDao;
import dao.RSProcessor;
import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileDaoImpl
 * @packageName: dao.impl
 * @description: 文件列表数据库操作实现类
 **/

public class FileDaoImpl extends BaseDao implements FileDao {

	@SuppressWarnings("unchecked")
	public Vector<FileMessage> findFilesByPathAndUser(String path, String userName) {
		String sql = "select * from filelist where path = ? and user = ?";
		Object[] params = { path, userName };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<FileMessage> files = new Vector<FileMessage>();
				//System.out.println("select filelist "+path+"\t"+userName);
				
				while (rs.next()) {
					String fileName = rs.getString("fileName");
					String uuidName = rs.getString("uuidName");
					Timestamp updateTime = rs.getTimestamp("updateTime");
					String type = rs.getString("filetype");
					String path = rs.getString("path");
					String user = rs.getString("user");
					long size = rs.getLong("size");
					FileMessage fileMessage = new FileMessage(fileName, uuidName, updateTime, type, path, user, size);
					files.add(fileMessage);
				}
				return files;

			}
		};

		return (Vector<FileMessage>) this.executeQuery(getResultProcessor, sql, params);
	}

	public int addFile(FileMessage file) {
		System.out.println("yes");
		String sql = "insert filelist (fileName,uuidName,updateTime,filetype,path,user,size) values(?,?,?,?,?,?,?)";
		Object[] params = { file.getFileName(), file.getUuidName(), file.getUpdateTime(), file.getType(),
				file.getPath(), file.getUser(), file.getSize() };
		return this.executeUpdate(sql, params);
	}

	@SuppressWarnings("unchecked")
	public Vector<FileMessage> findFilesByTypeAndUser(String type, String user) {
		String sql = "select * from filelist where filetype = ? and user = ? order by updateTime desc";
		Object[] params = { type, user };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<FileMessage> files = new Vector<FileMessage>();

				while (rs.next()) {
					String fileName = rs.getString("fileName");
					String uuidName = rs.getString("uuidName");
					Timestamp updateTime = rs.getTimestamp("updateTime");
					String type = rs.getString("filetype");
					String path = rs.getString("path");
					String user = rs.getString("user");
					long size = rs.getLong("size");

					FileMessage fileMessage = new FileMessage(fileName, uuidName, updateTime, type, path, user, size);
					files.add(fileMessage);
				}
				return files;
			}
		};

		return (Vector<FileMessage>) this.executeQuery(getResultProcessor, sql, params);
	}
	
	@SuppressWarnings("unchecked")
	public Vector<FileMessage> findFilesByUser(String user) {
		String sql = "select * from filelist where user = ? order by updateTime desc";
		Object[] params = {user };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<FileMessage> files = new Vector<FileMessage>();

				while (rs.next()) {
					String fileName = rs.getString("fileName");
					String uuidName = rs.getString("uuidName");
					Timestamp updateTime = rs.getTimestamp("updateTime");
					String type = rs.getString("filetype");
					String path = rs.getString("path");
					String user = rs.getString("user");
					long size = rs.getLong("size");

					FileMessage fileMessage = new FileMessage(fileName, uuidName, updateTime, type, path, user, size);
					files.add(fileMessage);
				}
				return files;
			}
		};

		return (Vector<FileMessage>) this.executeQuery(getResultProcessor, sql, params);
	}

	public int delFileByUuidName(String uuidName) {
		String sql = "delete from filelist where uuidName = ?";
		Object[] params = { uuidName };
		return this.executeUpdate(sql, params);
	}

	public int delFolder(String path, String fileName) {
		String sql = "delete from filelist where fileName = ? and filetype = 'folder' and path = ?";
		Object[] params = { fileName, path };
		return this.executeUpdate(sql, params);
	}

	public int countFiles() {
		String sql = "select count(*) as file_count from filelist";

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				int count = 0;
				if (rs != null) {
					if (rs.next()) {
						count = rs.getInt("file_count");
					}
				}
				return new Integer(count);
			}

		};

		return (Integer) this.executeQuery(getResultProcessor, sql);
	}

}

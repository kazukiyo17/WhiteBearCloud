package dao;

import java.util.Vector;

import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileDao
 * @packageName: dao
 * @description: 文件列表数据库操作接口
 **/

public interface FileDao {
	/**
	 * 统计文件总数
	 * @return
	 */
	public int countFiles();
	/**
	 * 按用户和路径查找文件
	 * @param path 路径
	 * @param userName 用户
	 * @return
	 */
	public Vector<FileMessage> findFilesByPathAndUser(String path, String userName);
	/**
	 * 按类型和路径查找文件
	 * @param path 路径
	 * @param userName 用户
	 * @return
	 */
	public Vector<FileMessage> findFilesByTypeAndUser(String type, String user);
	/**
	 * 按用户查找文件
	 * @param user
	 * @return
	 */
	public Vector<FileMessage> findFilesByUser(String user);
	/**
	 * 添加文件信息
	 * @param file 文件信息实体
	 * @return
	 */
	public int addFile(FileMessage file);
	/**
	 * 按uuid删除文件信息
	 * @param uuidName
	 * @return
	 */
	public int delFileByUuidName(String uuidName);
	/**
	 * 删除文件夹
	 * @param path
	 * @param fileName
	 * @return
	 */
	public int delFolder(String path, String fileName);
}

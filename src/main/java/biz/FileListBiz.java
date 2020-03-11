package biz;

import java.util.LinkedHashMap;
import java.util.Vector;

import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileListBiz
 * @packageName: biz
 * @description: 文件列表的业务逻辑接口
 **/

public interface FileListBiz {
	/**
	 * 由路径和用户账号获取文件列表
	 * @param path
	 * @param userName
	 * @return 文件信息实体数组
	 */
	public Vector<FileMessage> getFilesByPathAndUser(String path, String userName);
	
	/**
	 * 由用户账号获取文件列表
	 * @param userName
	 * @return 文件信息实体数组
	 */
	public Vector<FileMessage> getFilesByUser(String userName);

	/**
	 * 获取上一级路径
	 * @param path
	 * @return 上一级路径String
	 */
	public String getLastPath(String path);

	/**
	 * 路径的哈希列表
	 * @param path
	 * @return
	 */
	public LinkedHashMap<String, String> getPaths(String path);
}

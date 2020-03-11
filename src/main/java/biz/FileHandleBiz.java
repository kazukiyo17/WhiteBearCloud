package biz;

import java.util.Vector;

import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileHandleBiz
 * @packageName: biz
 * @description: 文件处理的业务逻辑接口
 **/
public interface FileHandleBiz {
	/**
	 * 新建文件夹
	 * @param foldername 文件夹名称
	 * @param folderpath 文件夹路径
	 * @param user 用户
	 */
	public void addFolder(String foldername,String folderpath,String user);
	/**
	 * 删除文件夹
	 * @param foldername  文件夹名称
	 * @param user 用户
	 * @param fileSaveRootPath 文件夹路径
	 * @return true-成功  false-失败
	 */
	public boolean deleteFolder(String foldername,String path,String user,String fileSaveRootPath);
	
	/**
	 * 查询文件
	 * @param files
	 * @param filename
	 * @return
	 */
	public FileMessage searchFile(Vector<FileMessage> files,String filename);
}


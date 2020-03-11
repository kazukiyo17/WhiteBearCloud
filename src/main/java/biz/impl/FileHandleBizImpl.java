package biz.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Vector;

import biz.FileHandleBiz;
import dao.impl.FileDaoImpl;
import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileHandleBizImpl
 * @packageName: biz.impl
 * @description: 文件处理的业务逻辑实现
 **/

public class FileHandleBizImpl implements FileHandleBiz {
	private static FileDaoImpl filedao = new FileDaoImpl();
	private static FileListBizImpl filelistBiz = new FileListBizImpl();
	
	public FileHandleBizImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addFolder(String foldername, String folderpath, String user) {
		System.out.println("�½��ļ���"+foldername);
		// uuid
		String uuid = UUID.randomUUID().toString();
		// 获取时间
		Timestamp now_time = new Timestamp(System.currentTimeMillis());
		// 文件类型-文件夹
		String filetype = "folder";
		// 初始大小
		long size = 0;
		//创建文件信息
		FileMessage folder = new FileMessage(foldername, uuid, now_time, filetype, folderpath, user, size);
		filedao.addFile(folder);

	}

	public boolean deleteFolder(String foldername,String path, String userName, String fileSaveRootPath) {
		Vector<FileMessage> folders = filelistBiz.getFilesByPathAndUser(path, userName);
		Vector<FileMessage> allfile = filelistBiz.getFilesByPathAndUser(path+"/"+foldername, userName);
		System.out.println("文件夹+路径"+path+foldername);
		//数据库中没有此文件夹
		if(folders.isEmpty())
			return false;
		for(FileMessage tmp : folders) {
			if(tmp.getFileName().equals(foldername)&&tmp.getType().equals("folder")) {
				System.out.println("ɾ���ļ���"+tmp.getFileName());
				String fileuuid = tmp.getUuidName();
				filedao.delFileByUuidName(fileuuid);
			}
		}
		// 遍历文件夹中文件
		for (FileMessage tmp : allfile) {
			String fileuuid = tmp.getUuidName();
			File file = new File(fileSaveRootPath + "\\" + fileuuid + "." + tmp.getType());
			if (!file.exists())
				return false;
			else {
				file.delete();
				filedao.delFileByUuidName(fileuuid);
				return true;
			}
		}
		return true;// 删除成功
	}
	
	public FileMessage searchFile(Vector<FileMessage> files,String filename) {
		for(FileMessage tmp:files) {
			System.out.println(tmp.getFileName());
			if(tmp.getFileName().equals(filename))
				return tmp;
		}
		return null;
	}

}
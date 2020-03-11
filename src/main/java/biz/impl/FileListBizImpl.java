package biz.impl;

import java.util.LinkedHashMap;
import java.util.Vector;
import biz.FileListBiz;
import dao.impl.FileDaoImpl;
import entity.FileMessage;

/**
 * @author: 李旭芸
 * @className: FileListBizImpl
 * @packageName: biz.impl
 * @description: 文件列表的业务逻辑实现
 **/

public class FileListBizImpl implements FileListBiz {
	private static FileDaoImpl fileDaoImpl = new FileDaoImpl();
	
	public Vector<FileMessage> getFilesByPathAndUser(String path, String userName) {
		//从数据库获取
		Vector<FileMessage> files = fileDaoImpl.findFilesByPathAndUser(path, userName);
		return files;
	}
	
	public Vector<FileMessage> getFilesByUser(String userName) {
		Vector<FileMessage> files = fileDaoImpl.findFilesByUser(userName);
		return files;
	}

	public String getLastPath(String path) {
		String pathNames[] = path.split("/");
		int len = pathNames.length;
		String lastPath = "";
		for (int i = 0; i < len - 1; i++) {
			if (i == 0) {
				lastPath = pathNames[i];
			} else {
				lastPath = lastPath + "/" + pathNames[i];
			}
		}
		return lastPath;
	}

	public LinkedHashMap<String, String> getPaths(String path) {
		LinkedHashMap<String, String> paths = new LinkedHashMap<String, String>();
		String pathNames[] = path.split("/");
		String curPath = "";
		for (String name : pathNames) {
			if (curPath == "") {
				curPath = name;
				paths.put(name, curPath);
			} else {
				curPath = curPath + "/" + name;
				paths.put(name, curPath);
			}
		}
		return paths;
	}
}

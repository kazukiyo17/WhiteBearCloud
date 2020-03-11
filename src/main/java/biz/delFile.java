package biz;

import java.io.File;

/**
 * @author: 林源
 * @className: delFile
 * @packageName: biz
 * @description: 删除文件
 **/
public class delFile {
    public  boolean deleteFile(String filePath) {
        File file = new File(filePath);
        //如果文件路径对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.err.println("文件" + filePath + "删除成功！");
                return true;
            } else {
                System.err.println("文件" + filePath + "删除失败！");
                return false;
            }
        } else {
            System.err.println("文件" + filePath + "不存在！");
            return false;
        }
    }
    public  boolean deleteDirectory (String dir) {
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的问件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.err.println("文件夹" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        //删除问价夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                deleteDirectory(files[i].getAbsolutePath());
            }
        }
        //删除当前目录
        if (dirFile.delete()) {
            System.err.println("目录" + dir + "删除成功！");
            return true;
        } else {
            return false;
        }
    }
}

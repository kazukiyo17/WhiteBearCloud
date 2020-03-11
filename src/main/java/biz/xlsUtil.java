package biz;

import java.io.*;

/**
 * @author: 林源
 * @className: xlsUtil
 * @packageName: biz
 * @lastmodifiedtime:2019年9月2日 
 **/
import com.aspose.cells.*;
public class xlsUtil {

	/**
	 * @description：获取证书
	 * @return
	 */
    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = PdfUtil.class.getClassLoader().getResourceAsStream("xlsxlicense.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @description：预览excel文件
     * @param wordPath
     * @param pdfPath
     */
    public  void excel2pdf(String wordPath, String pdfPath) {

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File pdfFile = new File(pdfPath);// 输出路径
            Workbook wb = new Workbook(wordPath);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + pdfFile.getPath()); //转化过程耗时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
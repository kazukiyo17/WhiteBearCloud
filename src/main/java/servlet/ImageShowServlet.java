package servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import biz.delFile;
import biz.xlsUtil;
import dao.impl.*;
import entity.FileMessage;
import biz.App;
import biz.PdfUtil;

/**
 * @author: 林源
 * @className: ImageShowServlet
 * @packageName: servlet
 * @description: 图片预览
 * @lastmodifiedtime:2019年8月31日（大概）
 **/
@WebServlet("/ImageShowServlet")
@MultipartConfig
public class ImageShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageShowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");

		// userName实质为account，此处获取@前面的内容（特指qq号），用于指向存储桶
		String bucketName = userName.substring(0, userName.lastIndexOf("@"));

		String path = (String) session.getAttribute("filepath");
		FileDaoImpl FileDaoImpl = new FileDaoImpl();
		Vector<FileMessage> files = FileDaoImpl.findFilesByPathAndUser(path, userName);
		String getfileName = request.getParameter("filename");
		int i = 0;
		while (i < files.size()) {
			if (files.elementAt(i).getFileName().equals(getfileName))
				break;
			else
				i++;

		}

		String prefix = files.elementAt(i).getType();// 后缀
		String truename = files.elementAt(i).getUuidName();// 前缀
		String fileName = truename + "." + prefix;
		System.out.println("文件名是" + fileName);
		// 得到要下载的文件名
		// String fileName = request.getParameter("filename"); //23239283-92489-阿凡达.avi
		// fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
		// 上传的文件都是保存在/WEB-INF/upload目录下的子目录当中

		String fileSaveRootPath = this.getServletContext().getRealPath("upload");
		App.getCosClient();
		System.out.println(fileSaveRootPath);

		App.downLoadFile(files.elementAt(i).getUuidName(), fileSaveRootPath + "/" + fileName, bucketName);
		String cachePath = this.getServletContext().getRealPath("cache");
		File cFile = new File(cachePath);
		if (!cFile.exists()) {
			// 创建临时目录
			cFile.mkdir();
		}
		// 得到要下载的文件
		File file = new File(fileSaveRootPath + "\\" + fileName);
		String filepath = fileSaveRootPath + "\\" + fileName; // 全路径

		PdfUtil changepdf = new PdfUtil();

		xlsUtil changexls = new xlsUtil();
		System.out.println("这是" + prefix);
		System.out.println("这是" + truename);
		OutputStream os = response.getOutputStream();
		System.out.println(request.getContextPath());
		if (prefix.equals("txt") || prefix.equals("pdf") || prefix.equals("jpg") || prefix.equals("png")
				|| prefix.equals("jpeg")) {
			System.out.println("这是文本文档");

			FileInputStream fips = new FileInputStream(file);
			byte[] btImg = readStream(fips);
			fips.close();
			os.write(btImg);
			os.flush();
			os.close();
			request.setAttribute("message", "预览成功");
		} else

		{
			if (prefix.equals("doc") || prefix.equals("docx")) {

				String docfilepath = cachePath + "\\" + truename + ".pdf";
				changepdf.doc2pdf(filepath, docfilepath);
				File docfile = new File(docfilepath);

				FileInputStream fips = new FileInputStream(docfile);
				byte[] btImg = readStream(fips);
				fips.close();
				os.write(btImg);
				os.flush();
				os.close();

				System.out.println("hello world");
				// docfile.delete();
				request.setAttribute("message", "预览成功");
			} else {

				if (prefix.equals("xls") || prefix.equals("xlsx")) {

					String pptfilepath = cachePath + "\\" + truename + ".pdf";
					changexls.excel2pdf(filepath, pptfilepath);
					File pptfile = new File(pptfilepath);
					FileInputStream fips = new FileInputStream(pptfile);
					byte[] btImg = readStream(fips);
					fips.close();
					os.write(btImg);
					os.flush();
					os.close();
					request.setAttribute("message", "预览成功");
				}
				// pptfile.delete();
				else {
					System.out.println("这是文本文档");

					FileInputStream fips = new FileInputStream(file);
					byte[] btImg = readStream(fips);
					fips.close();
					os.write(btImg);
					os.flush();
					os.close();

					request.setAttribute("message", "正常预览失败，可能显示不正常");

				}

			}
		}

		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	/**
	 * 读取管道中的流数据
	 */
	public byte[] readStream(InputStream inStream) {
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		int data = -1;
		try {
			while ((data = inStream.read()) != -1) {
				bops.write(data);
			}
			inStream.close();
			byte[] bos = bops.toByteArray();
			bops.close();
			String cachePath = this.getServletContext().getRealPath("cache");
			delFile delit = new delFile();
			delit.deleteDirectory(cachePath);

			File cFile = new File(cachePath);
			if (!cFile.exists()) {
				// 创建临时目录
				cFile.mkdir();
			}
			System.out.println(cachePath);

			return bos;
		} catch (Exception e) {
			return null;
		}
	}

}

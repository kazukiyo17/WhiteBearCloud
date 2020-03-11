package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.impl.ShareDaolmpl;
import entity.ShareMessage;
import biz.App;

/**
 * @author: 林源
 * @class_name：ShareDownServlet
 * @packageName: servlet
 * @description: 通过分享链接下载文件
 **/
public class ShareDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// @WebServlet("/ShareDownServlet") 原因不明的报错
	public ShareDownServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theurl = request.getParameter("shareurl");
			;// 前端没做,需要前端输入
			System.out.println("在数据库寻找文件");
			ShareDaolmpl myShareDaoImpl = new ShareDaolmpl();
			Vector<ShareMessage> files = myShareDaoImpl.findbyurl(theurl);
			if (files.isEmpty()) {
				request.setAttribute("message", "文件不存在");
				request.getRequestDispatcher("home.jsp").forward(request, response);
				return;
			}
			ShareMessage thefile = files.elementAt(0);
			String userName = thefile.getUser();
			String fileuuid = thefile.getUuidName();
			String fileName = thefile.getFileName();
			String truename = fileuuid + "." + thefile.getType();
			String fileSaveRootPath = this.getServletContext().getRealPath("upload");
			System.out.println("在数据库找到文件");
			App.getCosClient();
			System.out.println(fileSaveRootPath);

			// 获取@前面的内容，本处特指qq号，用于指向存储桶
			String bucketName = userName.substring(0, userName.lastIndexOf("@"));

			App.downLoadFile(fileuuid, fileSaveRootPath + "/" + truename, bucketName);
			// 获取文件uuid

			System.out.println("文件uuid：" + fileuuid);
			System.out.println("在服务器寻找文件");
			// 上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
			// 得到要下载的文件
			File file = new File(fileSaveRootPath + "\\" + fileuuid + "." + thefile.getType());
			System.out.println(fileSaveRootPath + "\\" + fileuuid + "." + thefile.getType());
			if (!file.exists()) {
				request.setAttribute("message", "文件不存在2");
				request.getRequestDispatcher("FileListServlet").forward(request, response);
				return;
			}
			// 处理文件名
			String realname = fileName.substring(fileName.indexOf("_") + 1) + "." + thefile.getType();
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			// 读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileuuid + "." + thefile.getType());
			// 创建输出流
			OutputStream out = response.getOutputStream();
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区当中
			while ((len = in.read(buffer)) > 0) {
				// 输出缓冲区的内容到浏览器，实现文件下载
				out.write(buffer, 0, len);
			}
			// 关闭文件输入流
			System.out.println("关闭文件输入流");
			in.close();
			// 关闭输出流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "上传文件失败");
		}
		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}

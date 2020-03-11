package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.impl.FileDaoImpl;
import dao.impl.ShareDaolmpl;
import entity.FileMessage;
import entity.ShareMessage;
import biz.App;

/**
 * Servlet implementation class ShareSaveServlet
 */

/**
 * @author: 林源
 * @class_name：ShareSaveServlet
 * @packageName: servlet
 * @description: 通过分享链接保存文件
 **/
public class ShareSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 *
	 */
	public ShareSaveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String theurl = request.getParameter("saveurl");
			String useraccount = (String) session.getAttribute("name");

			System.out.println("在数据库寻找文件");
			ShareDaolmpl myShareDaoImpl = new ShareDaolmpl();
			Vector<ShareMessage> files = myShareDaoImpl.findbyurl(theurl);
			String filepath = (String) session.getAttribute("filepath");
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
			String newuuid = UUID.randomUUID().toString();
			App.getCosClient();

			// 获取@前面的内容，本处特指qq号，用于指向存储桶
			String bucketName2 = useraccount.substring(0, useraccount.lastIndexOf("@"));

			App.uploadFile(newuuid, fileSaveRootPath + "/" + truename, bucketName2);
			FileDaoImpl fileDaoImpl = new FileDaoImpl();
			Timestamp now_time = new Timestamp(System.currentTimeMillis());

			// 将filepath改为"upload" FileMessage file = new FileMessage(fileName + "-1",
			// newuuid, now_time, thefile.getType(), filepath,
			FileMessage file = new FileMessage(fileName + "-1", newuuid, now_time, thefile.getType(), "upload",
					useraccount, Long.parseLong(thefile.getSize()));

			fileDaoImpl.addFile(file);
			File delfile = new File(fileSaveRootPath + "/" + truename);
			delfile.delete();
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

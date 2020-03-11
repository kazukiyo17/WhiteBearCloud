package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.impl.ShareDaolmpl;
import entity.ShareMessage;
import biz.App;

/**
 * Servlet implementation class ShareServlet
 */

/**
 * @author: 林源
 * @class_name：ShareServlet
 * @packageName: servlet
 * @description: 生成分享链接
 **/
@WebServlet("/ShareServlet")
@MultipartConfig
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShareServlet() {
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
		String fileName = request.getParameter("filename");
		String uuid = request.getParameter("uuidname");
		String username = request.getParameter("username");
		String size = request.getParameter("size");
		String type = request.getParameter("filetype");
		long setsharetime = 10080L;// 前端输时间还没做
		String time = String.valueOf(setsharetime) + "分钟";
		System.out.println("分享时间是" + time);
		if (uuid == null) {
			request.setAttribute("message", "设置成功");
			request.getRequestDispatcher("FileListServlet").forward(request, response);
			response.sendRedirect("/a.jsp");
		}
		// 获取@前面的内容，本处特指qq号，用于指向存储桶
		String bucketName = username.substring(0, username.lastIndexOf("@"));

		String myurl = App.getfiledownloadkey(bucketName, uuid, setsharetime);// 前端输时间还没做
		System.out.println("url是" + myurl);
		ShareMessage share = new ShareMessage(username, fileName, uuid, myurl, time, 0, size, type);
		ShareDaolmpl newshare = new ShareDaolmpl();
		newshare.addshare(share);
		if (myurl != null) {
			String message = "生成的链接是" + myurl;
			request.setAttribute("message", message);
			request.getRequestDispatcher("FileListServlet").forward(request, response);
		} else {
			String message = "生成链接失败";
			request.setAttribute("message", message);
			request.getRequestDispatcher("FileListServlet").forward(request, response);
		}
		System.out.println("***************");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

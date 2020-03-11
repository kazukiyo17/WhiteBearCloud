package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.impl.FileHandleBizImpl;

/**
 * Servlet implementation class FolderServlet
 */

 /**
 * @author: 龚德超
 * @className: FolderServlet
 * @packageName: servlet
 * @description: 文件夹创建管理
 * @lastmodifiedtime:2019年9月3日（大概）
 **/
@WebServlet("/FolderServlet")
public class FolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String findpath = "123";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FolderServlet() {
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
		// 获取session
		try {
			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("name");// 用户
			String filepath = (String) session.getAttribute("filepath");// 文件夹路径
			String foldername = (String) request.getParameter("newfoldername");// 文件夹名称
			// 逻辑实现
			FileHandleBizImpl filehandle = new FileHandleBizImpl();
			filehandle.addFolder(foldername, filepath, user);
			request.setAttribute("message", "文件夹创建成功");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "新建文件夹失败");
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
		doGet(request, response);
	}

}

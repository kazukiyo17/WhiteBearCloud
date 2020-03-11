package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import biz.impl.FileListBizImpl;
import entity.FileMessage;
import java.util.Vector;

/**
 * Servlet implementation class SearchFileServlet
 */

/**
 * @author: 龚德超
 * @className: SearchFileServlet
 * @packageName: servlet
 * @description: 根据文件名搜索文件
 **/
@WebServlet("/SearchFileServlet")
public class SearchFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Vector<FileMessage> files = new Vector<FileMessage>();
		// 获取用户名
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		// 获取关键字
		String searchthing = request.getParameter("searchthing");
		// 获取该用户所有文件

		System.out.println(userName + "searching" + searchthing);

		FileListBizImpl filelistBiz = new FileListBizImpl();
		Vector<FileMessage> allfile = filelistBiz.getFilesByUser(userName);
		for (FileMessage tmp : allfile) {
			if (tmp.getFileName().indexOf(searchthing) != -1) {
				System.out.println(tmp.getFileName());
				System.out.println(tmp.getSize());
				System.out.println(tmp.getUpdateTime());
				files.add(tmp);
			}
		}

		request.setAttribute("files", files);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}

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
 * @author: 龚德超
 * @className: DeleteFolderServlet
 * @packageName: servlet
 * @description: 删除文件夹
 **/

@WebServlet("/DeleteFolderServlet")
public class DeleteFolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static FileHandleBizImpl filehandle = new FileHandleBizImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteFolderServlet() {
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
		try {
			// 获取用户名
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			String filepath = (String) session.getAttribute("filepath");
			String foldername = request.getParameter("foldername");
			System.out.println("将要删除文件夹" + foldername);
			String fileSaveRootPath = this.getServletContext().getRealPath("upload");
			// FileHandleBizImpl filehandle=new FileHandleBizImpl();
			if (filehandle.deleteFolder(foldername, filepath, userName, fileSaveRootPath)) {
				request.setAttribute("message", "文件夹删除成功");
			} else {
				request.setAttribute("error", "文件夹删除失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "文件夹删除失败");
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

}

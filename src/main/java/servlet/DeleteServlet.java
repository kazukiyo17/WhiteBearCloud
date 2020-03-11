package servlet;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import biz.impl.FileHandleBizImpl;
import dao.impl.FileDaoImpl;
import entity.FileMessage;
import biz.App;

/**
 * @author: 林源
 * @className: DeleteServlet
 * @packageName: servlet
 * @description: 删除文件
 **/

@SuppressWarnings("serial")
@WebServlet("/DeleteServlet")
@MultipartConfig
public class DeleteServlet extends HttpServlet {

	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			String filepath = (String) session.getAttribute("filepath");
			// 得到要下载的文件名
			String fileName = request.getParameter("filename");
			System.out.println("将要删除：" + fileName);
			// 查询数据库
			System.out.println("在数据库寻找文件");
			FileDaoImpl FileDaoImpl = new FileDaoImpl();
			Vector<FileMessage> files = FileDaoImpl.findFilesByPathAndUser(filepath, userName);
			FileHandleBizImpl filehandle = new FileHandleBizImpl();
			FileMessage thefile = filehandle.searchFile(files, fileName);
			// 获取文件uuid
			String fileuuid = thefile.getUuidName();
			System.out.println("文件uuid：" + fileuuid);
			System.out.println("在服务器寻找文件");
			App.getCosClient();
			// 获取@前面的内容，本处特指qq号，用于指向存储桶
			String bucketName = userName.substring(0, userName.lastIndexOf("@"));
			App.deleteFile(fileuuid, bucketName);
			System.out.println("删除成功");
			// 将文件从数据库删除
			System.out.println("从数据库删除" + fileuuid);
			FileDaoImpl.delFileByUuidName(fileuuid);
			String message = "删除成功";
			request.setAttribute("error", message);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "文件夹删除失败");
		}
		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}

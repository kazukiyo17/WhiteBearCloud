package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.UUID;
import dao.impl.*;
import entity.*;
import biz.App;

/**
 * @author: 林源（主）、冯书逸
 * @className: UploadServlet
 * @packageName: servlet
 * @description: 用于上传文件
 **/

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");

			// 获取用户名
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			String filepath = (String) session.getAttribute("filepath");
			// 获取上传的文件
			Part part = request.getPart("file");
			// 获取请求的信息
			String nametmp = part.getHeader("content-disposition");

			// 此name会出现中文乱码问题，尚不清楚原理，该用nametemp后无此问题
			// String name = new String(nametmp.getBytes(), "utf8");
			System.out.println("请求的信息还是分阶段入伙" + nametmp);// 测试使用
			System.out.println("测试name：" + nametmp + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

			// 获取文件名
			int tmp1 = nametmp.lastIndexOf("=") + 2;
			int tmp2 = nametmp.lastIndexOf(".");
			String filename = nametmp.substring(tmp1, tmp2);
			System.out.println(filename);// 测试使用

			// 获取上传文件的目录
			String root = request.getServletContext().getRealPath("/upload");
			File fileadd = new File(root);
			if (!fileadd.exists()) {
				fileadd.mkdir();
			}
			System.out.println("测试上传文件的路径：" + root);
			// 获取文件类型
			String filetype = nametmp.substring(nametmp.lastIndexOf(".") + 1, nametmp.length() - 1);
			System.out.println("测试文件类型：" + filetype);
			// 获取上传文件时的系统时间
			Timestamp now_time = new Timestamp(System.currentTimeMillis());
			// uuid
			String uuid = UUID.randomUUID().toString();
			// 获取文件大小
			long filesize = part.getSize();
			// 上传目录
			String newfilename = root + "\\" + uuid + "." + filetype;
			System.out.println("测试产生新的文件名：" + newfilename);
			// 上传文件到指定目录，不想上传文件就不调用这个
			part.write(newfilename);

			File thefile = new File(newfilename);
			if (thefile.exists()) {
				// 将文件信息写到数据库
				System.out.println("文件写入数据库");
				FileDaoImpl fileDaoImpl = new FileDaoImpl();

				// 这里的userName实质上是包含@后内容的account
				FileMessage file = new FileMessage(filename, uuid, now_time, filetype, filepath, userName, filesize);
				System.out.println("用户名" + userName);
				fileDaoImpl.addFile(file);
				App.getCosClient();
				// 获取@前面的内容，本处特指qq号，用于指向存储桶
				String bucketName = userName.substring(0, userName.lastIndexOf("@"));

				App.uploadFile(uuid, root + "/" + uuid + "." + filetype, bucketName);// 前端返回的useName实际是account
			}
			File delfile = new File(root + "/" + uuid + "." + filetype);
			delfile.delete();
			request.setAttribute("message", "上传文件成功");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "上传文件失败");

		}
		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlet;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.impl.LoginBizImpl;
import dao.impl.UserDaoImpl;
import entity.User;
import util.MyMD5Util;

/**
 * @author: 李旭芸
 * @className: LoginServlet
 * @packageName: servlet
 * @description: 登录
 **/

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserDaoImpl usrdao=new UserDaoImpl();

	public LoginServlet() {
		// TODO Auto-generated constructor stub
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 这里的userName实际上是指数据库中的account
		String userName = request.getParameter("userName");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^" + userName + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^&");
		String pwd = request.getParameter("pwd");

		// 生成登录密钥
		String key = MyMD5Util.encrypt(pwd);

		System.out.println("userName:" + userName);
		System.out.println("password:" + pwd);

		LoginBizImpl loginBiz = new LoginBizImpl();
		User user = loginBiz.login(userName, key);

		String uploadFilePath1 = this.getServletContext().getRealPath("/WEB-INF/path.txt");
		File fff = new File(uploadFilePath1);
		if (!fff.exists()) {
			fff.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(uploadFilePath1);
		fileWriter.write("");
		fileWriter.flush();
		fileWriter.close();

		if (user == null) {
			request.setAttribute("message", "用户名或密码错误");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			// cookie
			String	trueusername=usrdao.findUserByAccount(userName).getUserName();
			request.getSession().setAttribute("name", userName);
			request.getSession().setAttribute("trueusername", trueusername);
			System.out.println("用户名密码正确，添加cookie");
			Cookie cookie = new Cookie("sso", userName);
			cookie.setMaxAge(30 * 60);
			response.addCookie(cookie);
			request.getSession().setAttribute("name", userName);
			request.getRequestDispatcher("FileListServlet").forward(request, response);
		}
	}

}
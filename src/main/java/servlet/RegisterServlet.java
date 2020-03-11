package servlet;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.UserDaoImpl;
import util.MyMD5Util;
import biz.App;

/**
 * @author: 冯书逸
 * @className: RegisterServlet
 * @packageName: servlet
 * @description: 注册
 **/
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		// 获取session中的验证码
		String sessionCode = (String) request.getSession().getAttribute("code");
		System.out.println(sessionCode);

		if (sessionCode != null) {
			// 获取页面提交的验证码
			String inputCode = request.getParameter("code");
			System.out.println("页面提交的验证码:" + inputCode);

			if (sessionCode.toLowerCase().equals(inputCode.toLowerCase())) {

				String account = request.getParameter("signupAccount");
				String username = request.getParameter("signupUsername");
				String password = request.getParameter("signupPwd");

				// 对用户的密码进行加盐加密
				String key = MyMD5Util.encrypt(password);
				System.out.println("测试加盐加密功能：" + key);

				System.out.println("页面提交:" + account + "\t" + username + "\t" + password);

				// 测试java默认字符编码
				System.out.println(Charset.defaultCharset());

				// 使用邮箱名（@前的内容）作为桶名
				String bucketName = account.substring(0, account.lastIndexOf("@"));
				System.out.println("\n\n\n" + bucketName + "\n\n\n");
				// 验证成功，跳转成功页面
				UserDaoImpl userdao = new UserDaoImpl();
				if (userdao.addUser(username, key, account) != 0) {

					System.out.println("注册成功，添加cookie");
					// request.setAttribute("message", "注册成功");
					System.out.println("注册成功，添加123");
					App.createBucket(bucketName);
					// System.out.println("caocaocaocaocoacoa艹");
					request.getSession().setAttribute("trueusername", username);
					request.getSession().setAttribute("name", account);
					Cookie cookie = new Cookie("sso", account);
					cookie.setMaxAge(30 * 60);
					response.addCookie(cookie);
					request.setAttribute("userName", account);
					request.getRequestDispatcher("FileListServlet").forward(request, response);
				} else {
					// 验证失败
					// request.getRequestDispatcher("fail.jsp").forward(request, response);
				}
			}

			else {
				// 验证失败
				
				request.setAttribute("message", "注册失败");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			// 移除session中的验证码
			request.removeAttribute("code");
		}

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
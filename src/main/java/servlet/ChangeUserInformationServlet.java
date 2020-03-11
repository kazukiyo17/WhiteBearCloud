package servlet;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.impl.UserDaoImpl;
import util.MyMD5Util;

/**
 * @author :冯书逸
 * @description: 修改用户信息
 * @lastmodifedtime：2019年9月10日
 */
@WebServlet("/ChangeUserInformationServlet")
public class ChangeUserInformationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        // 获取session中的验证码
        HttpSession session = request.getSession();
        String sessionCode = (String) request.getSession().getAttribute("code");
        System.out.println(sessionCode);

        if(sessionCode != null) {
            //  获取页面提交的验证码
            String inputCode = request.getParameter("code");
            System.out.println("页面提交的验证码:" + inputCode);

            if (sessionCode.toLowerCase().equals(inputCode.toLowerCase())) {

                String new_password = request.getParameter("uploadPwd");
                String new_username = request.getParameter("uploadUsername");
                String account=request.getParameter("updateAccount");
                System.out.println("页面提交:" +"\t"+new_username+"\t"+new_password);
                
				// 对用户的密码进行加盐加密
				String key = MyMD5Util.encrypt(new_password);
				System.out.println("测试加盐加密功能：" + key);


                //测试java默认字符编码
                System.out.println(Charset.defaultCharset());

                //  验证成功，跳转成功页面
                UserDaoImpl userdao = new UserDaoImpl();
                if(userdao.updateUser(new_username, key, account)!=0) {
                    System.out.println("修改成功，添加cookie");
                    //request.setAttribute("message", "注册成功");
                    System.out.println("修改成功，添加123");
                    //App.createBucket(bucketName);
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                    //清除cookie
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("name")) {
                                cookie.setValue(null);
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                            }
                        }
                    }
                    //登出
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                }
                else {
                    //  验证失败
                    //request.getRequestDispatcher("fail.jsp").forward(request, response);
                }
            }

            else {
                //  验证失败
                request.setAttribute("message", "注册失败");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            //  移除session中的验证码
            request.removeAttribute("code");
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        doGet(request, response);
    }

}
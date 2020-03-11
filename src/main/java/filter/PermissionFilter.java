package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 李旭芸
 * @className: PermissionFilter
 * @packageName: filter
 * @description: 过滤器类
 **/

@WebFilter("/PermissionFilter")
public class PermissionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public PermissionFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		String url = httpReq.getRequestURI();//获得请求的url
		System.out.println("请求的url:" + url);
		//判断是否是登录页
		if (!url.substring(url.length()-9).equals("index.jsp")&&
				!url.substring(url.length()-15).equals("RegisterServlet")&&
				!url.substring(url.length()-12).equals("LoginServlet")) {
			//登录是否成功
			if (!isLogin(httpReq)) {
				System.out.println("没有登录，被拦截");
				httpReq.getSession().setAttribute("message", "未登录");
				httpResp.sendRedirect("index.jsp");
			}
		}
		chain.doFilter(request, response);
	}

	boolean isLogin(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String username = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sso")) {
					username = cookie.getValue();
				}
			}
		}
		if (username.equals(""))
			return false;
		else
			return true;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

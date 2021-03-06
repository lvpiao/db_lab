package administrator;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DBConnctionException;
import exceptions.MyException;
import javabeans.User;
import utils.RequestUtil;

/**
 * Servlet implementation class FlushCache
 */
@WebServlet("/admin/FlushCache")
public class FlushCache extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FlushCache()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try (OutputStream os = response.getOutputStream())
		{
			RequestUtil rqTool = RequestUtil.getRequestTool(request);
			// 未登录
			if (!rqTool.isOnline())
			{
				os.write("未登录".getBytes());
				return;
			}
			User user = rqTool.getUser();
			try
			{
				if (AdminService.FlushCache(user))
				{
					os.write("success".getBytes("utf-8"));
				} else
				{
					os.write("error".getBytes("utf-8"));
				}
			} catch (MyException e)
			{
				e.printStackTrace();
			} catch (DBConnctionException e)
			{
				os.write(e.getErrorInfo().getBytes("utf-8"));
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

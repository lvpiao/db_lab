package module_user_operare;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.DBConnctionException;
import javabeans.User;
import services.UserService;

/**
 * Servlet implementation class Register
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		UserService service = null;
		try
		{
			service = new UserService();
		} catch (DBConnctionException e1)
		{
			request.setAttribute("exception", e1);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			e1.printStackTrace();
		}
		try (OutputStream os = response.getOutputStream())
		{
			if (service.idExisted(userId))
			{
				os.write("该账户已被注册".getBytes("utf-8"));
				return;
			}
			if (userId != null && pwd != null && userId.length() > 0 && pwd.length() > 5)
			{
				User user =  new User(userId,pwd,userId);
				if (service.insertUser(user))
				{
					HttpSession session = request.getSession();
					user.setPwd(null);
					session.setAttribute("user", user);
					os.write("success".getBytes("utf-8"));
				} else
				{
					os.write("error".getBytes("utf-8"));
				}
			} else
			{
				os.write("error".getBytes("utf-8"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (service != null)
				service.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}

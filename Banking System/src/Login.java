import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;


@WebServlet({ "/Login", "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Statement st=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid=request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		try {
			Connection con=DBconnection.getConnection();
			st=con.createStatement();
			String sql= "select * from user_pass where uid='"+uid+"' and pwd='"+pwd+"'";
			ResultSet rs=st.executeQuery(sql);
			
			if(rs.next()) {
				RequestDispatcher rd= request.getRequestDispatcher("Banking.html");
				rd.forward(request, response);
			}
			else {
				response.setContentType("text/html");
				PrintWriter out =response.getWriter();
				out.println("<p style=color:black;text-align:center;>Invalid UserName/Password</p> ");
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response); 
	}

}

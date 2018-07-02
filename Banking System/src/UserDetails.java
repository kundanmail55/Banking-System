import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;



@WebServlet({ "/UserDetails", "/user", "/userRegistration" })
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Statement st=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("uname");
		String dob=request.getParameter("dob");
		String address=request.getParameter("add");
		String email=request.getParameter("email");
		String type=request.getParameter("type");
		try {
			Connection con=DBconnection.getConnection();
			st=con.createStatement();
			PrintWriter out =response.getWriter();
			int i=st.executeUpdate("insert into account_details values('"+uname+"', '"+dob+"', '"+address+"', '"+email+"', '"+type+"')");
			if(i>0) {
				response.setContentType("text/html");
				out.println("<p style=color:black;text-align:center;>Account Created Successfully</p> ");
				RequestDispatcher rd= request.getRequestDispatcher("account_creation.html");
				rd.include(request, response);	
			}
			else {
				response.setContentType("text/html");
				out.println("<p style=color:black;text-align:center;>User account not Created</p> ");
				RequestDispatcher rd=request.getRequestDispatcher("account_creation.html");
				rd.include(request, response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		System.out.print("hello");
	}

}

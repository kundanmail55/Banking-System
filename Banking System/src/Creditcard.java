import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet({ "/Creditcard", "/credit", "/approve" })
public class Creditcard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Statement st=null;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String c_no=request.getParameter("c_no");
		String cvv=request.getParameter("cvv");
		String amount=request.getParameter("amount");
		Integer amou=Integer.parseInt(amount);
		try {
			Connection con=DBconnection.getConnection();
			st=con.createStatement();
			PrintWriter out =response.getWriter();
			String sql= "select * from credit_card where name='"+name+"' and c_no='"+c_no+"' and cvv='"+cvv+"'";
			ResultSet rs=st.executeQuery(sql);
			if((rs.next()) && (amou<=100000)) {
				response.setContentType("text/html");
				out.println("<p style=color:black;text-align:center;>Approved!!!</p> ");
				RequestDispatcher rd= request.getRequestDispatcher("credit_card.html");
				rd.include(request, response);
			}
			else {
				response.setContentType("text/html");
				out.println("<p style=color:black;text-align:center;>Rejected!!!</p> ");
				RequestDispatcher rd=request.getRequestDispatcher("credit_card.html");
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

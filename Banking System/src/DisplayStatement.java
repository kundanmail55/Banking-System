import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet({ "/DisplayStatement", "/display" })
public class DisplayStatement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		PrintWriter out =response.getWriter();
		try{
			Connection con=DBconnection.getConnection();
			PreparedStatement ps= con.prepareStatement("select * from statement where Date between '"+start+"' and '"+end+"'");
			ResultSet rs=ps.executeQuery();
			RequestDispatcher rd= request.getRequestDispatcher("statement.html");
			rd.include(request, response);
			int i=1;
			String str="<table border=1><tr><th>Sl.No</th><th>Date</th><th>Description</th><th>Cheque No</th><th>Withdraw</th><th>Deposit</th><th>Available Balance</th></tr>";
			while(rs.next()) {
				str +="<tr><td>"+i+++"</td><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td></tr>";
				}
			str +="</table>";
			out.println(str);

		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

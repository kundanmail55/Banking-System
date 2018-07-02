import java.io.*;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


	 
@WebServlet({ "/Transaction", "/transaction", "/tran" })
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;


public static java.sql.Date getCurrentJavaSqlDate() 
{
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}
		private static Statement st=null;
		PreparedStatement pstmt = null;
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out =response.getWriter();
			String acc=request.getParameter("acct_no");
			Integer acct_no=Integer.parseInt(acc);
			
			String amoun=request.getParameter("amount");
			Integer amount=Integer.parseInt(amoun);
			
			String action=request.getParameter("action");
			try {
			Connection con=DBconnection.getConnection();
			st=con.createStatement();
			String sql="SELECT Balance from statement";
			ResultSet rs=st.executeQuery(sql);
			
			    if("Credit To".equals(action)) {
			    	if(rs.next()) {
						double c=rs.getDouble("Balance");
					    double b=c-amount;
					    
					   String query = "insert into statement(Date, Description, Cheque, Withdraw, Balance) values( ?, ?, ?, ?, ?)";
					   pstmt = con.prepareStatement(query);
					   java.sql.Date date = getCurrentJavaSqlDate();
			        pstmt.setDate(1, date);
			    	pstmt.setString(2, action);
			    	pstmt.setInt(3, acct_no);
			       	pstmt.setDouble(4, amount);
			       	//pstmt.setDouble(5, 0);
			       	pstmt.setDouble(5, b);
			    	pstmt.executeUpdate();
			    	}
				    response.setContentType("text/html");
					out.println("<p style=color:black;text-align:center;>Credited Successfully</p> ");
					RequestDispatcher rd= request.getRequestDispatcher("perform_transaction.html");
					rd.include(request, response);
					
				}
			    
				else if("Debit From".equals(action)) {
					if(rs.next()) {
						double c=rs.getDouble("Balance");
					    double b=c+amount;
					    
					    String query = "insert into statement(Date, Description, Cheque, Withdraw, Balance) values( ?, ?, ?, ?, ?)";
						   pstmt = con.prepareStatement(query);
						   java.sql.Date date = getCurrentJavaSqlDate();
				        pstmt.setDate(1, date);
				    	pstmt.setString(2, action);
				    	pstmt.setInt(3, acct_no);
				    	//pstmt.setDouble(4, Null);
				       	pstmt.setDouble(4, amount);
				       	pstmt.setDouble(5, b);
				    	pstmt.executeUpdate();
				    	}
				    response.setContentType("text/html");
					out.println("<p style=color:black;text-align:center;>Debited Successfully</p> ");
					RequestDispatcher rd= request.getRequestDispatcher("perform_transaction.html");
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

package forwarding;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	public void init(){
		try {
			ServletContext sc=getServletContext();
			String s1=sc.getInitParameter("driver");
			String s2=sc.getInitParameter("url");
			String s3=sc.getInitParameter("name");
			String s4=sc.getInitParameter("password");
			
			Class.forName(s1);
			con=DriverManager.getConnection(s2,s3, s4);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String s1=request.getParameter("uname");
			String  s2=request.getParameter("pword");
			PreparedStatement ps=con.prepareStatement("select * from uinfo where uname=? and password=?");
			ps.setString(1, s1);
			ps.setString(2, s2);
			ResultSet rs=ps.executeQuery();
			PrintWriter pw=response.getWriter();
			if(rs.next()) {
				
				RequestDispatcher rd=request.getRequestDispatcher("/welcome");
				rd.forward(request,response);
			}
			else {
				pw.println("Invalid");
			    RequestDispatcher rd=request.getRequestDispatcher("/login.html");
			    rd.include(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

package bus.registeration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterationServlet
 */
@WebServlet("/register")
public class RegisterationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String Reupwd=request.getParameter("re_pass");
		RequestDispatcher dispatcher =null;
		Connection con = null;
		if(uname==null||uname.equals("")) {
			request.setAttribute("status", "invalidName");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		if(uemail==null||uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		if(upwd==null||upwd.equals("")) {
			request.setAttribute("status", "invalidUpwd");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}else if(!upwd.equals(Reupwd)) {
			request.setAttribute("status", "invalidConfirmPassword");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		
		try{
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mangalore_bus_routes?useSSL=false","root","krathi@123");
		    PreparedStatement pst=con.prepareStatement("insert into user(uname,upwd,uemail) values(?,?,?)");
		    pst.setString(1,uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			
			int rowcount=pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowcount>0) {
				request.setAttribute("status", "success");
				
			}else {
				request.setAttribute("status", "failed");
				
			}
			dispatcher.forward(request, response);
			
			
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

}

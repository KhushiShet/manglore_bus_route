package bus.registeration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchBus
 */
@WebServlet("/Search")
public class SearchBus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out= response.getWriter();
		
		String endingPlace=request.getParameter("endingPlace");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mangalore_bus_routes?useUnicode=true&characterEncoding=UTF-8","root","krathi@123");
		    PreparedStatement ps=con.prepareStatement("select * from routes where destination =  ?" );
		    
		    
		    
		    ps.setString(1,endingPlace);
		    
		    out.print("<table  align=center width=90% border=1 solid brown>");
		    		out.print("<caption><h1><b>Here is Your Selected Bus Route!!</b></h1></caption>");
		    		
		    		ResultSet rs = ps.executeQuery();
		    		
		    		ResultSetMetaData rsmd = rs.getMetaData();
		    		int totalColumn= rsmd.getColumnCount();
		    		out.print("<tr>");
		    		for(int i=1;i<=totalColumn;i++) {
		    			out.print("<th>"+rsmd.getColumnName(i)+"</th>");
		    		}
		    		out.print("</tr>");
		    		while(rs.next()) {
		    			out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getInt(5)+"</td></tr>");
		    		}
		    		out.print("</table>");
		    		
		    
			
		}catch (Exception e) {
			out.print(e);
		}
	}

}


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author TATARAO
 * @since JdbcConPool
 * @version java 1.6
 */
public class ConPoolServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
      String tabname=request.getParameter("tab");
        try {
            Connection con=makeConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from "+tabname);
            
            ResultSetMetaData rsmd=rs.getMetaData();
            int columncount=rsmd.getColumnCount();
            
            out.println("<html><body bgcolor=#fffffa9>");
            out.println("<center><h3>The Details Of The Table "+tabname);
            out.println("<br><br><table cellspacing=1 bgcolor=#00ff00 width=986px>");
            out.println("<tr bgcolor=#ffffa9>");
            for(int col=1;col<columncount;col++)
            out.println("<th>"+rsmd.getColumnLabel(col)+"&nbsp;&nbsp;</th>");
            out.println("</tr>");
            while(rs.next())
            {
                out.println("<tr bgcolor=#ffffa9>");
                for(int i=1;i<columncount;i++)
                    out.println("<td align=center>"+rs.getString(i)+"</td>");
                    out.println("</tr>");
            }
            out.println("</table><br>");
            out.println("To view another table <b><a href=index.jsp style=text-decoration:none;color:green;>Click Here</a></b>");
            out.println("</center></body></html>");
        }
        catch(Exception e)
        {
            out.println("<html><body bgcolor=#ffffa9><ceter><br><br>");
            out.println("<h3>Table Doesn't Exit</h3>");
            out.println("<br><a href=index.jsp>Click here</a>");
            out.println("</body></html>");
        }
    }
    public Connection makeConnection()
    {
        Connection con=null;
        try
        {
            InitialContext ic=new InitialContext();
            DataSource ds=(DataSource)ic.lookup("myjndi");
            con=ds.getConnection();
            
  /*          Class.forName("org.gjt.mm.mysql.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rao","root",""); */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}

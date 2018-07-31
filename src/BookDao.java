import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookDao {
public static int save(String callno,String name,String author,String publisher,int quantity){
	int status=0;
	try{
		Connection con=DB.getConnection();
		PreparedStatement ps=con.prepareStatement("insert into books(name,author,publisher,quantity) values(?,?,?,?)");
		ps.setString(1,name);
		ps.setString(2,author);
		ps.setString(3,publisher);
		ps.setInt(4,quantity);
		status=ps.executeUpdate();
		con.close();
	}catch(Exception e){System.out.println(e);}
	return status;
}
}

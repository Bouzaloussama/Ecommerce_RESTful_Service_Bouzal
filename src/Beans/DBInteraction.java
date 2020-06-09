package Beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class DBInteraction {

	
	static Connection conn;
	static Statement st;
	static java.lang.String url="jdbc:mysql://localhost:3306/oussama ?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	static ResultSet rs;
	
	public  static void connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(url,"root","root");
			st=conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static ResultSet Select(java.lang.String sql)
	{
		try {
			rs=st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int Update(java.lang.String sql)
	{
		int i=0;
		try {
			i=st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	public static void deconnect()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


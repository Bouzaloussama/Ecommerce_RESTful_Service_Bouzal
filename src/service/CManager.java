package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import Beans.Book;
import Beans.User;
import Beans.DBInteraction;


@Path("/Service_Manager")
public class CManager {
	
 //================= All Book Of db (Book or Packet) ===========
	@GET
	@Path("/AllBook/{db_book_Or_panier}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Book> AllBook(@PathParam(value = "db_book_Or_panier")String db) {
		ArrayList<Book> Books= new ArrayList<>();
		DBInteraction.connect();
		String sql = "select * from "+db;
		ResultSet rs = DBInteraction.Select(sql);
			
		try {
			while(rs.next())
			{					
				Book b= new Book(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(1));
				Books.add(b);					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return Books ;					
	}
	
 //================== Add Book =====================
	
	@POST
	@Path("/AddBook")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void AddBook(Book book){
		DBInteraction.connect();
		
		String sql="insert into book (name,auteur,prix) values('"+book.getBookName()+"','"+book.getAuteurName()+"','"+book.getPrix()+"')";
		DBInteraction.Update(sql);
		DBInteraction.deconnect();
		System.out.println("coté Server Add Book : BookName "+book.getBookName()+" AuteurName "+book.getAuteurName());
	}

 //================== Find Book =====================	
	@GET
	@Path("/FindBook/{nameBook}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Book> FindBook(@PathParam("nameBook")String nameBook){
		ArrayList<Book> Books= new ArrayList<>();
		DBInteraction.connect();
		String sql="select * from book where name like lower('"+nameBook+"%')";
		ResultSet rs = DBInteraction.Select(sql);
			
		try {
			while(rs.next())
			{					
				Book b= new Book(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(1));
				Books.add(b);					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return Books ;					
	}
	
 //================ Find Book by Id ===================	
	@GET
	@Path("/FindBookById/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Book FindBookById(@PathParam(value = "id")int id){
		DBInteraction.connect();
        String sql="select * from book where id='"+id+"'";
		Book c = null;
		ResultSet rs = DBInteraction.Select(sql);
		try {
			if(rs.next())
			{
				c = new Book();
				c.setId(id);
				c.setBookName( rs.getString(2));
				c.setAuteurName( rs.getString(3));
				c.setPrix( rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c ;
	}
	
		

	
//###############################  For Compete ####################################
	
	//================ Register ===================	
		@POST
		@Path("/Register")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void  Register(User compte) {
		DBInteraction.connect();
	
		String sql="insert into compte1 (name,last_name,tell,email,password) values('"+compte.getName()+"','"+compte.getLast_name()+"','"+compte.getTell()+"','"+compte.getEmail()+"','"+compte.getPassword()+"')";
		DBInteraction.Update(sql);
		DBInteraction.deconnect();
	}
	
	//================ Authentification ===================
		@GET
		@Path("/Authentificate/{log}/{pass}")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User Authentificate(@PathParam(value = "log")String log, @PathParam(value = "pass")String pass) {
			User c=null;
		DBInteraction.connect();
		String sql="select * from compte1 where email='"+log+"' and password='"+pass+"'";
		
		ResultSet rs = DBInteraction.Select(sql);
		try {
			if(rs.next())
			{
				c=new User();
				c.setName(rs.getString(2));
				c.setLast_name(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
		
	//============= FindType ( Admin or User ) =============	
		@GET
		@Path("/FindType/{log}/{pass}")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String FindType(@PathParam(value = "log")String log, @PathParam(value = "pass")String pass) {
		DBInteraction.connect();
		String sql="select TypeUser from compte1 where email='"+log+"' and password='"+pass+"'";
		String c = "" ;
		ResultSet rs = DBInteraction.Select(sql);
		try {
			if(rs.next())
			{
				
				c = rs.getString(6);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	
	
//################################### For Packet #########################################
		
		
   //=================  AddBookToPanier  =====================		
		@POST
		@Path("/AddBookToPanier")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		//@Produces(MediaType.TEXT_PLAIN)
	public void AddBookToPanier(Book book) {
		DBInteraction.connect();
		int nb=1;

		String sql="insert into panier (name,auteur,prix,compt) values('"+book.getBookName()+"','"+book.getAuteurName()+"','"+book.getPrix()+"','"+nb+"')";
		DBInteraction.Update(sql);
		DBInteraction.deconnect();
	}
		
  //=================  Delete Book From Packet  =====================		
		@DELETE
		@Path("/DeletBookPanier/{id}")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void DeletBookPanier(@PathParam("id")int id) {
		DBInteraction.connect();
		System.out.println("coté server packet : id -> "+id);////////////////
		String sql="delete from panier where id='"+id+"'";
		DBInteraction.Update(sql);
		DBInteraction.deconnect();
	}
		
  //=================  TotalPrixe  =====================		
		@GET
		@Path("/TotalPrixe")
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public int TotalPrixe(){
		DBInteraction.connect();
        String sql="select SUM(prix) from panier";
		int c = 0;
		ResultSet rs = DBInteraction.Select(sql);
		try {
			if(rs.next())
			{
				
				c = rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c ;
	 }

}

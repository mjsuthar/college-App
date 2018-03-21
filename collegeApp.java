package com.mj.jdbcproject;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class project1 extends JFrame implements ActionListener{
	
	private JLabel lno,lname,laddr;
	private JTextField tno,tname,taddr;
	private JButton jinsert, jdelete, jupdate, jview;
	private Statement st;
	private ResultSet rs;
	private Connection con;
//	private CallableStatement cs;
	PreparedStatement ps1,ps2,ps3;
	
	public project1() {
		System.out.println("project 0-params constructor");
		setTitle("all in one");
		setSize(200,300);
		setLayout(new FlowLayout());
		setBackground(Color.cyan);
		
		lno=new JLabel("sno");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		
		lname=new JLabel("Name");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		laddr=new JLabel("Address");
		add(laddr);
		taddr=new JTextField(10);
		add(taddr);
		
		jinsert=new JButton("Insert");
		jinsert.addActionListener(this);
		add(jinsert);
		
		jupdate=new JButton("Upadate");
		jupdate.addActionListener(this);
		add(jupdate);
		
		jdelete=new JButton("Delete");
		jdelete.addActionListener(this);
		add(jdelete);
		
		jview=new JButton("View");
		jview.addActionListener(this);
		add(jview);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("window closing");
				try{
					if(ps1!=null)
					ps1.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(ps2!=null)
					ps2.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(ps3!=null)
					ps3.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(rs!=null)
					rs.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(st!=null)
					st.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(con!=null)
					con.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				
			}
		});
		
		loadItems();
		
	}//constructor
	
	private void loadItems() {
		System.out.println("load Items()");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manoj");
			if(con!=null) {
			ps1=con.prepareStatement("insert into student2 values(?,?,?)");
			ps2=con.prepareStatement("delete from student2 where sno=?");
			ps3=con.prepareStatement("update student2 set name = ?,addr=? where sno=?");
			st=con.createStatement();
			
			}
		
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		int ino=0,result=0,uno=0,uresult=0,dno=0,dresult=0;
		String name=null,addr=null;
		String uname=null,uaddr=null;
		System.out.println("Actionperformed(-)");
		
		if(ae.getSource()==jinsert) {
			System.out.println("insert btn is Clicked");
			try {
				ino=Integer.parseInt(tno.getText());
				name=tname.getText();
				addr=taddr.getText();
				//set values for insert btn
				ps1.setInt(1, ino);
				ps1.setString(2, name);
				ps1.setString(3, addr);
				result=ps1.executeUpdate();
				if(result==1) {
					System.out.println("insertion succesful");
				}else {
					System.out.println("insertion failed");
				}
			}catch(SQLException se) {
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(ae.getSource()==jupdate) {
			System.out.println("Update btn is Clicked");
			try {
				uno=Integer.parseInt(tno.getText());
				uname=tname.getText();
				uaddr=taddr.getText();
				//set values for update btn
				ps3.setString(1,uname);
				ps3.setString(2,uaddr);
				ps3.setInt(3,uno);
				
				uresult=ps3.executeUpdate();
				if(uresult==1) {
					System.out.println("update succesfuly");
				}else {
					System.out.println("updation failed");
				}
			}catch(SQLException se) {
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(ae.getSource()==jdelete) {
			System.out.println("Delete btn is Clicked");
			try {
					dno=Integer.parseInt(tno.getText());
					
					ps2.setLong(1, dno);
					dresult=ps2.executeUpdate();
					
					if(dresult==1) {
						System.out.println("delete succesfully");
					}else {
						System.out.println("delete query failed");
					}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("View btn is Clicked\n");
			try {
				rs=st.executeQuery("select * from student2");
				
				if(rs!=null) {
					while(rs.next()) {
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
					}
				}
					
			}catch(SQLException se) {
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("main(-) method");
		new project1();

	}

}

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

public class miniproject extends JFrame implements ActionListener{
	private JLabel lno,lname,lm1,lm2,lm3,lres;
	private JTextField tname,tm1,tm2,tm3,tres;
	private JButton bdetails,bresult;
	private JComboBox tno;
	private Connection con;
	private Statement st;
	private ResultSet rs1,rs2;
	private PreparedStatement ps;
	private CallableStatement cs;
	
	public miniproject() {
		System.out.println("All-Student 0 param constructor");
		setTitle("mini project");
		setSize(200,300);
		setLayout(new FlowLayout());
		setBackground(Color.CYAN);
		
		lno=new JLabel("Student Id");
		add(lno);
		tno=new JComboBox();
		add(tno);
		
		bdetails=new JButton("details");
		bdetails.addActionListener(this); //tno
		add(bdetails);
		
		lname=new JLabel("Name");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		lm1=new JLabel("marks1");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		
		lm2=new JLabel("marks2");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		
		lm3=new JLabel("marks3");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		
		bresult=new JButton("Result");
		bresult.addActionListener(this);//tno
		add(bresult);
		
		lres=new JLabel("Result");
		add(lres);
		tres=new JTextField(10);
		add(tres);
		
		tname.setEditable(false); tm1.setEditable(false); tm2.setEditable(false); tm3.setEditable(false); tres.setEditable(false);		
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("window closing");
				try{
					if(rs1!=null)
					rs1.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(rs2!=null)
					rs2.close();
				}	
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(cs!=null)
					cs.close();
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
					if(ps!=null)
					ps.close();
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
	
	private void loadItems(){
		System.out.println("load Items()");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manoj");
			
			if(con!=null)
				st=con.createStatement();
			
			if(st!=null)
				rs1=st.executeQuery("select sno from student");
			if(rs1!=null){
				while(rs1.next()) {
					tno.addItem(rs1.getInt(1));
				}
			}
			
			if(con!=null)
				ps=con.prepareStatement("select *from student where sno=?");
			
			if(con!=null){
				cs=con.prepareCall("{call find_pass_fail(?,?,?,?)}");
				cs.registerOutParameter(4,Types.VARCHAR);
			}//if ps
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
	}//loaditems
	
	public void actionPerformed(ActionEvent ae) {
		int m1=0,m2=0,m3=0;
		String result=null;
		System.out.println("actionPerformed");
		
		if(ae.getSource()==bdetails){
			System.out.println("Details btn is clicked");
			try {
				int no=(Integer)tno.getSelectedItem();
				
				if(ps!=null) {
					ps.setInt(1, no);
					rs2=ps.executeQuery();
				}
				if(rs2!=null) {
					if(rs2.next()) {
						tname.setText(rs2.getString(2));
						tm1.setText(rs2.getString(3));
						tm2.setText(rs2.getString(4));
						tm3.setText(rs2.getString(5));
					}
				}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//if
		else {
			System.out.println("Result btn is Clicked");
			try {
				m1=Integer.parseInt(tm1.getText());
				m2=Integer.parseInt(tm2.getText());
				m3=Integer.parseInt(tm3.getText());
				
				if(cs!=null) {
					cs.setInt(1, m1); cs.setInt(2, m2); cs.setInt(3, m3);
					
					cs.execute();
					result=cs.getString(4);
					tres.setText(result);
				}//if cs
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//else
	}//action
	
	public static void main(String[] args) {
		System.out.println("main(-) class");
		new miniproject();
	}
}
//class
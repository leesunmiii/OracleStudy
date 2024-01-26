package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TriggerDAO {
	private Connection conn;
	private PreparedStatement ps; //sql문장을 전송하는 역할
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	public TriggerDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ojdbcv파일8 => 11g 이상
			//
			//
		}catch(Exception ex) {}
	}
	// 오라클 연결
	public void getConnetcion()                                                                                              
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// conn hr/happy
		}catch(Exception ex) {}
	}
	// 오라클 닫기
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//기능 설정
	public void inputInsert(int bun,int account,int price)
	{
		try
		{
			getConnetcion();
			String sql="INSERT INTO 입고 VALUES(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setInt(2, account);
			ps.setInt(3, price);
			ps.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
	}
	
	public void outputInsert(int bun,int account,int price)
	{
		try
		{
			getConnetcion();
			String sql="INSERT INTO 입고 VALUES(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setInt(2, account);
			ps.setInt(3, price);
			ps.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
	}
	
	public void remainData()
	{
		try
		{
			getConnetcion();
			String sql="SELECT * FROM 재고";
			ps=conn.prepareStatement(sql);
			//결과값 받기
			ResultSet rs=ps.executeQuery();
			System.out.println("품번 수량 누적금액");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "
						+rs.getInt(2)+" "
						+ rs.getInt(3);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
	}
	public static void main(String[] args) {
		TriggerDAO dao=new TriggerDAO();
		dao.inputInsert(100, 3, 1500);
		dao.outputInsert(100, 3, 1500);
		dao.remainData();
	}
}

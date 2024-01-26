package com.sist.dao;
import java.util.*;
import java.util.concurrent.Callable;

import oracle.jdbc.OracleTypes;

import java.sql.*;
public class EmpDAO {
	//연결
	private Connection conn;
	//함수 호출(프로시저 호출)
	private CallableStatement cs;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public EmpDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	public void disConnection()
	{
		try
		{
			if(cs!=null) cs.close();
			if(conn!=null) conn.close();
			
		}catch(Exception ex) {}
	}
	
	public List<EmpVO> empAllData()
	{
		List<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			getConnection();
			String sql="{CALL empAllData(?)}";
			cs=conn.prepareCall(sql);
			cs.registerOutParameter(1,OracleTypes.CURSOR);
			cs.executeQuery(); //실행해라 
			ResultSet rs=(ResultSet)cs.getObject(1);
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				list.add(vo);
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
}

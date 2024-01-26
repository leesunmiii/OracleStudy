package com.sist.main;
import java.util.*;

import com.sist.dao.*;

import java.sql.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmpDAO dao=new EmpDAO();
		List<EmpVO> list=dao.empAllData();
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "
								+vo.getEname()+" "
								+vo.getJob()+" "
								+vo.getMgr()+" "
								+vo.getHiredate());
		}
	}

}

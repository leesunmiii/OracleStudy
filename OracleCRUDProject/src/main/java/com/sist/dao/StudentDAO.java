package com.sist.dao;
import java.util.*;
import java.sql.*;
public class StudentDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public StudentDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex){}
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
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			
			//exit
		}catch(Exception ex) {}
	}
	
	
	// 기능
	//	1. 목록 => SELECT => 페이징 (자바, 오라클=인라인뷰)
	// => 오라클만 연동 => 출력 => main, 브라우저로 전송
	// => 데이터를 모아서 전송 (목록) ArrayList
	public List<StudentVO> stdListData()
	{
		List<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			// 1. 오라클 연결
			getConnection();
			// 2. SQL문장을 만든다 (오라클로 전송)
			String sql="SELECT hakbun,name,kor,eng,math,"
						+"kor+eng+math,ROUND((kor+eng+math)/3.0,2),"
						+"RANK() OVER(ORDER BY(kor+eng+math) DESC),"
						+"TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
						+"FROM student";
			// RANK()는 자동 정렬 => ORDER BY 다시 쓸 필요 없다
			// => 웹은 모든 데이터가 공유되어있음 => 데이터는 반드시 오라클에 저장된 상태이어야함
			// => VueJS (Vuex) / ReactJS (Redux)
			//       JSP			JSP	  ------ Spring
			// ======> 데이터가 없는 상태에선 VueJS, ReactJS 실행 못함
			//							--------------- 데이터 관리 프로그램
			// Back-End (SQL), Front-End (JSON/JSOUP)
			// 자바스크립트는  => VO : {} , List : [] => [{}, {}, {}...]
			// XML => JSON => Ajax(javascript and xml)
			
			//3. 오라클에 SQL전송
			ps=conn.prepareStatement(sql);
			//4. SQL 문장을 실행
			ResultSet rs=ps.executeQuery();
			while(rs.next()) // => next : 한줄씩 내려가며 데이터 읽으라는 뜻 , previous : 한줄씩 올라가며 데이터 읽으라는 뜻
			{
				// 데이터를 모아서 전송 => MainClass
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				// 인덱스 번호는 1번부터 시작
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				vo.setRank(rs.getInt(8));
				vo.setDbday(rs.getString(9));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			// 오류 처리
			ex.printStackTrace();
		}
		finally
		{
			// 오라클 닫기
			disConnection();
		}
		return list;
	}
	//	2. 추가 => INSERT => COMMIT (AutoCommit)
	public void stdInsert(StudentVO vo)
	{
		try
		{
			getConnection();
			/*String sql="INSERT INTO student(hakbun,name,kor,eng,math) "
					+"VALUES((SELECT MAX(hakbun)+1 FROM Student),'"
					+vo.getName()+"',"+vo.getKor()+","+vo.getEng()+","+vo.getMath()+")";
			System.out.println(sql);*/
			
			String sql="INSERT INTO student(hakbun,name,kor,eng,math) "
					+"VALUES((SELECT MAX(hakbun)+1 FROM Student),?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,vo.getName());
			ps.setInt(2, vo.getKor());
			ps.setInt(3, vo.getEng());
			ps.setInt(4, vo.getMath());
			// 데이터형 처리
			// setString ==> 데이터를 입력하면 ''을 자동으로 붙여줌
			// 실행
			ps.executeUpdate(); // 데이터베이스가 변동 =>
			//executeQuery에는 COMMIT이 없고 executeUpdate에는 COMMIT이 있슴
		}catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	//	3. 상세보기 => SELECT WHERE
	// 	   ------- 반드시 (Primary Key값을 전송)
	 public StudentVO stdDetailData(int hakbun) // hakbun이 primary key
	 {
		 StudentVO vo=new StudentVO();
		 try
		 {
			 getConnection();
			 String sql="SELECT hakbun,name,kor,eng,math+"
						+"kor+eng+math,ROUND((kor+eng+math)/3.0,2),"
						+"RANK() OVER(ORDER BY(kor+eng+math) DESC),"
						+"TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
						+"FROM student "
						+"WHERE hakbun="+hakbun; // 이 학번에 대한 것
			 
			 	ps=conn.prepareStatement(sql);
				//4. SQL 문장을 실행
				ResultSet rs=ps.executeQuery();
				
				rs.next();
				
				vo.setHakbun(rs.getInt(1));
				// 인덱스 번호는 1번부터 시작
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				vo.setRank(rs.getInt(8));
				vo.setDbday(rs.getString(9));
				
				rs.close();
				
		 }catch(Exception ex) 
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 disConnection();
		 }
		 return vo;
	 }
	 
	 
	 
	//	4. 수정 => UPDATE => COMMIT
	public StudentVO stdUpdateData(int hakbun)
	{
		StudentVO vo=new StudentVO();
		try
		{
			getConnection();
			String sql="SELECT hakbun,name,kor,eng,math "
					+"FROM student "
					+"WHERE hakbun="+hakbun;
			// 대댓글 / 딥변형
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next(); // 얘 없으면 오류남. 데이터가 출력한 첫번째 위치로 커서를 옮기고 다음으로 넘어가는 기능을 하는게 next
			//while(rs.next()) 쓸 때는 데이터가 여러개일때 다음다음 넘어가라고 쓴다. 커서 위치를 바꿔주는것
			
			vo.setHakbun(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setKor(rs.getInt(3));
			vo.setEng(rs.getInt(4));
			vo.setMath(rs.getInt(5));
			
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	
	
	public void stdUpdate(StudentVO vo)
	{
		try
		{
			getConnection();
			String sql="UPDATE student SET "
					+"name=?,kor=?,eng=?,math=? " // 물음표순서가 1,2,3,4,5
					+"WHERE hakbun=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setInt(2, vo.getKor());
			ps.setInt(3, vo.getEng());
			ps.setInt(4, vo.getMath());
			ps.setInt(5, vo.getHakbun());
			
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
	
	
	//	5. 삭제 => DELETE => COMMIT
	public void stdDelete(int hakbun)
	{
		try
		{
			getConnection();
			String sql="DELETE FROM student "
						+"WHERE hakbun="+hakbun;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			/*
			 * -------------- 다른 방법
			 * String sql="DELETE FROM student "+"WHERE hakbun="+?;
			   ps.setInt(1,hakbun);
			 */
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	
	 /*public static void main(String[] args) {
		StudentDAO dao=new StudentDAO();
		StudentVO vo=new StudentVO();
		vo.setHakbun(8);
		vo.setName("이산");
		vo.setKor(90);
		vo.setEng(90);
		vo.setMath(90);
		dao.stdInsert(vo);
	}*/
	
	
}

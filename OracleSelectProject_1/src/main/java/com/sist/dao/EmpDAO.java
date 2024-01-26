package com.sist.dao;
/*
 * 		연동 => 마지막 (은퇴) => 웹 프로그래머의 핵심
 * 		=> 90%
 * 		브라우저	=====	자바	===== 오라클
 * 				 요청	   SQL 전송  | SQL문장 실행
 * 			|	 전송 <======   결과값 받기
 * 			| 결과값 받아서 화면 출력 (HTML)
 * 		** 오라클 SQL 문장과 자바에서 전송하는 SQL문장이 다른 것이 있다 (95%는 동일, 5%는 다르다)
 * 																		----------
 * 																		LIKE가 약간 다르다
 * 																		=> 오라클 / MySQL
 * 		1) 연결(송수신) => 드라이버 설정
 * 						------- 클래스로 만들어져 있다
 * 		   Class.forName("Oracle.jdbc.OracleDriver")
 * 						  -------------------------
 * 							ojdbc8.jar 안에 들어있다
 * 		2) 오라클 연결
 * 			Connection conn=DriverManager.getConnection(URL,username,password)
 * 																hr		happy
 * 
 * 			=> conn hr/happy
 * 
 * 			URL을 줄 때는 앞에 jdbc:업체명:드라이버타입:@IP:PORT:데이터베이스명
 * 							jdbc:oracle:thin:@localhost:1521:XE
 * 							=> SQLPlus가 열리는 거랑 똑같다
 * 		3) SQL문장을 전송
 * 		   PreparedStatement ps=conn.prepareStatement(SQL문장);
 * 		   SQL문장 => SELECT...
 * 		
 * 		4) 오라클에서 실행된 데이터를 받는다
 * 		   ResultSet rs=ps.executeQuery()
 * 					---	실행된 결과를 메모리에 저장
 * 		   SELECT ename,job
 * 		   ResultSet
 * 			-------- 			----------
			KING                 PRESIDENT | 커서 이동 => next()
			BLAKE                MANAGER
			CLARK                MANAGER
			JONES                MANAGER
			MARTIN               SALESMAN
			ALLEN                SALESMAN
			TURNER               SALESMAN
			JAMES                CLERK
			WARD                 SALESMAN
			FORD                 ANALYST
			SMITH                CLERK	| 커서 이동 => previous()
			| 커서가 여기 존재
			
			=> 커서가 있는 위치에서 값을 가져옴
			=> Order by를 이용해서 데이터를 읽어온다 => 보통 next()를 많이 사용한다
			다음데이터가 있으면  true 없으면 false
			
			while(rs.next())
			{
				=> VO에 값응ㄹ 채운다
			}
			=> 읽을 데이터가 없는 상태
			rs.close()
			ps.close()
			conn.close() ==> 종료
			------- 코딩하는 패턴이 1개
			------- SQL문장을 정상수행하게 제작할 수 있냐가 핵심 (SQL문장을 제대로 만들 수 있냐가 관건)
			------- DML,DQL => CRUD
			
		=> 반복구간 => 연결 / 닫기 => 메소드화 시킬거임
		
		SELECT 문장 : 데이터 읽기 (검색)
		 => 형식
		 	SELECT * | column1, column2...
		 	FROM table_name명
		 	[
		 		WHERE 조건문 (연산자)
		 		GROUP BY 컬럼|함수 => 그룹
		 		HAVING 그룹에 대한 조건 ==> 반드시 GROUP BY가 있는 경우에만 사용
		 		ORDER BY 컬럼(쩡렬대상), 번호 => ASC|DESC
		 									---- ASC는 생략가능
		 	]
		 	
 *			 		
 * 		   
 * 		
 * 
 */
import java.util.*; // Date
import java.sql.*; // 오라클 연결하는 클래스 : Connection / PreparedStatement / ResultSet
public class EmpDAO {
	// 네트워크 프로그램 => 자바 응용 프로그램 (클라이언트) <==> 오라클 (서버)
	// 요청(SQL) <==> 응답 (실제 출력 결과값을 받는다)
	// 연결 객체 선언 => Connection
	private Connection conn;
	//SQL문장 송수신
	private PreparedStatement ps; // read/ write
	// 오라클 연결 => 오라클 주소(ip)가 필요
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
											//	--------- 본인 ip로 바꾸면 됨
	
	// 드라이버 등록 => 한번만 수행 => 보통 코딩하는 위치는 생성자
	public EmpDAO()
	{
		try
		{
			// 대소문자 구분
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 메모리 할당 => 클래스명으로 메모리 할당이 가능 => 리플렉션
		}catch(Exception ex) {}
	}
	// 연결 => SQLPlus를 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			//conn hr/happy
		}catch(Exception ex) {}
	}
	// 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			
			//exit
		}catch(Exception ex) {}
	}
	// 기능 수행 => 메소드 => 테이블 1개당 => VO, DAO
	// SQL 문장 전송
	// emp에서 데이터를 => 사번, 이름, 
	public void empListData()
	{
		try
		{
			// 1. 오라클 연결
			getConnection();
			// 2. SQL문장 제작
			String sql="SELECT empno,ename,job,hiredate,sal "
					+ "FROM emp"; // ;이 자동으로 생성되기 때문에 emp 뒤에 ;찍으면 안된다
			// 3. SQL문장을 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. 결과값을 받는다
			ResultSet rs=ps.executeQuery();
			// 5. 결과값을 출력
			/*
			 *		no		name	sex		regdate
			 *		----------------------------------
			 *		1		홍길동	남자		23/11/13
			 *		-		----	---		--------
			 *								| rs.getDate(4)
			 *						| rs.getString(3)
			 *				|rs.getString(2)
			 *		|rs.getInt(1)
			 */
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+""
								  +rs.getString(2)+""
								  +rs.getString(3)+""
								  +rs.getDate(4)+""
								  +rs.getInt(5));
			}
		}catch(Exception ex) 
		{
			// 오류위치확인
			ex.printStackTrace();
		}
		finally
		{
			//닫기
			disConnection();
		}
	}
	// 사원의 이름, 직위, 급여, 입사일, 성과급 => 성과급이 없는 사원의 목록을 출력
	public void empNotCommListData()
	{
		try
		{
			getConnection();
			String sql="SELECT ename,job,hiredate,sal,comm "
					+ "FROM emp WHERE comm IS NULL";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1)+" "
								  +rs.getString(2)+" "
								  +rs.getDate(3)+" "
								  +rs.getInt(4)+" "
								  +rs.getInt(5));	
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
	
	public void empCommListData()
	{
		try
		{
			getConnection();
			String sql="SELECT ename,job,sal,hiredate,comm "
					+ "FROM emp WHERE comm <> 0 AND comm IS NOT null";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1)+" "
								  +rs.getString(2)+" "
								  +rs.getInt(3)+" "
								  +rs.getDate(4)+" "
								  +rs.getInt(5));	
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
	// 사용자로부터 검색어를 받아서 검색
	public void empFindData(String ename)
	{
		try
		{
			getConnection();
			String sql="SELECT ename,job,hiredate,sal "
					+ "FROM emp " + "WHERE ename LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			// => ?에 값을 채운 후에 실행 요청
			ps.setString(1, ename); //1번 물음표에 ename 값을
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1)+ " "
									+ rs.getString(2) + " "
									+ rs.getDate(3)+ " "
									+ rs.getInt(4));		
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
	//-- RPAD 
	public void empRPADData()
	{
		try
		{
			getConnection();
			String sql="SELECT ename, RPAD(SUBSTR(ename,1,2),LENGTH(ename),'*') "
					+ "FROM emp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
			System.out.println(rs.getString(1)+" "
					          +rs.getString(2));	
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	public void empSalInfoData()
	{
		try
		{
			getConnection();
			String sql="SELECT ename,ROUND(MONTHs_BETWEEN(SYSDATE,hiredate)),"
					+ "TO_CHAR(sal, '$999,999'),TO_CHAR(sal*12,'$999,999'),TO_CHAR(sal+nvl(comm,0),'$999,999'),"
					+ "TO_CHAR(hiredate,'YYYY-MM-DD HH24:MI:SS') "
					+ "FROM emp";
					//,다음에는 공백을 사용할 필요가 없다
			ps=conn.prepareStatement(sql);
			// TO_CHAR => 문자열로 변환 => rs.getString을 이용한다
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
			System.out.println(rs.getString(1)+" "
					          +rs.getInt(2)+" "
					          +rs.getString(3)+ " "
					          +rs.getString(4)+ " "
					          +rs.getString(5)+" "
					          +rs.getString(6));	
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
		
		
		public void empGroupByData()
		{
			try
			{
				getConnection();
				String sql="SELECT TO_CHAR(hiredate,'YYYY'),count(*),sum(sal),"
						+ "		AVG(sal),MAX(sal),MIN(sal) "
						+ "		FROM EMP "
						+ "		GROUP BY TO_CHAR(hiredate,'YYYY') "
						+ "		ORDER BY 1 ASC";
						//,다음에는 공백을 사용할 필요가 없다
				ps=conn.prepareStatement(sql);
				// TO_CHAR => 문자열로 변환 => rs.getString을 이용한다
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				System.out.println(rs.getString(1)+" "
						          +rs.getInt(2)+" "
						          +rs.getInt(3)+ " "
						          +rs.getDouble(4)+ " " // 소수점이 없으면 int, 있으면 double
						          +rs.getInt(5)+" "
						          +rs.getInt(6));	
				}
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				disConnection();
			}
		
		}

		// 서브쿼리를 사용하지 않는 경우
		public void subQueryNotData()
		{
			try
			{
				getConnection();
				String sql="SELECT ROUND(AVG(SAL)) "+"FROM emp";
				ps=conn.prepareStatement(sql);
				// sql문장은 1개만 전송가능
				ResultSet rs=ps.executeQuery();
				rs.next();
				int avg=rs.getInt(1);
				rs.close();
				sql="SELECT ename,job,hiredate,sal "
						+ "FROM emp "+" "
						+ "WHERE sal<?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, avg);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					System.out.println(rs.getString(1)+" "
									+ rs.getString(2) + " "
									+rs.getDate(3)+ " "
									+rs.getInt(4));
				
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
		// 서브쿼리를 사용한 경우 ***********************************다시 적어야함
		public void subQueryData()
		{
			try
			{
				getConnection();
				String sql="SELECT ROUND(AVG(SAL)) "+"FROM emp"+
				"WHERE sal<(SELECT ROUND(ANG(sal)) FROM emp";
				ps=conn.prepareStatement(sql);
				// sql문장은 1개만 전송가능
				ResultSet rs=ps.executeQuery();
				rs.next();
				int avg=rs.getInt(1);
				rs.close();
				sql="SELECT ename,job,hiredate,sal "
						+ "FROM emp "+" "
						+ "WHERE sal<?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, avg);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					System.out.println(rs.getString(1)+" "
									+ rs.getString(2) + " "
									+rs.getDate(3)+ " "
									+rs.getInt(4));
				
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
	}
		
			
		
	


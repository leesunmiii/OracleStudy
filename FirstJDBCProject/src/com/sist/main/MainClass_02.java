package com.sist.main;
/*
 	1) 
 		Web 개발 (사이트)
 		
 		출력 위치 => 브라우저
 				   ------ 오라클 연동하는 프로그램이 없다
 				   	HTML / CSS / JavaScript
 				   	=> 외의 모든 언어는 브라우저 일반 텍스트
 		
 		브라우저 ===== 자바 ===== 오라클
 				전송		  읽기
 						 JDBC => Java Database Connective
 						 JDBC는 java.sql에 들어있다
 						 => 오라클 연결하는 프로그램은 자바에 존재하지 않는다 => 그래서 외부 라이브러리 사용
 						 												   ------------ ojdbc8.jar
 						 												   		     (여기 빌드패스한 파일)
 						
 						파일은 xml, json, csv 읽는다
 						
 		=>	1차 프로젝트 : 화면 UI / 데이터베이스 (SQL)
 			HTML / CSS / Javascript
 			=> JSP => MVC / Ajax
 		------------------------------------ 흐름
 		=> 	2차 프로젝트 : Spring(의존성 약한)
 		 				보안 / 권한 부여 / ORM(MyBatis 같은거) 중심
 		 		=> JSP => MVC / VueJS
 		 		=> MyBatis
 		=>  3차 프로젝트 : 신기술 (Back / Front)
 							  ------------- 서버가 여러개 MSA
 			 	=> Spring Boot / Spring Cloud (MSA)
 			 	=> MySQL, React(Redux), JPA
 		---------------------------------------
 		MyBatis / JPA 데이터베이스 연동하는프로그램
 		
 		 => 자바
 		 	프로그램의 핵심 : 데이터 관리
 		 		변수 / 배열 / 클래스 / 파일 / 오라클 / XML / JSON
 		 		=> 데이터베이스
 		 		=> 머신러닝 / 딥러닝
 		 			=> 데이터 수집 / 데이터 전처리 / 분석
 		 			머신러닝은 위에 세개를 따로, 딥러닝은 위에 세개를 하나로 보는 것
 		 -----------------------------------------
 		 => 프로젝트
 		 	=> 벤치마킹 (아이템)
 		 		=> 예약(예매), 추천, 결제
 		 		---------------------
 		 			=> 출력할 데이터
 		 	=> 요구사항 분석
 		 	   ---------- 필요한 데이터 (크롤링)
 		 	=> 오라클 저장 (데이터베이스 설계)
 		 	=> 화면 UI
 		 	=> 구현
 		 	=> 테스트
 		 	=> 배포 (발표) => 호스팅(AWS)
 		 	
 		 => 문법
 		 	= 연산자 / 제어문
 		 	= 재사용 => 메소드
 		 	----------------
 		 	같은 기능끼리 묶어주는 역할
 		 	---------------------- 한개로 통합 (클래스)
 		 	
 		 	클래스 : 관련된 데이터를 묶는다 => VO
 		 				=> 캡슐화 이용 => 변수 은닉화 => private 사용
 		 				 	=> 다른 클래스나 브라우저에서 접근이 안됨
 		 				 	=> 사용할 수 있게 해주는게 읽기/쓰기 기능 : getter/setter
 		 			=> 사용자 정의 데이터형
 		 				=> CRUD => 액션
 		 					=> 메소드로만 만든다
 		 					=> ~Manager
 		 					=> ~DAO
 		 			관련된 기능을 묶는다
 		 				=> 핵심 => 재사용 (상속/포함)
 		 								웹을 짤때는 상속이라는 개념 별로 없을거임. 속도 느려진다고 상속하지말라함
 		 				=> 포함 / 상속 => 수정, 추가가 가능해야함
 		 				--------------- 객체지향의 3대 요소
 		 				=> 관련된 클래스가 여러개 있는 경우
 		 				----------------------------
 		 					=> 인터페이스를 이용해서 1개로 제어
 		 					List list=new ArrayList()
 		 						 list=new Vector()
 		 						 list=new LinkedList()
 		 					=> 인터페이스는 관련된 클래스 여러개를 묶어서 사용
 		 				=> 객체란?
 		 				   ------
 		 	 *** 여러개 데이터
 		 	  	  변수 / 배열 / 클래스 => 한 개의 이름으로 어떻게 제어
 		 				   
 		 					
 		 	리턴형 => 1개를 쓴다
 		 	
 		 	emp => 사원정보
 		 	=> empno 사번 => int
 		 	=> ename 이름 => String
 		 	=> job 직위 => String
 		 	=> hiredate 입사일 => Date
 		 	=> sal 급여 => int
 		 	=> comm 성과급 => int
 		 	=> mgr 사수의 사번 => int
 		 	=> deptno 부서번호 => int
 		 	
 		 	=> public int getEmpo()
 		 	=> public String getEname()
 		 	=> public String getJob()
 		 	=> public Date getHiredate()
 		 	=> public int getSal()
 		 	=> public int getMgr()
 		 	=> public int getComm()
 		 	=> public int getDeptno()
 		 	
 		 	=> public EmpVO getEmpData()
 		 	
 							   
 */
public class MainClass_02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

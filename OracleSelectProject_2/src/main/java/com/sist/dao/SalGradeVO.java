package com.sist.dao;
/*
 * 밸류 오브젝트      VO => 읽기/쓰기 (getter/setter)
 * 		VO => 오라클 데이터를 받아서 저장할 목적
 * 		   => 브라우저, 윈도우로 전송할 목적
 * 		   => DTO (Data Transfor Object)
 * 		브라우저(JSP)	====== 자바 응용 프로그램	====== 오라클
 * 					  요청->					SQL	->
 * 				VO 넘겨줌   VO로 결과값 받음  결과값 넘겨줌 <-
 * 		JSP에서는 핵심 클래스 => Bean
 * 		=> 가급적이면 => 오라클 컬럼과 매칭
 * 		데이터형을 맞춰야함
 * 		문자 : CHAR, VARCAHR2,CLOB => String
 * 		숫자 : NUMBER => int, double
 * 		날짜 : DATE => java.util.Date
 * 		-----------------> MyBatis(ORM) => VO 설정하면 자동으로 값을 받을 수 있다(알아서 값을 넣어줌)	
 */
public class SalGradeVO {
	private int grade,losal,hisal;

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getLosal() {
		return losal;
	}

	public void setLosal(int losal) {
		this.losal = losal;
	}

	public int getHisal() {
		return hisal;
	}

	public void setHisal(int hisal) {
		this.hisal = hisal;
	}
	
}

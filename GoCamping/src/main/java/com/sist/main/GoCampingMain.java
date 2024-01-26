package com.sist.main;

import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.gocamping.GoCampingDAO;
import com.sist.gocamping.GoCampingVO;

public class GoCampingMain {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
	   GoCampingDAO dao=new GoCampingDAO();
	   GoCampingVO vo=new GoCampingVO();
      try
      {
        
         for(int i=1;i<=375;i++)
         {
            Document doc=Jsoup.connect("https://gocamping.or.kr/bsite/camp/info/list.do?pageUnit=10&searchKrwd=&listOrdrTrget=c_rdcnt&pageIndex=" + i).get();
            Elements link=doc.select("h2.camp_tt a");
            
            for(int j=0;j<link.size();j++)
            {
               try 
               {
                  //System.out.println(link.get(i).attr("href"));
                  String subLink="https://gocamping.or.kr" + link.get(j).attr("href");
                  System.out.println(j+". "+subLink);
                  Document doc2=Jsoup.connect(subLink).get();
                  
                  //1. 제목
                  Elements title=doc2.select("div.s_title2 p.camp_name");
                 System.out.println(title.text());
                  
                  //2. 서브제목
                  Elements SubTitle=doc2.select("div.s_title2 p.camp_s_tt");
                  System.out.println(SubTitle.text());
                  
                   //3. 포스터
                  Elements poster=doc2.select("div.camp_info_box div.img_b img");
                System.out.println("https://gocamping.or.kr"+poster.attr("src"));
                  
                 
                  //4. 주소
                  Elements loc=doc2.select("div.camp_info_box div.cont_tb td");
                  //System.out.println(loc.get(0).text());
                  
                  //5. 문의처
                  Elements num=doc2.select("div.camp_info_box div.cont_tb td");
                  //System.out.println(num.get(1).text());
                  
                  //6. 캠핑장 환경
                  Elements envir=doc2.select("div.camp_info_box div.cont_tb td");
                 // System.out.println(envir.get(2).text());
                  
                  //7. 캠핑장 유형
                  Elements category=doc2.select("div.camp_info_box div.cont_tb td");
                 // System.out.println(category.get(3).text());
                  
                  //8. 운영기간(계절)
                  Elements season=doc2.select("div.camp_info_box div.cont_tb td");
                 // System.out.println(season.get(4).text());
                  
                  //9. 운영일
                  Elements open=doc2.select("div.camp_info_box div.cont_tb td");
                 // System.out.println(open.get(5).text());
                  
                  //10. 홈페이지
                  Elements homepage=doc2.select("div.camp_info_box div.cont_tb a");
                  //System.out.println(homepage.get(6).text());
                 
                  //11. 주변이용가능시설
                  Elements facility=doc2.select("div.camp_info_box div.cont_tb td");
                  //System.out.println(facility.get(7).text());
                  
                  //12. 사진1
                  Elements pic1=doc2.select("div.camp_intro img");
                  //System.out.println(pic1.get(0).attr("src"));
                  
                  //13. 사진2
                  Elements pic2=doc2.select("div.camp_intro img");
                  //System.out.println(pic2.get(1).attr("src"));
                  
                  //14. 사진3
                  Elements pic3=doc2.select("div.camp_intro img");
                 // System.out.println(pic3.get(2).attr("src"));
                  
                  //15. 간단설명
                  Elements explain=doc2.select("div.camp_intro p.camp_intro_txt");
                  //System.out.println(explain.text());
                  
                  //System.out.println("===========================================");

                  
                
                   try 
                   {
                	   
                	   vo.setTitle(title.text());
	                   vo.setSubtitle(SubTitle.text());
	                   
	                   
	                   if(poster.hasAttr("src"))
	                   {
	                	   System.out.println("https://gocamping.or.kr"+poster.attr("src"));
	                	   vo.setPoster("https://gocamping.or.kr"+poster.attr("src"));
	                   }
	                   else if(poster.hasAttr("href"))
	                   {
	                	   System.out.println("https://gocamping.or.kr"+poster.attr("href"));
	              
	                	   vo.setPoster("https://gocamping.or.kr"+poster.attr("href"));
	                	   
	                   }
	                  
	               
	                   
                   
                	   Elements liink=doc2.select("div.camp_info_box div.cont_tb th");
                	   for(int k=0;k<=7;k++)
	                   {
                			   if(liink.get(k).text().contains("주소"))
                			   {
		                		   System.out.println(loc.get(k).text());
                			   	   vo.setLoc(loc.get(k).text());
                			   }
                			   else if(liink.get(k).text().contains("문의처"))
		                	   {
		                		   System.out.println(num.get(k).text());
		                		   vo.setNum(num.get(k).text());
		                	   }
		                	   else if(liink.get(k).text().contains("캠핑장 환경"))
		                	   {
		                		   System.out.println(envir.get(k).text());
		                		   vo.setEnvir(envir.get(k).text());
		                	   }
		                	   else if(liink.get(k).text().contains("캠핑장 유형"))
		                	   {
		                		   System.out.println(category.get(k).text());
		                		   vo.setCategory(category.get(k).text());
		                	   }
		                		   
		                	   else if(liink.get(k).text().contains("운영기간"))
		                	   {
		                		   System.out.println(season.get(k).text());
		                		   vo.setSeason(season.get(k).text());
		                	   }
		                		   
		                	   else if(liink.get(k).text().contains("운영일"))
		                	   {
		                		   System.out.println(open.get(k).text());
		                		   vo.setOpen(open.get(k).text());
		                	   }
		                		   
		                	   else if(liink.get(k).text().contains("홈페이지"))
		                	   {
		                		   vo.setHomepage(homepage.attr("href"));
		                	   }
		                	   else if(liink.get(k).text().contains("예약방법"))
		                	   {
		                		   vo.setHomepage(homepage.text());
		                	   }
		                	   else if(liink.get(k).text().contains("주변이용가능시설"))
		                		   if(facility.get(k).text().contains("/"))
		                		   {
		                			   System.out.println((facility.get(k).text()).replace("/", ", "));
		                			   vo.setFacility(facility.get(k).text().replace("/", ", "));
		                		   }  
		                		   else
		                		   {
		                			    System.out.println(facility.get(k).text());
		                			    vo.setFacility(facility.get(k).text());
		                		   }
                			   
		                	   else if(liink.get(k).text().contains(" "))                  		  
                    			   System.out.println(" 값 없음 ");
                		   }
                	   vo.setPic1(pic1.get(0).attr("src"));
	                   vo.setPic2(pic2.get(1).attr("src"));
	                   vo.setPic3(pic3.get(2).attr("src"));
	                   if(explain.text().equals("* 고캠핑에 등록된 정보는 현장상황과 다소 다를 수 있으니 반려동물 동반 여부, 부가 시설물, 추가차량 등 원활한 캠핑을 위해 꼭 필요한 사항은 해당 캠핑장에 미리 확인하시기 바랍니다."))
	                	   vo.setExplain(" ");
	                   else
	                	   vo.setExplain(explain.text()); 
              	   
                   }catch(IndexOutOfBoundsException ex) 
                   {
                	  System.out.println(" ");
                   }
         
               }catch(Exception ex) 
               {
            	   ex.printStackTrace();
               }
               System.out.println("===========================================");
               dao.campInsert(vo);
//        	   Thread.sleep(100);
            }
         }
         }catch(Exception ex)
         {
        	 ex.printStackTrace();
         }
   }
}
   



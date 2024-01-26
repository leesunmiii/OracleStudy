package com.sist.main;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.gocamping.GoCampingDAO;
import com.sist.gocamping.GoCampingVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GoCampingMain6 {

    public static void main(String[] args) {
        GoCampingDAO dao = new GoCampingDAO();
        GoCampingVO vo = new GoCampingVO();

        try 
        {
            for (int i = 1; i <= 375; i++) 
            {
                // URL 연결 및 데이터 가져오기
                Connection.Response response = Jsoup.connect("https://gocamping.or.kr/bsite/camp/info/list.do?pageUnit=10&searchKrwd=&listOrdrTrget=c_rdcnt&pageIndex=" + i).execute();

                // 응답 스트림 가져오기
                InputStream inputStream = response.bodyStream();

                // BufferedReader를 사용하여 응답 스트림에서 데이터 읽기
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) 
                {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) 
                    {
                        content.append(line);
                    }

                    // 이제 content StringBuilder에 웹 페이지의 전체 내용이 들어 있습니다.
                    // 원하는 처리를 수행하십시오.
                    System.out.println(content.toString());

                    // Jsoup으로 파싱
                    Document doc = Jsoup.parse(content.toString());
                    Elements link = doc.select("h2.camp_tt a");

                    for (int j = 0; j < link.size(); j++) 
                    {
                        String subLink = "https://gocamping.or.kr" + link.get(j).attr("href");
                        System.out.println(j + ". " + subLink);
                        Document doc2 = Jsoup.connect(subLink).get();

                        Elements title = doc2.select("div.s_title2 p.camp_name");
                        System.out.println(title.text());

                        Elements subTitle = doc2.select("div.s_title2 p.camp_s_tt");
                        System.out.println(subTitle.text());

                        Elements poster = doc2.select("div.camp_info_box div.img_b img");

                        Elements loc = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements num = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements envir = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements category = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements season = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements open = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements homepage = doc2.select("div.camp_info_box div.cont_tb a");

                        Elements facility = doc2.select("div.camp_info_box div.cont_tb td");

                        Elements pic1 = doc2.select("div.camp_intro img");

                        Elements pic2 = doc2.select("div.camp_intro img");

                        Elements pic3 = doc2.select("div.camp_intro img");

                        Elements explain = doc2.select("div.camp_intro p.camp_intro_txt");

                        vo.setTitle(title.text());
                        try 
                        {
                            vo.setSubtitle(subTitle.text());
                        } catch (Exception ex) 
                        {
                            vo.setSubtitle(" ");
                            ex.printStackTrace();
                        }
                        try
                        {
                           vo.setNum(doc2.select("tr.camp_call_pcVer td").text());
                           System.out.println(doc2.select("tr.camp_call_pcVer td").text());
                        }catch(Exception ex)
                        {
                           ex.printStackTrace();
                           vo.setNum(" ");
                        }

                        try 
                        {
                            if (poster.hasAttr("src")) 
                            {
                                System.out.println("https://gocamping.or.kr" + poster.attr("src"));
                                vo.setPoster("https://gocamping.or.kr" + poster.attr("src"));
                            } else if (poster.hasAttr("href")) 
                            {
                                System.out.println("https://gocamping.or.kr" + poster.attr("href"));
                                vo.setPoster("https://gocamping.or.kr" + poster.attr("href"));
                            }
                        } catch (Exception ex) 
                        {
                            vo.setPoster(" ");
                            ex.printStackTrace();
                        }

                        Elements linkHeaders = doc2.select("div.camp_info_box div.cont_tb th");

                        //String[] fieldNames = {"주소", "문의처", "캠핑장 환경", "캠핑장 유형", "운영기간", "운영일", "홈페이지", "예약방법", "주변이용가능시설"};

                       

                        for (int k = 0; k < linkHeaders.size(); k++) 
                        {
                           boolean valueAlreadySet = false;
                            
                            if (linkHeaders.get(k).text().contains("주소")) 
                            {
                                System.out.println(loc.get(k).text());
                                vo.setLoc(loc.get(k).text());
                                valueAlreadySet = true;
                            } 
                            
//                            else if (linkHeaders.get(k).text().contains("문의처")) 
//                            {
//                                System.out.println(num.get(k).text());
//                                vo.setNum(num.get(k).text());
//                                valueAlreadySet = true;
//                            } 
                            
                            else if (linkHeaders.get(k).text().contains("캠핑장 환경")) 
                            {
                                System.out.println(envir.get(k).text());
                                vo.setEnvir(envir.get(k).text());
                                valueAlreadySet = true;
                            } 
                            
                            else if (linkHeaders.get(k).text().contains("캠핑장 유형")) 
                            {
                                System.out.println(category.get(k).text());
                                vo.setCategory(category.get(k).text());
                                valueAlreadySet = true;
                            } 
                            
                            else if (linkHeaders.get(k).text().contains("운영기간")) 
                            {
                                System.out.println(season.get(k).text());
                                vo.setSeason(season.get(k).text());
                                valueAlreadySet = true;
                            } 
                            
                            else if (linkHeaders.get(k).text().contains("운영일")) {
                                System.out.println(open.get(k).text());
                                vo.setOpen(open.get(k).text());
                                valueAlreadySet = true;
                            }
                            
                            else if (linkHeaders.get(k).text().contains("홈페이지"))
                            {
                                vo.setHomepage(homepage.attr("href"));
                                System.out.println(homepage.attr("href"));
                                valueAlreadySet = true;
                            } 
                            
                            else if (linkHeaders.get(k).text().contains("예약방법")) 
                            {
                                vo.setReservation(category.get(k).text());
                                System.out.println(category.get(k).text());
                                valueAlreadySet = true;
                            } 
                            
                            else if (linkHeaders.get(k).text().contains("주변이용가능시설")) 
                            {
                                if (facility.get(k).text().contains("/")) 
                                {
                                    System.out.println((facility.get(k).text()).replace("/", ", "));
                                    vo.setFacility(facility.get(k).text().replace("/", ", "));
                                } 
                                
                                else 
                                {
                                    System.out.println(facility.get(k).text());
                                    vo.setFacility(facility.get(k).text());
                                }
                                valueAlreadySet = true;
                            }
                       

                        // 값이 이미 설정되지 않은 경우에만 실행
                            if (!valueAlreadySet) 
                            {
                               vo.setEnvir(null);
                               vo.setSeason(null);
                               vo.setOpen(null);
                               vo.setHomepage(null);
                               vo.setCategory(null);
                               vo.setFacility(null);
                            }
                           }  
                        vo.setHomepage(homepage.attr("href"));
                        
                        System.out.println("==============그룹 끝============");

                        try 
                        {
                            vo.setPic1(pic1.get(0).attr("src"));
                            System.out.println(pic1.get(0).attr("src"));
                        } catch (Exception ex) 
                        {
                            vo.setPic1(" ");
                            ex.printStackTrace();
                        }

                        try 
                        {
                            vo.setPic2(pic2.get(1).attr("src"));
                            System.out.println(pic2.get(1).attr("src"));
                        } catch (Exception ex) 
                        {
                            vo.setPic2(" ");
                            ex.printStackTrace();
                        }

                        try 
                        {
                            vo.setPic3(pic3.get(2).attr("src"));
                            System.out.println(pic3.get(2).attr("src"));
                        } catch (Exception ex) 
                        {
                            vo.setPic3(" ");
                            ex.printStackTrace();
                        }

                        try
                        {
                            if (explain.text().equals(
                                    "* 고캠핑에 등록된 정보는 현장상황과 다소 다를 수 있으니 반려동물 동반 여부, 부가 시설물, 추가차량 등 원활한 캠핑을 위해 꼭 필요한 사항은 해당 캠핑장에 미리 확인하시기 바랍니다.")) 
                            {
                                System.out.println("설명없음");
                                vo.setExplain(" ");
                            }
                            else
                            {
                               vo.setExplain(explain.text());
                                System.out.println(explain.text());
                            }   
                            
                        } catch (Exception ex) 
                        {
                            ex.printStackTrace();
                            vo.setExplain(" ");
                        }
                        System.out.println("===========================================");
                        dao.campInsert(vo);
                        // Thread.sleep(500);
                    }
                    
                } catch (IOException e) 
                {
                   e.printStackTrace();
                }
            }
        }catch(Exception ex)
        {
           ex.printStackTrace();
        }
    }
}
        
    
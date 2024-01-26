package com.sist.gocamping;

import java.util.*;
import com.sist.gocamping.GoCampingVO;
import java.sql.*;

public class GoCampingDAO {
   private Connection conn;
   private PreparedStatement ps; //sql문장을 전송하는 역할
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   public GoCampingDAO()
   {
      try
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      }catch(Exception ex) {}
   }
   
   public void getConnetcion()
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
      }catch(Exception ex) {}
   }
   
   public void campInsert(GoCampingVO vo)
   {
      try
      {
         getConnetcion();
         String sql="INSERT INTO GoCamping VALUES("
                  +"GoCamping_mno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         ps=conn.prepareStatement(sql);
         ps.setString(1, vo.getTitle());
         ps.setString(2, vo.getSubtitle());
         ps.setString(3, vo.getPoster());
         ps.setString(4, vo.getLoc());
         ps.setString(5, vo.getNum());
         ps.setString(6, vo.getEnvir());
         ps.setString(7, vo.getCategory());
         ps.setString(8, vo.getSeason());
         ps.setString(9, vo.getOpen());
         ps.setString(10, vo.getHomepage());
         ps.setString(11, vo.getReservation());
         ps.setString(12, vo.getFacility());
         ps.setString(13, vo.getPic1());
         ps.setString(14, vo.getPic2());
         ps.setString(15, vo.getPic3());
         ps.setString(16, vo.getExplain());
         
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
}

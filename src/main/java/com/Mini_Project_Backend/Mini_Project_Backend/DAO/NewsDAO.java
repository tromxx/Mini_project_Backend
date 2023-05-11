package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

<<<<<<< HEAD
    //Get the only new Title
    public List<String> getTitle(){
        List<String> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT NEWS_TITLE FROM NEWS";
=======
    //최신 뉴스 기준으로 가죠오기
    public List<NewsVO> getTitle(){
        List<NewsVO> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT NEWS_NO, NEWS_TITLE, NEWS_IMAGE_URL,NEWS_SHORT_CONTENT, NEWS_DATE FROM NEWS order by news_date";
>>>>>>> Trom
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                String news_Title = rs.getString("NEWS_TITLE");
<<<<<<< HEAD
                list.add(news_Title);
=======
                String news_Image_Url = rs.getString("NEWS_IMAGE_URL");
                String news_Short_Content = rs.getString("NEWS_SHORT_CONTENT");
                Date news_Date = rs.getDate("NEWS_DATE");
                NewsVO newsVO = new NewsVO();
                newsVO.setNews_No(news_No);
                newsVO.setNews_Title(news_Title);
                newsVO.setNews_Image_Url(news_Image_Url);
                newsVO.setNews_Short_Content(news_Short_Content);
                newsVO.setNews_Date(news_Date);
                list.add(newsVO);
>>>>>>> Trom
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    // 뉴스 번호로 뉴스 제목, 뉴스 이미지, 뉴스 내용 가지고 오기
    public List<NewsVO> getNewsInfo(int news_Numbers){
        List<NewsVO> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM NEWS WHERE NEWS_NO = '"+ news_Numbers +"'";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                int news_No = rs.getInt("NEWS_NO");
                String news_title = rs.getString("NEWS_TITLE");
                String news_image = rs.getString("News_Image");
                String news_content = rs.getString("News_Content");
                NewsVO newsVO = new NewsVO();
                newsVO.setNews_No(news_No);
                newsVO.setNews_Title(news_title);
                list.add(newsVO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }

}

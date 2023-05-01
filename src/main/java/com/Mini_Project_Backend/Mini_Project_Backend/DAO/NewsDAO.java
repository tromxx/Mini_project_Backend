package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    //Get the only new Title
    public List<NewsVO> getTitle(){
        List<NewsVO> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM NEWS";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                int news_No = rs.getInt("NEWS_NO");
                String news_Title = rs.getString("NEWS_TITLE");
                String news_Image = rs.getString("NEWS_IMAGE");
                String news_Content = rs.getString("NEWS_CONTENT");
                NewsVO newsVO = new NewsVO();
                newsVO.setNews_No(news_No);
                newsVO.setNews_Title(news_Title);
                newsVO.setNews_Image(news_Image);
                newsVO.setNews_Content(news_Content);
                list.add(newsVO);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //Get the news title, news url, news content
    public List<NewsVO> getNewsInfo(String newsTitle){
        List<NewsVO> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT NEWS_TITLE , NEWS_IMAGE, NEWS_CONTENT FROM NEWS WHERE NEWS_TITLE = '"+ newsTitle +"'";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                String news_title = rs.getString("NEWS_TITLE");
                String news_image = rs.getString("News_Image");
                String news_content = rs.getString("News_Content");

                NewsVO newsVO = new NewsVO();
                newsVO.setNews_Title(news_title);
                newsVO.setNews_Image(news_image);
                newsVO.setNews_Content(news_content);

                list.add(newsVO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }


}

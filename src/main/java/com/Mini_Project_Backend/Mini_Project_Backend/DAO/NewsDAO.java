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

    //뉴스 번호 제목 가지고 오기
    public List<NewsVO> getTitle(){
        List<NewsVO> list = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT NEWS_NO, NEWS_TITLE FROM NEWS";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                int news_No = rs.getInt("NEWS_NO");
                String news_Title = rs.getString("NEWS_TITLE");
                NewsVO newsVO = new NewsVO();
                newsVO.setNews_No(news_No);
                newsVO.setNews_Title(news_Title);

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

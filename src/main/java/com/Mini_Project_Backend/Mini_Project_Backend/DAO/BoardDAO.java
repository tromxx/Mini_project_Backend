package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.BoardVO;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BoardDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<Integer> getBoardPage(String cat) {
        List<Integer> page = new ArrayList<>();
        String sql = "";
        if (cat.equals("All")) {
            sql = "SELECT COUNT(*) FROM BOARD";
        } else if (cat.equals("LatestBoard")) {
            sql = "SELECT COUNT(*) FROM BOARD";
        } else {
            sql = "SELECT COUNT(*) FROM BOARD WHERE BOARD_TITLE LIKE  '%" + cat + "%'";
        }
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            int totalData = rs.getInt("COUNT(*)");
            page.add(totalData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return page;
    }

    public List<BoardVO> getBoard(String cat, int page) {
        List<BoardVO> list = new ArrayList<>();
        int postPerPage = 20;
        int endNum = page * postPerPage;
        int startNum = endNum - (postPerPage - 1);
        try {
            String sql = "";
            if (cat.equals("All")) {
                sql = "SELECT f.BOARD_NO, f.BOARD_TITLE, f.BOARD_DATE, s.nickname FROM (SELECT t.*, rownum r FROM (SELECT * FROM BOARD ORDER BY BOARD_NO) t WHERE rownum <= "+endNum+") f JOIN member s ON f.MEMBER_NO = s.member_no WHERE f.r >= " + startNum;
            } else if(cat.equals("LatestBoard")){
                sql = "SELECT f.BOARD_NO, f.BOARD_TITLE, f.BOARD_DATE, s.nickname FROM (SELECT t.*, rownum r FROM (SELECT * FROM BOARD ORDER BY BOARD_DATE DESC) t WHERE rownum <= "+endNum+") f JOIN member s ON f.MEMBER_NO = s.member_no WHERE f.r >= " + startNum;
            }else{
                sql = "SELECT f.BOARD_NO, f.BOARD_TITLE, f.BOARD_DATE, s.nickname FROM (SELECT t.*, rownum r FROM (SELECT * FROM BOARD WHERE BOARD_TITLE LIKE '%"+cat+"%') t WHERE rownum <= "+endNum+") f JOIN member s ON f.MEMBER_NO = s.member_no WHERE f.r >= " + startNum;
            }
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int board_No = rs.getInt("BOARD_NO");
                String board_Title = rs.getString("BOARD_TITLE");
                Date board_Date = rs.getDate("BOARD_DATE");
                String nickName = rs.getString("NICKNAME");

                BoardVO boardVO = new BoardVO();
                boardVO.setBoardNo(board_No);
                boardVO.setBoardTitle(board_Title);
                boardVO.setBoardDate(board_Date);
                boardVO.setNickName(nickName);
                list.add(boardVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }

    public List<BoardVO> getBoardInfo(int boardNo) {
        List<BoardVO> list = new ArrayList<>();
        try {
            String sql = "SELECT BOARD_NO, BOARD_TITLE, BOARD_DATE, NICKNAME, BOARD_CONTENT, BOARD_IMG_URL FROM BOARD f JOIN member s ON f.MEMBER_NO = s.member_no WHERE BOARD_NO = ?";
            conn = Common.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int board_No = rs.getInt("BOARD_NO");
                String board_Title = rs.getString("BOARD_TITLE");
                Date board_Date = rs.getDate("BOARD_DATE");
                String nickName = rs.getString("NICKNAME");
                String board_content = rs.getString("BOARD_CONTENT");
                String board_img_url = rs.getString("BOARD_IMG_URL");

                BoardVO boardVO = new BoardVO();
                boardVO.setBoardNo(board_No);
                boardVO.setBoardTitle(board_Title);
                boardVO.setBoardDate(board_Date);
                boardVO.setNickName(nickName);
                boardVO.setBoardContent(board_content);
                boardVO.setBoardImgUrl(board_img_url);
                list.add(boardVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return list;
    }

    // 최신순 10개 가죠오기
    public List<BoardVO> getBoardLatest(){
        List<BoardVO> list = new ArrayList<>();
        try{
            String sql = "SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM (SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM BOARD JOIN MEMBER ON BOARD.MEMBER_NO = MEMBER.MEMBER_NO ORDER BY BOARD_DATE DESC)WHERE ROWNUM <= 20";

            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int boardNo = rs.getInt("BOARD_NO");
                String boardTitle = rs.getString("BOARD_TITLE");
                String nickName = rs.getString("NICKNAME");
                Date boardDate = rs.getDate("BOARD_DATE");

                BoardVO boardVO = new BoardVO();
                boardVO.setBoardNo(boardNo);
                boardVO.setBoardTitle(boardTitle);
                boardVO.setNickName(nickName);
                boardVO.setBoardDate(boardDate);

                list.add(boardVO);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }




}
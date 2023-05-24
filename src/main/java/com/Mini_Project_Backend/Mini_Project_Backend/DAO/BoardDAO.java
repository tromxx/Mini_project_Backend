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

    // 보드 ALL 가죠오기 첫번쨰
    public Map<String, Object> getShortBoard() {
        Map<String, Object> data = new HashMap<>();
        try {
            String sql = "SELECT COUNT(*) FROM BOARD";
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            int totalData = rs.getInt("COUNT(*)");
            data.put("TotalData", totalData);
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String sql = "SELECT BOARD_NO, BOARD_TITLE, BOARD_DATE, NICKNAME FROM BOARD B INNER JOIN MEMBER M ON B.MEMBER_NO = B.MEMBER_NO WHERE ROWNUM <= 10";
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            List<Map<String, Object>> boardList = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> boardData = new HashMap<>();
                int board_No = rs.getInt("BOARD_NO");
                String board_Title = rs.getString("BOARD_TITLE");
                String nickName = rs.getString("NICKNAME");
                Date board_Date = rs.getDate("BOARD_DATE");
                boardData.put("boardNo", board_No);
                boardData.put("boardTitle", board_Title);
                boardData.put("boardDate", board_Date);
                boardData.put("nickName", nickName);
                boardList.add(boardData);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
            System.out.println("finish");
            data.put("data", boardList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<BoardVO> getAllBoardByPage(int page){
        List<BoardVO> list = new ArrayList<>();
        int postPerPage = 10;
        int endNum = page * postPerPage;
        int startNum = endNum - (postPerPage-1);
        try {
            String sql = "SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM (SELECT t.*, rownum AS r FROM (SELECT BOARD_NO, BOARD_TITLE, BOARD_DATE, NICKNAME FROM BOARD B INNER JOIN MEMBER M ON B.MEMBER_NO = B.MEMBER_NO) t WHERE rownum <= " + endNum + ") f WHERE r >=" + startNum;
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int board_No = rs.getInt("BOARD_NO");
                String board_Title = rs.getString("BOARD_TITLE");
                String nickName = rs.getString("NICKNAME");
                Date board_Date = rs.getDate("BOARD_DATE");

                BoardVO boardVO = new BoardVO();
                boardVO.setBoardNo(board_No);
                boardVO.setBoardTitle(board_Title);
                boardVO.setNickName(nickName);
                boardVO.setBoardDate(board_Date);
                list.add(boardVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 보드 번호로 정보 가죠오기
    public List<BoardVO> getLongBoard(int boardno){
        List<BoardVO> list = new ArrayList<>();
        try{
            String sql = "SELECT B.BOARD_NO, B.BOARD_TITLE, B.BOARD_IMG_URL, M.NICKNAME,  B.BOARD_CONTENT, B.BOARD_DATE FROM BOARD B INNER JOIN MEMBER M ON B.MEMBER_NO = M.MEMBER_NO WHERE BOARD_NO =" + boardno;
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int boardNo = rs.getInt("BOARD_NO");
                String boardTitle = rs.getString("BOARD_TITLE");
                String boardImgUrl = rs.getString("BOARD_IMG_URL");
                String nickName = rs.getString("NICKNAME");
                String boardContent = rs.getString("BOARD_CONTENT");
                Date boardDate = rs.getDate("BOARD_DATE");

                BoardVO boardVO = new BoardVO();
                boardVO.setBoardNo(boardNo);
                boardVO.setBoardTitle(boardTitle);
                boardVO.setBoardImgUrl(boardImgUrl);
                boardVO.setBoardContent(boardContent);
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
        return  list;
    }

    // 최신순 10개 가죠오기
    public List<BoardVO> getBoardLatest(){
        List<BoardVO> list = new ArrayList<>();
        try{
            String sql = "SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM (SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM BOARD JOIN MEMBER ON BOARD.MEMBER_NO = MEMBER.MEMBER_NO ORDER BY BOARD_DATE DESC)WHERE ROWNUM <= 10";
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
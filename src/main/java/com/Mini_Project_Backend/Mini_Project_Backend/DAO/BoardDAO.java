package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.BoardVO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 보드 제목, 닉네임 조회 하기
    public List<BoardVO> getShortBoard() {
        List<BoardVO> list = new ArrayList<>();
        try {
            String sql = "SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE FROM (SELECT BOARD_NO, BOARD_TITLE, NICKNAME, BOARD_DATE, ROW_NUMBER() OVER (ORDER BY BOARD_NO) AS rn FROM BOARD JOIN MEMBER ON BOARD.MEMBER_NO = MEMBER.MEMBER_NO) WHERE rn BETWEEN 1 AND 50";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 보드 닉네임 다음 데이터 조회 하기
    public List<BoardVO> getShortBoard(int boardno){
        List<BoardVO> list = new ArrayList<>();
        try{
            String sql = "SELECT BOARD_NO, BOARD_TITLE, BOARD_IMG_URL, NICKNAME, BOARD_DATE FROM (SELECT T.*, ROWNUM AS RN FROM (SELECT B.*, M.NICKNAME FROM BOARD B INNER JOIN MEMBER M ON B.MEMBER_NO = M.MEMBER_NO WHERE B.BOARD_NO > ? ORDER BY B.BOARD_NO) T) WHERE RN BETWEEN 1 AND 50";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, boardno);
            rs = pStmt.executeQuery();
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

    // 화면 첫페이지 가기고 오기
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
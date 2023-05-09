//package com.Mini_Project_Backend.Mini_Project_Backend.DAO;
//
//import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
//import com.Mini_Project_Backend.Mini_Project_Backend.VO.TeamVO;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TeamDAO {
//    private Connection conn = null;
//    private Statement stmt = null;
//    private ResultSet rs = null;
//    private PreparedStatement pStmt = null;
//
//    public List<TeamVO> getTeamRanking(){
//        List<TeamVO> list = new ArrayList<>();
//        try{
//            conn = Common.getConnection();
//            stmt = conn.createStatement();
//            String sql = "SELECT * FROM TEAM ORDER BY TEAM_WIN_RATIO DESC";
//            rs = stmt.executeQuery(sql);
//            while (rs.next()){
//                String team_Name = rs.getString("TEAM_NAME");
//                int team_Win = rs.getInt("TEAM_WIN");
//                int team_Lose = rs.getInt("TEAM_LOSE");
//                int team_Win_Ratio = rs.getInt("TEAM_WIN_RATIO");
//                TeamVO teamVO = new TeamVO();
//                teamVO.setTeam_Name(team_Name);
//                teamVO.setTeam_Win(team_Win);
//                teamVO.setTeam_Lose(team_Lose);
//                teamVO.setTeam_Win_Ratio(team_Win_Ratio);
//                list.add(teamVO);
//            }
//            Common.close(rs);
//            Common.close(stmt);
//            Common.close(conn);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
//}

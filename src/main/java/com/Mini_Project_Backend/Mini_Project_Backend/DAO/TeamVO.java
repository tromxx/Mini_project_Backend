package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamVO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<TeamVO> getTeamRanking(){
        List<TeamVO> teamVOList = new ArrayList<>();
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM TEAM ORDER BY TEAM_WIN_RATIO";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                String team_Name = rs.getString("TEAM_NAME");
                int team_Win = rs.getInt("TEAM_WIN");
                int team_Lose = rs.getInt("TEAM_LOSE");
                int team_Win_Ratio = rs.getInt("TEAM_WIN_RATIO");
                TeamVO teamVO = new TeamVO();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return teamVOList;
    }
}

package com.Mini_Project_Backend.Mini_Project_Backend.DAO;

import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.TeamVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeamDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<TeamVO> getTeamRanking(){
        List<TeamVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT Team_Name, Team_Win, Team_Draw, Team_Lose, Team_Win_Ratio, Team_GameBehind, ( select count(*) from Teams t2 where t2.Team_Win_Ratio > t1.Team_Win_Ratio) Ranking FROM Teams t1 ORDER BY Team_Win_Ratio DESC";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String team_Name = rs.getString("TEAM_NAME");
                int team_Win = rs.getInt("TEAM_WIN");
                int team_Draw = rs.getInt("TEAM_DRAW");
                int team_Lose = rs.getInt("TEAM_LOSE");
                float team_Win_Ratio = rs.getFloat("TEAM_WIN_RATIO");
                float team_GameBhind = rs.getFloat("TEAM_GAMEBEHIND");
                int team_Ranking = rs.getInt("RANKING");
                TeamVO teamVO = new TeamVO();
                teamVO.setTeam_Name(team_Name);
                teamVO.setTeam_Win(team_Win);
                teamVO.setTeam_Draw(team_Draw);
                teamVO.setTeam_Lose(team_Lose);
                teamVO.setTeam_Win_Ratio(team_Win_Ratio);
                teamVO.setTeam_GameBehind(team_GameBhind);
                //teamVO.setTeam_Ranking(team_Ranking);

                list.add(teamVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }
}



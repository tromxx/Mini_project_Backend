package com.Mini_Project_Backend.Mini_Project_Backend.VO;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter

public class TeamVO {
    private String team_Name;
    private int team_Win;
    private int team_Draw;
    private int team_Lose;
    private float team_Win_Ratio;
    private float team_GameBehind;
    private int team_Ranking;
}

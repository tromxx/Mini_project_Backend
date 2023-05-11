package com.Mini_Project_Backend.Mini_Project_Backend.Controller;
import com.Mini_Project_Backend.Mini_Project_Backend.DAO.TeamDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.TeamVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TeamController {

    @GetMapping("/")
    public ResponseEntity<List<TeamVO>> teamRanking(@RequestParam String x){
        TeamDAO teamDAO = new TeamDAO();
        List<TeamVO> teamVOList = teamDAO.getTeamRanking();
        return new ResponseEntity<>(teamVOList, HttpStatus.OK);
    }
}

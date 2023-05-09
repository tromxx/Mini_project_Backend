//package com.Mini_Project_Backend.Mini_Project_Backend.Controller;
//import com.Mini_Project_Backend.Mini_Project_Backend.DAO.TeamDAO;
//import com.Mini_Project_Backend.Mini_Project_Backend.VO.TeamVO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//public class TeamController {
//
//    @GetMapping("/")
//    public ResponseEntity<List<TeamVO>> getTeamInfo(@RequestParam String team){
//        TeamDAO dao = new TeamDAO();
//        List<TeamVO> list = dao.getTeamRanking();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//}

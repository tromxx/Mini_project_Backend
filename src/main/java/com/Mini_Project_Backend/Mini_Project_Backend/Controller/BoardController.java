package com.Mini_Project_Backend.Mini_Project_Backend.Controller;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.BoardDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.BoardVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class BoardController {
    @GetMapping("/HomePlate")
    public ResponseEntity<Map<String, Object>> getTotalPageBoard(@RequestParam String cat){
        BoardDAO boardDAO = new BoardDAO();
        Map<String,Object> list = boardDAO.getShortBoard();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/HomePlate/Page")
    public ResponseEntity<List<BoardVO>> getAllBoardByPage(@RequestParam int page) {
        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> list = boardDAO.getAllBoardByPage(page);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/HomePlate/Views")
    public ResponseEntity<List<BoardVO>> getLongBoard(@RequestParam int boardNo) {
        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> list = boardDAO.getLongBoard(boardNo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/Latest")
    public ResponseEntity<List<BoardVO>> getLatestBoard(@RequestParam String cat) {
        BoardDAO boardDAO = new BoardDAO();

        List<BoardVO> list = boardDAO.getBoardLatest();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

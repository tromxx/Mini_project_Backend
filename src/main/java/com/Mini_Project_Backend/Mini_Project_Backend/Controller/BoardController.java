package com.Mini_Project_Backend.Mini_Project_Backend.Controller;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.BoardDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.DAO.NewsDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.BoardVO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class BoardController {
    @GetMapping("/HomePlate{search}")
    public ResponseEntity<List<BoardVO>> getShortBoard(@PathVariable String search, @RequestParam(value = "lastBoardNo", required = false)Integer lastBoardNo){
        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> list;
        if(lastBoardNo == null){
            list = boardDAO.getShortBoard();
        }else{
            list = boardDAO.getShortBoard(lastBoardNo);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/HomePlate/Views")
    public ResponseEntity<List<BoardVO>> getLongBoard(@RequestParam int boardno){
        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> list = boardDAO.getLongBoard(boardno);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<BoardVO>> getBoardLatest(@RequestParam String boardNo) {
        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> list = boardDAO.getBoardLatest();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

package com.Mini_Project_Backend.Mini_Project_Backend.Controller;
import com.Mini_Project_Backend.Mini_Project_Backend.DAO.NewsDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class NewsController {


    // only get the news title
    @GetMapping("/News")
    public ResponseEntity<List<NewsVO>> titleList(@RequestParam String news_no){
        NewsDAO dao = new NewsDAO();
        List<NewsVO> titleLists = dao.getTitle();
        return new ResponseEntity<>(titleLists, HttpStatus.OK);
    }

    @GetMapping("/News/View")
    public ResponseEntity<List<NewsVO>> newsInfo(@RequestParam String title){
        NewsDAO dao = new NewsDAO();
        List<NewsVO> newsInfoList = dao.getNewsInfo(title);
        return new ResponseEntity<>(newsInfoList, HttpStatus.OK);
    }


}

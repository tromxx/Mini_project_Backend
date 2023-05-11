package com.Mini_Project_Backend.Mini_Project_Backend.Controller;
import com.Mini_Project_Backend.Mini_Project_Backend.DAO.NewsDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.NewsVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class NewsController {

    // 뉴스 번호 뉴스 뉴스 제목 가져오기
    @GetMapping("/News")
    public ResponseEntity<List<String>> titleList(@RequestParam String news_no){
        NewsDAO dao = new NewsDAO();
        List<String> titleLists = dao.getTitle();
        return new ResponseEntity<>(titleLists, HttpStatus.OK);
    }
    // 뉴스 번호로 제목, 이미지, 코텐츠 가져오기
    @GetMapping("/News/View")
    public ResponseEntity<List<NewsVO>> newsInfo(@RequestParam int news_no){
        NewsDAO dao = new NewsDAO();
        List<NewsVO> newsInfoList = dao.getNewsInfo(news_no);
        return new ResponseEntity<>(newsInfoList, HttpStatus.OK);
    }


}

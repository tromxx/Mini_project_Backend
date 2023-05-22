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

    //뉴스 shortContent 정보 가죠오기
    @GetMapping("/News")
    public ResponseEntity< List<NewsVO>> getShortDetailNews(@RequestParam String cat, int page){
        NewsDAO dao = new NewsDAO();
        List<NewsVO> news = dao.getNews(cat, page);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    //Pagination 을 위해 News total 사이즈 가죠오기
    @GetMapping("/News/TotalPage")
    public ResponseEntity<List<Integer>> getNewsPage(@RequestParam String cat){
        NewsDAO dao = new NewsDAO();
        List<Integer> news = dao.getNewsPage(cat);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }


    // 뉴스 번호로 제목, 이미지, Long Content 가져오기
    @GetMapping("/News/View")
    public ResponseEntity<List<NewsVO>> getLongDetailNews(@RequestParam int news_no){
        NewsDAO dao = new NewsDAO();
        List<NewsVO> newsInfoList = dao.getNewsInfo(news_no);
        return new ResponseEntity<>(newsInfoList, HttpStatus.OK);
    }
}

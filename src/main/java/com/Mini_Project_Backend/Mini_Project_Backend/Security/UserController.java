package com.Mini_Project_Backend.Mini_Project_Backend.Security;

import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<List<MemberVO>> getUserInfo() {
        AuthDAO authDAO = new AuthDAO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();
        System.out.println("인증 : " + auth);
        System.out.println("id : " + id);
        List<MemberVO> list;

        list = authDAO.getUserInfoById(id);
        System.out.println(list.get(0));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
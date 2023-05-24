package com.Mini_Project_Backend.Mini_Project_Backend.Security;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.MemberDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private BCUserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity<String> createAuthToken(@RequestBody Map<String, String> authRequest) throws Exception {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.get("email"));

        if (!userDetails.getPassword().equals(authRequest.get("pwd"))) {
            throw new BadCredentialsException("이메일, 비밀번호가 맞지 않습니다.");
        }
        final String jwt = tokenService.generateAuthToken(userDetails);

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping("/editinfo")
    public ResponseEntity<Boolean> editInfo(@RequestBody Map<String, String> regData) {
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getNickname = regData.get("nickname");
        String getFavTeam = regData.get("favTeam");
        MemberDAO dao = new MemberDAO();
        boolean isUpdated = dao.memberUpdate(getId, getPwd, getNickname, getFavTeam);
        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<List<MemberVO>> userInfo(@RequestParam String id) {
        System.out.println("ID : " + id);
        AuthDAO authDAO = new AuthDAO();
        List<MemberVO> list = authDAO.getUserInfoById(id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}



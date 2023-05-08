package com.Mini_Project_Backend.Mini_Project_Backend.Controller;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.MemberDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
// 크로스오리진 에러를 체크하지 말라고 명령하는 명령어
@RestController

public class MemberController {
    // POST : 로그인

    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String id = loginData.get("id");
        String pwd = loginData.get("pwd");
        MemberDAO dao = new MemberDAO();
        boolean result = dao.loginCheck(id, pwd);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET : 회원조회

    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET : 회원 가입 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> memberCheck(@RequestParam String id) {
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.regMemberCheck(id);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // POST : 회원 가입
    @PostMapping("/new")
    public ResponseEntity<Boolean> memberRegister(@RequestBody Map<String, String> regData) {
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getName = regData.get("name");
        String getMail = regData.get("mail");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberRegister(getId, getPwd, getName, getMail);

        // 회원 가입이 성공하면 이메일 인증 링크 전송
        if (isTrue) {
            // 임의의 인증키 생성
            String authKey = UUID.randomUUID().toString();

            // 인증키를 DB에 저장 및 만료 시간 설정
            long expireTimeMillis = System.currentTimeMillis() + 24 * 60 * 60 * 1000; // 24시간 뒤
            Timestamp expireTime = new Timestamp(expireTimeMillis);
            dao.updateAuthKey(getId, authKey, expireTime);

            // 이메일 인증 링크 생성
            String emailLink = "http://localhost:8080/emailAuth?email=" + getMail + "&authKey=" + authKey + "&expireTime=" + expireTime;

            // 이메일 인증 링크를 이메일로 전송
            JavaMailSender mailSender = new JavaMailSenderImpl();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                helper.setFrom("보내는 이메일 주소");
                helper.setTo(getMail);
                helper.setSubject("이메일 인증");
                helper.setText(emailLink);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mimeMessage);
        }

        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    @GetMapping("/emailAuth")
    public ResponseEntity<String> emailAuth(@RequestParam String email, @RequestParam String authKey) {
        MemberDAO dao = new MemberDAO();
        boolean result = dao.updateAuthKeyByAuthKey(email, authKey);
        if (result) {
            return new ResponseEntity<>("이메일 인증에 성공하였습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이메일 인증에 실패하였습니다.", HttpStatus.OK);
        }
    }

    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> memberDelete(@RequestBody Map<String, String> delData) {
        String getId = delData.get("id");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

}

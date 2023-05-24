package com.Mini_Project_Backend.Mini_Project_Backend.Controller;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.MemberDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.Security.TokenService;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;

import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")
// 크로스오리진 에러를 체크하지 말라고 명령하는 명령어
@RestController

public class MemberController {
    @Autowired
    private JavaMailSender mailSender;

    String backend = "http://localhost:8111";
    String frontend = "http://localhost:3000";
    // POST : 로그인


    @Autowired
    private TokenService tokenService;

//    @PostMapping("/login")
//    public ResponseEntity<Boolean> memberLogin(HttpServletResponse response, @RequestBody Map<String, String> loginData) {
//        String id = loginData.get("id");
//        String pwd = loginData.get("pwd");
//        MemberDAO dao = new MemberDAO();
//        boolean result = dao.loginCheck(id, pwd);
//
//        if (result) {
//            // 로그인 성공 시 토큰 생성
//            String authToken = tokenService.generateAuthToken();
//            Cookie cookie = new Cookie("authToken", authToken);
//            cookie.setMaxAge(3600); // 쿠키 유효 기간 설정 (예: 1시간)
//            response.addCookie(cookie);
//
//            dao.saveToken(id, authToken);
//        }
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


    // GET : 회원조회

    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/nickname")
    public ResponseEntity<Boolean> nicknameList(@RequestParam String nickname) {
        System.out.println("nickname : " + nickname);
        MemberDAO dao = new MemberDAO();
        boolean isAlready = dao.nicknameCheck(nickname);
        return new ResponseEntity<>(isAlready, HttpStatus.OK);
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
        String getNickname = regData.get("nickname");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberRegister(getId, getPwd, getNickname);

        // 회원 가입이 성공하면 이메일 인증 링크 전송
        if (isTrue) {
            // 임의의 인증키 생성
            String authKey = UUID.randomUUID().toString();
            System.out.println(authKey);
            // 인증키를 DB에 저장 및 만료 시간 설정
            long expireTimeMillis = System.currentTimeMillis() + 60 * 60 * 1000; // 1시간 뒤
            Timestamp expireTime = new Timestamp(expireTimeMillis);
            System.out.println(expireTime);
            dao.updateAuthKey(getId, authKey, expireTime);

            // 이메일 인증 링크 생성
            String emailLink = backend + "/emailAuth?id=" + getId + "&authKey=" + authKey;
            System.out.println(emailLink);
            // 인증 이메일에 들어갈 내용
            String htmlContent = "<div style=\"text-align: center;\">"
                    + "<p style=\"font-size: 16px;\">벤치클리어링 인증 메일입니다.</p>"
                    + "<a href=\"" + emailLink + "\" style=\"display: inline-block; background-color: #007bff; color: #ffffff; padding: 10px 20px; border-radius: 5px; text-decoration: none;\">인증하기</a>"
                    + "<p style=\"font-size: 12px;\">이 인증 링크는 생성 후 한 시간까지 유효합니다.</p>"
                    + "</div>";

            // 이메일 인증 링크를 이메일로 전송

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                helper.setFrom("benchclearing@naver.com");
                helper.setTo(getId);
                helper.setSubject("Bench Clearing 이메일 인증");
                helper.setText(htmlContent, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mimeMessage);
        }

        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @GetMapping("/emailAuth")
    public RedirectView emailAuth(@RequestParam String id, @RequestParam String authKey) {
        MemberDAO dao = new MemberDAO();
        boolean result = dao.updateAuthKeyByAuthKey(id, authKey);

        return new RedirectView(frontend + "/login");
    }


    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> memberDelete(@RequestBody Map<String, String> delData) {
        String getId = delData.get("id");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

     // POST : 비밀번호 찾기 이메일 발송
    @PostMapping("/findpw")
    public ResponseEntity<Boolean> findPw(@RequestBody Map<String, String> data) {
        String getId = data.get("id");
        System.out.println(getId);
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.regMemberCheck(getId);
        System.out.println(isTrue);
        boolean isSent = false;
        // 회원 가입이 성공하면 이메일 인증 링크 전송
        // !isTrue 인 이유 : 회원가입 할 때 쓰는 regMemberCheck() 를 끌어 쓰기 때문에 !isTrue 로 해야 맞다
        if (!isTrue) {
            // 임의의 임시 비밀번호 생성
            Random random = new Random();
            int min = 10000000;
            int max = 99999999;
            String tempPw = String.valueOf(random.nextInt(max - min + 1) + min);
            System.out.println(tempPw);
            // DB의 원래 비밀번호를 임시비밀번호로 바꿔 저장
            dao.changeTempPw(getId,tempPw);

            // 이메일에 들어갈 내용
            String htmlContent = "<div style=\"text-align: center; display:flex; justify-contents:center; text-align:center;\">"
                    + "<p style=\"font-size: 16px;\">벤치클리어링 임시 비밀번호입니다.</p>"
                    + "<div style=\"font-size:20px; font-style:bold; width: 100px; height:50px; border: 1px solid #c6c6c6;\">" + tempPw + "</div>"
                    + "<p style=\"font-size: 12px;\">임시 비밀번호로 로그인 후 안전을 위해 비밀번호를 즉시 변경해주세요.</p>"
                    + "</div>";

            // 임시 비밀번호를 이메일로 전송

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                helper.setFrom("benchclearing@naver.com");
                helper.setTo(getId);
                helper.setSubject("Bench Clearing 이메일 인증");
                helper.setText(htmlContent, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mimeMessage);
            isSent = true;
            System.out.println("isSent : " + isSent);
        }

        return new ResponseEntity<>(isSent, HttpStatus.OK);
    }
}


package com.Mini_Project_Backend.Mini_Project_Backend.Controller;

import com.Mini_Project_Backend.Mini_Project_Backend.DAO.MemberDAO;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;

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
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
// 크로스오리진 에러를 체크하지 말라고 명령하는 명령어
@RestController

public class MemberController {
    @Autowired
    private JavaMailSender mailSender;

    String backend = "http://localhost:8111";
    String frontend = "http://localhost:3000";
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

    @GetMapping("/nickname")
    public ResponseEntity<List<MemberVO>> nicknameList(@RequestParam String nickname) {
        System.out.println("nickname : " + nickname);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(nickname);
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
        String getNickname = regData.get("Nickname");
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

}

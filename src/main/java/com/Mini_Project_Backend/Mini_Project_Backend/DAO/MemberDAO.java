package com.Mini_Project_Backend.Mini_Project_Backend.DAO;
import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 로그인 체크
    public boolean loginCheck(String id, String pwd) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql = "SELECT * FROM T_MEMBER WHERE ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) { // 읽은 데이타가 있으면 true
                String sqlId = rs.getString("ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPwd = rs.getString("PWD");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPwd);
                if (id.equals(sqlId) && pwd.equals(sqlPwd)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원정보 조회

    public List<MemberVO> memberSelect(String getId) {
        List<MemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("ID");
                String pwd = rs.getString("PWD");
                String name = rs.getString("NAME");
                String email = rs.getString("EMAIL");
                Date join = rs.getDate("JOIN");

                MemberVO vo = new MemberVO();
                vo.setId(id);
                vo.setPwd(pwd);
                vo.setName(name);
                vo.setEmail(email);
                vo.setJoin(join);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 회원 가입 여부 확인
    public boolean regMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }


    // 회원 가입
    public boolean memberRegister(String id, String pwd, String name, String mail) {
        int result = 0;
        String sql = "INSERT INTO T_MEMBER(ID, PWD, NAME, EMAIL, JOIN) VALUES(?, ?, ?, ?, SYSDATE)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, mail);
            result = pStmt.executeUpdate();
            System.out.println("회원 가입 DB 결과 확인 : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if (result == 1) return true;
        else return false;
    }


    // 회원탈퇴
    public boolean memberDelete(String Id) {
        String sql = "DELETE * FROM T_MEMBER WHERE = " + "'" + Id + "'";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            System.out.println("회원 탈퇴 완료 : " + Id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        return true;
    }

    // 회원가입을 눌렀을 때 DB에 AuthKey를 추가

    public void updateAuthKey(String id, String authKey, Timestamp expireTime) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;
        String sql = "UPDATE member SET auth_key = ?, expire_time = ? WHERE id = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, authKey);
            pstmt.setTimestamp(2, expireTime);
            pstmt.setString(3, id);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 이메일에서 링크를 클릭하면 DB값을 바꿔 로그인이 가능하게 하도록 하는 기능
    public boolean updateAuthKeyByAuthKey(String email, String authKey) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            conn = Common.getConnection();
            String sql = "SELECT * FROM member WHERE email=? AND authKey=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, authKey);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 인증 링크 만료 여부 확인
                Timestamp expireTime = rs.getTimestamp("expire_time");
                if (expireTime.getTime() < System.currentTimeMillis()) {
                    return false; // 만료됨
                }

                sql = "UPDATE member SET authStatus='Y', authKey=null WHERE email=? AND authKey=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, authKey);
                pstmt.executeUpdate();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }
        return result;
    }
}

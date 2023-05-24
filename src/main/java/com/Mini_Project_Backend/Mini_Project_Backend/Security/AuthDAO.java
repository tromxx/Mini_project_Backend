package com.Mini_Project_Backend.Mini_Project_Backend.Security;
import com.Mini_Project_Backend.Mini_Project_Backend.Common.Common;
import com.Mini_Project_Backend.Mini_Project_Backend.VO.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthDAO {

    // 아이디로 회원 정보 가져오기
    public List<MemberVO> getUserInfoById(String id) {
        List<MemberVO> list = new ArrayList<>();
        String sql = "SELECT * FROM MEMBER WHERE ID = ?";
        try {
            Connection conn = Common.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                MemberVO vo = new MemberVO();
                vo.setId(rs.getString("ID"));
                System.out.println();
                vo.setPwd(rs.getString("PW"));
                vo.setNickname(rs.getString("NICKNAME"));
                vo.setFavTeam(rs.getString("FAVTEAM"));
                System.out.println("아래는 AuthDAO");
                System.out.println(rs.getString("ID"));
                System.out.println(rs.getString("PW"));
                System.out.println(rs.getString("NICKNAME"));
                System.out.println(rs.getString("FAVTEAM"));

                list.add(vo);
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectTest {

	public static void main(String[] args ) {
		 
		// 단위 테스트 없음 , 추가해서 쓸 순 있으나 그냥 해봄
		// DB 연결 
		Connection conn = DbUtil.connect();
		
		// SQL
		// 특별한 원칙이 없을 경우 오타안나게 조심 
		String sql = "SELECT * FROM MEMBER_TBL_WHERE ID = ?";
		
		// SQL 처리 객체 등록
		PreparedStatement pstmt=null;
		
		// SQL 결과셋 객체 등록
		ResultSet rs = null;
		
		// 시범용 id
		String id = "abcd1111";
		
		MemberVO memberVO;
	 
	 
		
		try {
			// SQL 처리/해석 
			pstmt = conn.prepareStatement(sql);
			// 인자 안넣었을 경우 sql에러 나옴 IN OR OUT 매개변수:: 1
			pstmt.setString(1, id);
			
			// SQL 실행 => 결과셋 
			rs = pstmt.executeQuery();
			// memberVO 데려옴
		 
			// 결과셋 => 출력
			if(rs.next()) 
			{ // 해당 SQL의 결과가 있다면..
				//System.out.println("ID  : "+rs.getString("ID"));
				//System.out.println("NAME  : "+rs.getString("NAME"));
 
				memberVO = new MemberVO();
				memberVO.setId(rs.getString("ID"));
				memberVO.setPw(rs.getString("PW"));
				memberVO.setName(rs.getString("NAME"));
				memberVO.setGender(rs.getString("GENDER"));
				memberVO.setEmail(rs.getString("EMAIL"));
				memberVO.setMobile(rs.getString("MOBILE"));
				memberVO.setPhone(rs.getString("PHONE"));
				memberVO.setZip(rs.getString("ZIP"));
				memberVO.setRoadAddress(rs.getString("ROAD_ADDRESS"));
				memberVO.setJibunAddress(rs.getString("JIBUN_ADDRESS"));
				memberVO.setDetailAddress(rs.getString("DETAIL_ADDRESS"));
				memberVO.setBirthday(rs.getDate("BIRTHDAY"));
				memberVO.setJoindate(rs.getDate("JOINDATE"));				
				
				System.out.println("회원 : " + memberVO);
			}
			
			
		} catch (SQLException e) {
		 	System.err.println("SQL 에러 : " + e);
			e.printStackTrace();
		}
		// DB 자원 반납
				DbUtil.close(pstmt, rs, conn);
	}

}

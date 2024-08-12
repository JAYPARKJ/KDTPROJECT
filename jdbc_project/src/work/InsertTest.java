package work;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {

	public static void main(String[] args) {
	 
		Connection conn = DbUtil.connect();
		
		String sql = "INSERT INTO 	MEMBER_TBL VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?, sysdate)";
				
		// 이걸 써야 와일드카드 및 인풋처리가 낫다.
		PreparedStatement pstmt = null;
		try {
			// 자동 커밋 모드 => 수동 커밋 모드 전환 
			// commit / rollback 
			// transaction 하려면 connection단위로 함 -> 수동로그로 ~~~~~~ conn.commit 해주면됨
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "abcd2222");
			pstmt.setString(2, "#Abcd1234");
			pstmt.setString(3, "신유빈");
			pstmt.setString(4, "f");
			pstmt.setString(5, "shin@naver.com");
			pstmt.setString(6, "010-9898-4545");
			pstmt.setString(7, "02-1111-2222");
			pstmt.setString(8, "12343");
			pstmt.setString(9, "서울특별시 노원구 화랑로 727");
			pstmt.setString(10, "서울특별시 노원구 공릉동 26-17 ");
			pstmt.setString(11, "태릉선수촌");
			pstmt.setDate(12, Date.valueOf("2004-07-05"));
			
			if(pstmt.executeUpdate() ==1)
			System.out.println("회원정보 저장 성공");
			else {throw new SQLException ("회원정보 저장 실패");}

			// conn.commit(); 레코드 영구 반영(등록)
			// 테스트 안하고 롤백하겠다. 
			conn.rollback(); // 레코드 작업 취소 
			} 
			catch (SQLException e) {
		 	e.printStackTrace();}
			DbUtil.close(pstmt , null, conn);
			}

}

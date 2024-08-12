package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
 
	public static Connection connect() {
		
		// static 자체는 오버라이딩은 안되나 추상화 한다고 했을 때 
		// 널값이 기본값이라 return값 먼저 챙기기 넣어주기 
		
		// DB 연결 객체 
		Connection conn = null;
		
		// DB 연결 요소 
		// 1.JDBC driver 명세
		// 2.URL 
		// 3.계정 ID/PW
		String driver = "oracle.jdbc.OracleDriver";
		// 오라클 드라이버 명세 
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "java";
		String pw = "java";
		
		/**
		 * DB 연결
		 * 
		 * @return DB연결 객체 
		 */
		// 함수에 throws 걸면 위험하니 예외처리 한다.-> ClassNotFoundException
		try {
				// JDBC 드라이버 등록
				Class.forName(driver);
		System.out.println("=============================");		
		try {
			// DB  연결 (커넥션) , 객체 리턴할 수 있도록 해줌 
			conn= DriverManager.getConnection(url, id, pw);
			} catch(SQLException e){
				System.err.println("SQL 에러 :" + e);
				e.printStackTrace();
			}

		System.out.println("=============================");
					
			} catch(ClassNotFoundException e) {
				System.err.println("CNFE 에러 : " + e);
				e.printStackTrace();
		}	return conn;
	
		/**
		 * DB 자원 반납(close)
		 * 
		 * @param pstmt SQL 처리 객체
		 * @param rs SQL 결과셋 객체
		 * @param conn DB 연결 객체 
		 */
	}
	// 연결 해제 
	// PreparedStatement ResultSet Connection 인자 3개 
	public static void close(PreparedStatement pstmt,
							ResultSet rs,
							Connection conn) {
		 if(rs != null) {
			 try 
		    {
				rs.close();
			} catch (SQLException e) {
			 	e.printStackTrace();
			}
		 }
		 if ( pstmt !=null) 
		    {
			 try {
				pstmt.close();
	    	} catch (SQLException e) {
			 	e.printStackTrace();
			}
		 }	// 인스턴스가 살아 있다면 
			
		 if( conn != null)
			{	try {
				conn.close();
			} catch (SQLException e) {
				 e.printStackTrace();
			}
	 	}
	 }	 
	
	}

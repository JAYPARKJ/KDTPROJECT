package com.javateam.memberProject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
////////////////////hamcrest //////////////////////

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

////////////////////////////////////////////////////
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.memberProject.domain.MemberVO;

@SpringBootTest
class MemberDAOTest2 {
	@Autowired
	MemberDAO memberDAO;

	List<MemberVO> members;
	
	// 공통 코드 => 선처리
		@BeforeEach
		public void setUp() {
			members = memberDAO.selectAllMembers();
		}
		
	
		// case-1) 데이터 유무(null) 점검
		 
		 	
		
		@Test
		void testSelectAllMembers() {
			// assertNull(members);
			assertNotNull(members);
		}	
		
		// Test는 return 되는게 없
		// case-2) 총 데이터(레코드)의 갯수(인원수)
		// ex) SELECT count(*) FROM MEMBER_TBL;
		// 기댓값 100개
		@Test
		void testCountSelectAllMembers() {
			// 참 값이면 파란불 , 삐딱하게 나오면 static임
			//assertEquals(100, members.size());
			// ctrl + space해서 안보이면 위에 임포트 해라 
			assertThat(100, equalTo(members.size()));
			
		
		}
		
		// case-3) 맨 처음/끝 레코드의 이름(데이터) 점검
		// 첫번째 레코드의 사람 이름이 누구냐? 소갑정 // 마지막은 허운철
		/*
		 * SELECT * FROM MEMBER_TBL;
		 * 
		 * 
		 * SELECT * FROM (SELECT m.*, rownum rowNo FROM ( SELECT * FROM member_tbl ) m )
		 * where rowNo = 1; 소갑정
		 * 
		 * 
		 * SELECT * FROM (SELECT m.*, rownum rowNo FROM ( SELECT * FROM member_tbl ) m )
		 * where rowNo = 100; 허운철
		 */
		@Test //일시적인 블럭
/*		void testFirstMembers() {
			
			// 첫번째 회원 조회
			// members.get(0).getName()
			// 소갑정
			// 똑같은거지만 다른 표현 
			// assertEquals("소갑정", members.get(0).getName());
			assertThat("소갑정", equalTo(members.get(0).getName()));
		}*/
		
		void testLastMembers() {
			
			// 마지막 회원 조회
			// members.get(99).getName()
			// 허운철
			//assertEquals("허운철", members.get(99).getName());
			 assertThat("허운철", equalTo(members.get(99).getName()));
			
		}
		
}

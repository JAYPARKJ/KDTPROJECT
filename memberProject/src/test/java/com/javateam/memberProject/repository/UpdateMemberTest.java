package com.javateam.memberProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.memberProject.domain.MemberVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j

class UpdateMemberTest {

		@Autowired
		MemberDAO memberDAO;
		
		//사전에 주의하자~
		MemberVO memberVO;

	// 기존정보 없을 때 이상태에서 mapper가 들어간다면 기존 정보를 밀어넣고 그것만 setter해도됨 
	@BeforeEach
	void setUp() throws Exception 
	{
		
		// 동적 sql 패스워드만 바꾸면 정보가 안들어오기에, 밑에 정보는 업데이트 하지 않도록 구문 생략 -> 다이나믹 sql로 생략 할 수 있음 
		
		// 기존 정보 Mapper : (동적 SQL 사용전)  
		// memberVO = memberDAO.selectMemberById("abcd111");
		// 수정할 데이터(필드)
		// memberVO.setPw("#Java1111");
		// memberVO.setEmail("ABCDABCD@AAAA.com");
		
		// 수정할 필드만 수정하고자 할 때 
		// howto-1)
		// memberVO.setId("#abcd1111");
		// memberVO.setPw("#Java1111");
		// memberVO.setEmail("#Spring1111"); 
		
		// howto-2) 선별적 업데이트
		 memberVO = MemberVO.builder() 
				            .id("abcd1112") 
				            //.pw("#Java1111")
				            // 일부 수정
				            // .email("Spring@1111@naver.com")
				            .build();
		
		/* 기존정보 다 바꾸기에 위에게 나음
		 * memberVO = MemberVO.builder() .id("abcd1111") .pw("#Java1111") .build();
		 */
	}
	// case-1) 패쓰워드만 변경(수정)
	// case-1-1) 기존 VO 패쓰워드와 수정 패쓰워드 동등 비교
	@Transactional
	// 실제 데이터값이 들어가 잇다면 false, 패스워드만 변경하기위해 true
	@Rollback(false)
	@Test
	void testUpdateMemberByPw() {
		
		 memberVO = MemberVO.builder() 
		            .id("abcd1111") 
		            .pw("#Java2222")
		            // 일부 수정
		            //.email("Spring@1111@naver.com")
		            .build();
		 
		// log.info("MemberVO : " + memberVO);
		boolean result = memberDAO.updateMember(memberVO);
		assertTrue(result);
	
		// case-1-1)
		String pw2
				=
				memberDAO.selectMemberById(memberVO.getId()).getPw();
				assertEquals("#Java2222", pw2);
				
	}
	  // case-2) 이메일만 변경(수정)
      // case-2-1) 기존 VO 이메일과 수정 이메일 동등비교
	 @Transactional
	 @Rollback(true)
	 @Test
	 void testUpdateMemberByEmail() 
	 {
		memberVO = MemberVO.builder()
						   .id("abcd1111")
						   .email("JavaSpring@naver.com")
						   .build();
		boolean result = memberDAO.updateMember(memberVO);
		assertTrue(result);
		 
		// case-2-1)
		String email2
			=
			memberDAO.selectMemberById(memberVO.getId()).getEmail();
			assertEquals("JavaSrping@naver.com", email2);
	 }
	 	// case-3) 휴대폰/일반전화 변경(수정)
	 	// case-3-1) 기존 VO 휴대폰/일반전화와 수정 휴대폰/일반전화 동등 비교
	 	@Transactional
	 	@Rollback(true)
	 	@Test
	 	void testUpdateMemberByMobileAndPhone() {
	 		
	 		memberVO = MemberVO.builder()
	 						   .id("abcd1111")
	 						   .mobile("010-5656-7878")
	 						   .phone("02-6555-8282")
	 						   .build();
	 		boolean result = memberDAO.updateMember(memberVO);
	 		assertTrue(result);
	 		
	 		// case-3-1)
	 		MemberVO memberVO2
	 				= memberDAO.selectMemberById(memberVO.getId());
	 		String mobile2 = memberVO2.getMobile();
	 		String phone2 = memberVO2.getPhone();
	 		
	 		assertEquals("010-5656-7878", mobile2);
	 		assertEquals("02-6555-8282", phone2);
	 		
	 	}
	 	
	}
	
 

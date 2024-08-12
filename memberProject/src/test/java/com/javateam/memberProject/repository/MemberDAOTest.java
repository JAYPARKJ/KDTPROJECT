package com.javateam.memberProject.repository;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.javateam.memberProject.domain.MemberVO;


@SpringBootTest
class MemberDAOTest {
	
	@Autowired
	MemberDAO memberDAO;


	// 개별 회원 정보를 조회
	// case) id(mbc_1023)인 회원정보의 이름이 "장갑인"인지 점검
	@Test
	void testSelectMemberById() {
		
		MemberVO memberVO = memberDAO.selectMemberById("mbc_1023");
		// assertNotEquals("홍길동", memberVO.getName());
		// assertEquals("홍길동", memberVO.getName());
		assertEquals("배정민", memberVO.getName());
	}


	 
}
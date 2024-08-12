package com.javateam.memberProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
//@Log4j
@Slf4j

class HasMemberTest {

	@Autowired
	MemberDAO memberDAO;
	
	// case-1 ) 아이디(id) 중복 점검 -> 정규식이 된 상태에서 ajax에서 봄 ( 정규식에서 규정에 맞게 타당하다)
	// case-1-1) 중복되지 않는 아이디 입력/점검
	@Transactional(readOnly =true)
	@Test
	void testHasMemberByFld() {
		//log.info("결과 : " + memberDAO.hasMemberByFld("ID", "javaSpring1"));
		assertFalse(memberDAO.hasMemberByFld("ID", "javaSpring1"));}
		//  중복
		// assertTrue(memberDAO.hasMemberByFld("ID", "abcd1111"));}
	
	// case-1-2) 중복되는 아이디 입력/점검
	@Test
	void testHasMemberByFld2() {
		//log.info("결과 : " + memberDAO.hasMemberByFld("ID", "javaSpring1"));
		assertTrue(memberDAO.hasMemberByFld("ID", "abcd1111"));}
	
	// case-2) 이메일(email) 중복점검
	// case-2-1) 중복되지 않는 이메일 입력/점검
	@Test // 함수 이름에 대한 강제성은 없음
	// 사전에 중복점검 확인
	void testHasMemberByFldEmail() { 
		assertFalse(memberDAO.hasMemberByFld("EMAIL","javaspring@naver.com"));
	}
	// case-2-2) 중복된 이메일 입력/점검		
	@Test 
	void testHasMemberByFldAndEmail2() { 
		assertTrue(memberDAO.hasMemberByFld("EMAIL","abcd1111@abcd.com"));
	}
	// case-3) 휴대폰 (Mobile) 중복 점검
	// case-3-1) 중복되지 않는 휴대폰 입력/점검
	@Test  
	 void testHasMemberByFldMobile() { 
		assertFalse(memberDAO.hasMemberByFld("MOBILE","010-7777-8888"));
	}
	// case-3-2) 중복된 휴대폰 입력/점검
	@Test
	void testHasMemberByFldAndMOBILE2() { 
	assertTrue(memberDAO.hasMemberByFld("MOBILE","010-1111-3333"));
}

	
}

package com.javateam.memberProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

class DeleteMemberTest {

	@Autowired
	 MemberDAO memberDAO;
	
	// case-1) 회원(abcd1111)의 롤(들) 삭제 점검
	@Transactional
	@Test
	void testDeleteRoles() {
		assertTrue(memberDAO.deleteRoles("mbc_1001"));
		
	}
	
	// case-2)회원정보 삭제
	// 주의) 참조 무결성을 고려하여 자녀(참고하는) 테이블을 먼저 삭제 이후에
	// 부모 테이블의 레코드를 삭제해야 됨
	
	@Transactional
	@Rollback(true)
	@Test
	void testDeleteMemberById() {
		
		assertTrue(memberDAO.deleteRoles("abcd1111"));
		assertTrue(memberDAO.deleteMemberById("abcd1111"));
	}
	 
}

package com.javateam.memberProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
/* 데이터 접근 객체(DAO : Data Access Object) DAO에서는 SQL문을 구현한 XML파일에서의 해당 ID값을 호출하여
데이터 조회하거나 조작한다*/

class InsertRoleTest {

	@Autowired
	MemberDAO memberDAO;
	// case-1) 존재하는 id(abcd1111)에 대한 관리자 롤(ROLE_ADMIN)을 할당 
	@Transactional
	@Rollback(true)
	@Test
	void testInesrtRole() {
		assertTrue(memberDAO.insertRole("abcd3333", "ROLE_USER"));
	}
	// case-2) 존재하지 않는 id(abcd3333)에 대한 관리자 롤(ROLE_USER)을 할당 
		// 기댓값 => false
		@Transactional
		@Rollback(true)
		@Test
		void testInsertRole2() {

			assertFalse(memberDAO.insertRole("abcd3333", "ROLE_USER"));
		}
		
}

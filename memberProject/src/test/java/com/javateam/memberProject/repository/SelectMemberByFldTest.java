package com.javateam.memberProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class SelectMemberByFldTest {

	@Autowired
	MemberDAO memberDAO;
	// 테스트 케이스
	// case-4) 아이디/이메일/휴대폰으로 개별 회원정보 조회 점검
	// case-4-1) 아이디로 점검
	// case-4-2) 이메일로 점검
	// case-4-3) 휴대폰으로 점검
	@Test
	void testSelectMemberByFldId() {
		Map<String, Object> map =
		memberDAO.selectMemberByFld("ID", "mbc_1001");
		assertThat("소갑정", equalTo(map.get("NAME")));
	}
	// case-4-2) 이메일로 개별 회원정보(이름) 점검
	@Test
	void testSelectMemberByFldEmail() {
		
		Map<String, Object> map =
		memberDAO.selectMemberByFld("EMAIL", "mbc_1@abcd.com");
		assertThat("소갑정", equalTo(map.get("NAME")));
	}
	
	
	// case-4-3) 휴대폰으로 개별 회원정보(이름) 점검
	@Test
	void testSelectMemberByFldMobile() {
		
		Map<String, Object> map =
		memberDAO.selectMemberByFld("MOBILE", " 010-1234-1001");
		assertThat("소갑정", equalTo(map.get("NAME")));
	}
}
 

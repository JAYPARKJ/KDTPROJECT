package com.javateam.memberProject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.memberProject.domain.MemberVO; 

/*
@Transactional: 테스트가 완료된 후 데이터베이스의 변경 사항을 롤백하여 데이터 일관성을 유지합니다.
@Rollback(true): 트랜잭션을 롤백합니다. 테스트가 데이터베이스에 실제로 영향을 미치지 않게 합니다.
이 테스트는 id가 "mbc_1001"인 사용자의 패스워드를 "Java2222"로 변경하려고 시도합니다. 
updateMember 메서드가 true를 반환하면 테스트가 성공적으로 완료된 것으로 간주됩니다. 
*/
import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
class UpdateMemberTest {
	@Autowired
	MemberService memberService;
	MemberVO memberVO;
	// case-1) 패쓰워드만 변경(수정)
	// 자체적으로 트랜잭션 있으나 기능 써줌
	@Transactional
	@Rollback(true)
	@Test
	void testUpdateMember() {
	
		 memberVO = MemberVO.builder()
				 			.id("mbc_1001")
				 			.pw("Java2222")
				 			.build();
		 // 업데이트 멤버VO 집어넣음 
		assertTrue(memberService.updateMember(memberVO));
		 
		 
	}
	
	// case-2) 존재하지 않는 회원정보를 수정하려고 할 때 
	// case-2-1) 존재하지 않는 회원 아이디(abcdabcd1111) => 메시징(log) 출력 여부 점검
	// 메시지 : "수정할 회원정보가 존재하지 않습니다." => false 
	@Transactional
	@Rollback(true)
	@Test
	void testUpdateMemberAbsent() 
	{
		 memberVO = MemberVO.builder()
				 			.id("abcd55555")
				 			.pw("#Java3333")
				 			.build();
		 // 업데이트 멤버VO 집어넣음 
		assertFalse(memberService.updateMember(memberVO));
	}
	
}

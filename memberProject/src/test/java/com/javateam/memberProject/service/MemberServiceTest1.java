package com.javateam.memberProject.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.memberProject.domain.MemberVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MemberServiceTest1 {
	
	@Autowired
	MemberService memberService;
	
	MemberVO memberVO;
	  
	  // 삽입(생성)할 회원정보 준비
	@BeforeEach
	public void setUp() {
		 // howto-1) //memberVO = new MemberVO(); //memberVO.setId("abcd1111"); //중략
		  
		  // howto-2) // memberVO = new MemberVO("abcd1111", "#Abcd1234", ...)
		  
		  // howto-3)
		memberVO = MemberVO.builder()
				   .id("abcdjava1")
				   .pw("#Abcd1234")
				   .name("자바맨")
				   .gender("m")
				   .email("abcdJAVA1@abcd.com")
				   .mobile("010-1112-5454")
				   .phone("02-1111-2222")
				   .zip("08290")
				   .roadAddress("서울특별시 관악구 신림로 340")
				   .jibunAddress("서울특별시 관악구 신림동 1422-5 르네상스 복합쇼핑몰")
				   .detailAddress("6층 MBC 아카데미")
				   .birthday(Date.valueOf("1999-11-11"))
				   //.joindate(new Date(System.currentTimeMillis())) .build(); }
		  			.joindate(Date.valueOf("2024-08-05"))
		  			.build();
	}
	
	 // case-1 ) 주어진 회원정보 (MemberVO) 테이블에서 레코드 생성 점검 // case1-1) 트랜잭션 활용
	 /* 롤백(rollback) => 실제 테이블 영향(X) // => 테스트 ! // case-1-2) 판정 시 assertXXX 정확한
	  판정(단언) 결과값이 필요!
	  */
	// rollback은 테스트며 안쓰거나 그러면 커밋된다.true => 취소(rollback), false
	  //=> 반영(commit)
	  
	@Test
	void testInsertMember() {
		
		assertTrue(memberService.insertMember(memberVO));
		
		
	}
	 // 이후 판정 남
	  //memberDAO.insertMember(memberVO); // true / false로 정리
	
	
	 

}

class MemberServiceTest2 {
	@Autowired
	MemberService memberService;
	MemberVO memberVO;
	@BeforeEach
	public void setUp() {
		memberVO = MemberVO.builder()
				   .id("abcdjava1")
				   .pw("#Abcd1234")
				   .name("자바맨")
				   .gender("m")
				   .email("abcdJAVA1@abcd.com")
				   .mobile("010-1112-5454")
				   .phone("02-1111-2222")
				   .zip("08290")
				   .roadAddress("서울특별시 관악구 신림로 340")
				   .jibunAddress("서울특별시 관악구 신림동 1422-5 르네상스 복합쇼핑몰")
				   .detailAddress("6층 MBC 아카데미")
				   .birthday(Date.valueOf("1999-11-11"))
				   // .joindate(new Date(System.currentTimeMillis()))
				   .joindate(Date.valueOf("2024-08-05"))
				   .build();
	}

	@Test
	void testInsertMember2() {
		// assertThat(memberService.insertMember(memberVO), equalTo(memberVO));
		MemberVO actualVO = memberService.insertMember2(memberVO);
		MemberVO expectedVO = memberVO;
		assertTrue(expectedVO.equals(actualVO));
	}

}
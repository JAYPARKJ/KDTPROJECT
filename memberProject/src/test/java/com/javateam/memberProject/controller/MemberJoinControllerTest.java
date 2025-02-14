/*
 * package com.javateam.memberProject.controller;
 * 
 * import static
 * org.assertj.core.api.Assertions.setExtractBareNamePropertyMethods; import
 * static org.junit.jupiter.api.Assertions.*; import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import
 * static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
 * 
 * import java.sql.Date;
 * 
 * import org.junit.jupiter.api.AfterEach; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.http.MediaType; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.setup.MockMvcBuilders; import
 * org.springframework.web.context.WebApplicationContext;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.javateam.memberProject.domain.MemberVO; import
 * com.javateam.memberProject.service.MemberService;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @SpringBootTest
 * 
 * @AutoConfigureMockMvc // 꼭 써야 되는건 아니지만 목객체 생성해야함
 * 
 * @Slf4j class MemberJoinControllerTest {
 * 
 * // context 객체 (wac)
 * 
 * @Autowired WebApplicationContext wac;
 * 
 * // 목(mock) 객체
 * 
 * @Autowired MockMvc mockMvc;
 * 
 * // 서비스 객체 ex) @AfterEach : 회원정보 삭제
 * 
 * @Autowired MemberService memberService;
 * 
 * // VO MemberVO memberVO;
 * 
 * // 목(mock) 객체 생성, 회원정보 준비
 * 
 * @BeforeEach void setUp() throws Exception { this.mockMvc =
 * MockMvcBuilders.webAppContextSetup(ctx).builder();
 * 
 * memberVO = MemberVO.builder() .id("springjava1111") .pw("#Abcd1234")
 * .name("홍길동") .gender("m") .email("springjava1111@abcd.com")
 * .mobile("010-8989-7878") .phone("02-1111-2222") .zip("08290")
 * .roadAddress("서울특별시 관악구 신림로 340")
 * .jibunAddress("서울특별시 관악구 신림동 1422-5 르네상스 복합쇼핑몰")
 * .detailAddress("6층 MBC 아카데미") .birthday(Date.valueOf("2000-01-01"))
 * .joindate(Date.valueOf("2024-08-05")) .build(); }
 * 
 * @AfterEach void tearDown() throws Exception {
 * memberService.deleteMember("springjava1111"); }
 * 
 * // case-1) URL/Http 메소드 => 인자(준비된 회원정보) => JSON화(일괄처리) // => 연결여부(Http 상태 코드
 * : 200) //=> 성공여부(메시징) 점검 => 이동될 html(tyhmeleaf)점검
 * 
 * @Test void testJoinProc() throws Exception { ObjectMapper om = new
 * ObjectMapper(); String json = om.writeValueAsString(om);
 * 
 * try { mockMvc.perform(post("/member/joinProc")
 * .contentType(MediaType.APPLICATION_JSON) .content(json)
 * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk())
 * .andExpect(model().attribute("msg", "회원가입에 성공하셨습니다"))
 * .andExpect(view().name("/member/result"));
 * 
 * }catch (Exception e) {
 * 
 * System.err.println(""); }
 * 
 * }
 * 
 * private Object Model() { // TODO Auto-generated method stub return null; }
 * 
 * }
 
 s://www.yes24.com/Product/Goods/36836557 ※ 목차 참고
고정
No.366
파이썬(python)
댓글 0개
user profile
이승호 강사
관리자
2024/08/05
2024년 8월 5일 4교시 수업내용 (웹 서버 구축) : Spring & MyBatis 트랜잭션(Transaction)
1.MemberVO.java : 자바빈 객체 멤버필드간의 비교역할을 하는 hashCode, equals 메소드 오버라이딩 작성시 joindate를 비교 대상에서 제외 ...(전략)... @Override public boolean equals(Object obj) { if (this == obj) return true; if (obj == null) return false; if (getClass() != obj.getClass()) return false; MemberVO other = (MemberVO) obj; return Objects.equals(birthday, other.birthday) && Objects.equals(detailAddress, other.detailAddress) && Objects.equals(email, other.email) && Objects.equals(gender, other.gender) && Objects.equals(id, other.id) && Objects.equals(jibunAddress, other.jibunAddress) && Objects.equals(mobile, other.mobile) && Objects.equals(name, other.name) && Objects.equals(phone, other.phone) && Objects.equals(pw, other.pw) && Objects.equals(roadAddress, other.roadAddress) && Objects.equals(zip, other.zip); } @Override public int hashCode() { return Objects.hash(birthday, detailAddress, email, gender, id, jibunAddress, mobile, name, phone, pw, roadAddress, zip); } ...(후략)...
No.365
웹 서버 구축
 
 

package com.javateam.memberProject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.javateam.memberProject.domain.MemberVO;
import com.javateam.memberProject.service.MemberService;


import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("member")
@Slf4j
public class MemberJoinController {


	@Autowired
	MemberService memberService;
	// @RequestMapping(value="/member/joinProc", 
	//				method=RequestMethod.POST)
	// @PostMapping("/member/joinProc")
	@PostMapping("joinProc")
	public String joinProc(@RequestBody MemberVO memberVO, Model model) {
		log.info("회원가입 처리 : {}", memberVO);
		String msg = ""; // 저장 성공/실패 메시지
		if (memberService.insertMember(memberVO) == true) {
			msg = "회원가입에 성공하셨습니다.";
		} else {
			msg = "회원가입에 실패하였습니다.";
		}
		model.addAttribute("msg", msg);
		return "/member/result"; // result.html (thymeleaf)
	}
}
2.result.html

<!DOCTYPE html>
<html xmlns:th="http://www.thymleaf.org">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
메시지 : [[${msg}]]
</body>
</html>
3. MemberJoinControllerTest.java

package com.javateam.memberProject.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.sql.Date;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.memberProject.domain.MemberVO;
import com.javateam.memberProject.service.MemberService;


import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MemberJoinControllerTest {
	
	// context 객체(wac)
	@Autowired
	WebApplicationContext ctx;
	
	// 목(mock) 객체
	@Autowired 
	MockMvc mockMvc;
	
	// 서비스 객체 ex) @AfterEach : 회원정보 삭제
	@Autowired
	MemberService memberService;
	
	// VO
	MemberVO memberVO;


	// 목(mock) 객체 생성, 회원정보 준비
	@BeforeEach
	void setUp() throws Exception {
		
		this.mockMvc 
			= MockMvcBuilders.webAppContextSetup(ctx).build();
		
		memberVO = MemberVO.builder()
				   .id("springjava1111")
				   .pw("#Abcd1234")
				   .name("홍길동")
				   .gender("m")
				   .email("springjava1111@abcd.com")
				   .mobile("010-8989-7878")
				   .phone("02-1111-2222")
				   .zip("08290")
				   .roadAddress("서울특별시 관악구 신림로 340")
				   .jibunAddress("서울특별시 관악구 신림동 1422-5 르네상스 복합쇼핑몰")
				   .detailAddress("6층 MBC 아카데미")
				   .birthday(Date.valueOf("2000-01-01"))
				   .build();
	}


	// 후처리 : 테스트시 가입된 회원삭제
	@AfterEach
	void tearDown() throws Exception {
		memberService.deleteMember("springjava1111");
	}


	// case-1) URL/Http 메소드 => 인자(준비된 회원정보) => JSON화(일괄처리) 
	// => 연결 여부(Http 상태 코드 : 200) 
	// => 성공 여부(메시징) 점검 => 이동될 html(thymeleaf) 점검
	@Test
	void testJoinProc() {
		
		ObjectMapper om = new ObjectMapper();
		String json = "";
		try {
			json = om.writeValueAsString(memberVO);
		} catch (JsonProcessingException e1) {
			log.error("json 에러 : " + e1);
			e1.printStackTrace();
		}
		
		try {
			
			mockMvc.perform(post("/member/joinProc")
							.contentType(MediaType.APPLICATION_JSON)
							.content(json)
							.accept(MediaType.APPLICATION_JSON))
						  .andExpect(status().isOk())
						  .andExpect(model().attribute("msg", "회원가입에 성공하셨습니다."))
						  .andExpect(view().name("/member/result"));
			
		} catch (Exception e) {
			log.error("MemberJoinController 에러 : " + e);
			e.printStackTrace();
		}
	}

}
*/
 
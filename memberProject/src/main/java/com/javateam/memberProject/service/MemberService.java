package com.javateam.memberProject.service;

import java.util.List;


import com.javateam.memberProject.domain.MemberVO;


public interface MemberService {


	 
 
	/**
	 * 개별 회원정보 조회(검색)
	 * 
	 * @param id 회원 아이디
	 * @return 회원정보
	 */
	MemberVO selectMemberById(String id);
	/**
	 * 페이징에 의한(페이지 별로) 회원정보 조회(검색)
	 * 
	 * @param page 페이지
	 * @param limit 페이지당 회원정보 갯수
	 * @return 회원정보(들)
	 */
	List<MemberVO> selectMembersByPaging(int page, int limit);
	/**
	 * 전체 회원정보 조회
	 * 
	 * @return 전체 회원정보
	 */
	List<MemberVO> selectAllMembers();
	/**
	 * 회원정보 삽입(생성)
	 * 
	 * @param memberVO 회원정보
	 * @return 삽입된(생성된) 레코드 수(int) / 삽입(생성) 여부(boolean) 
	 * ex) 1/true(레코드 생성), 0/false(레코드 미생성)
	 */
	boolean insertMember(MemberVO memberVO);
	//boolean insertRole(String id, String role);

	/**
	 * 회원정보 삽입(생성)
	 * 
	 * @param memberVO 회원정보
	 * @return 생성된 회원 정보 
	  
	 */
	MemberVO insertMember2(MemberVO memberVO);
	/**
	 * 회원정보 수정(갱신)
	 * 
	 * @param memberVO 회원정보
	 * @return 생성된 회원 정보 (갱신여부)
	  
	 */
	boolean updateMember(MemberVO memberVO);
	/**
	 * 개별 회원정보 삭제 
     * : 회원 롤(role)/회원정보 동시 삭제
	 * 
	 * @param id 회원 아이디
	 * @return 회원정보 삭제 성공 여부 
	 */
	boolean deleteMember(String id);
} 